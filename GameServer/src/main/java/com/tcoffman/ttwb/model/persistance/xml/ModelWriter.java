package com.tcoffman.ttwb.model.persistance.xml;

import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_BINDING;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_EXTENDS;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_ID;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_IS_TERMINAL;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_PROTOTYPE_REF;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_REF;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_RESULT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_TYPE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_INITIAL_STAGE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_NAME;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_OP_JOIN;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_OP_MOVE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_OP_ORIENT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_OP_SIGNAL;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_OP_SPLIT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PART;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PARTS;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_QUANTITY;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_ROLE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_SUBJECT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_TARGET;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PLACE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PROTOTYPE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_ROLE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_RULE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_STAGE;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.w3c.dom.Element;

import com.tcoffman.ttwb.model.GameComponentRef;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GameModelProperty;
import com.tcoffman.ttwb.model.GamePartInstance;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePlace;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameRule;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.pattern.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.GamePlacePattern;
import com.tcoffman.ttwb.model.pattern.GameQuantityPattern;
import com.tcoffman.ttwb.model.pattern.GameRolePattern;
import com.tcoffman.ttwb.plugin.PluginName;

public class ModelWriter {

	private final GameModel m_model;
	private final Map<Integer, Element> m_objectElements = new HashMap<Integer, Element>();
	private final Map<Integer, String> m_objectIdentifierMap = new HashMap<Integer, String>();
	private final Map<String, Integer> m_objectIdentifierSequences = new HashMap<String, Integer>();

	public ModelWriter(GameModel model) {
		m_model = model;
	}

	private Element createAndAppendElement(Element parentElement, Object boundObject, QName name) {
		if (m_objectElements.containsKey(boundObject.hashCode()))
			throw new IllegalStateException("object already bound to an element");

		final Element childElement = createAndAppendElement(parentElement, name);

		m_objectElements.put(boundObject.hashCode(), childElement);

		final String id = m_objectIdentifierMap.get(boundObject.hashCode());
		if (null != id)
			childElement.setAttribute(MODEL_ATTR_NAME_ID, id);

		return childElement;
	}

	private Element createAndAppendElement(Element parentElement, QName name) {
		final Element childElement = parentElement.getOwnerDocument().createElementNS(name.getNamespaceURI(), name.getLocalPart());
		parentElement.appendChild(childElement);

		return childElement;

	}

	private final Map<String, PluginName> m_namespacePrefixes = new HashMap<String, PluginName>();
	private final Map<PluginName, String> m_namespaces = new HashMap<PluginName, String>();

	private String nextAvailablePrefix(String prefixBase) {
		String prefix = prefixBase;
		int i = 0;
		while (m_namespacePrefixes.containsKey(prefix))
			prefix = prefixBase + Integer.toString(++i, 10);
		return prefix;
	}

	private String prefixFor(PluginName pluginName) {
		String prefix = m_namespaces.get(pluginName);
		if (null != prefix)
			return prefix;

		prefix = nextAvailablePrefix(pluginName.getQualifiedName().replaceAll(".*[^a-zA-Z]", "").toLowerCase());

		m_namespacePrefixes.put(prefix, pluginName);
		m_namespaces.put(pluginName, prefix);

		return prefix;
	}

	public void write(Element modelElement) {
		for (final PluginName pluginName : m_model.getRequiredPlugins())
			modelElement.setAttribute("xmlns:" + prefixFor(pluginName), pluginName.toURI().toString());

		writeName(modelElement);
		writeInitialStage(modelElement);
		writeRoles(modelElement);
		writePrototypes(modelElement);
		writeParts(modelElement);
		writeStages(modelElement);
	}

	private void writeName(Element modelElement) {

		final Element nameElement = createAndAppendElement(modelElement, MODEL_ELEMENT_QNAME_NAME);
		nameElement.setTextContent(m_model.getName());

	}

	private void writeInitialStage(Element modelElement) {

		final Element initialStageElement = createAndAppendElement(modelElement, MODEL_ELEMENT_QNAME_INITIAL_STAGE);
		initialStageElement.setAttribute(MODEL_ATTR_NAME_REF, idFor(m_model.getInitialStage()));

	}

	private void writeRoles(Element modelElement) {

		m_model.roles().forEachOrdered((r) -> writeRole(modelElement, r));

	}

	private void writeRole(Element modelElement, GameRole role) {

		createAndAppendElement(modelElement, role, MODEL_ELEMENT_QNAME_ROLE);

	}

	private void writePrototypes(Element modelElement) {

		m_model.prototypes().forEachOrdered((p) -> writePrototype(modelElement, p));

	}

	private void writePrototype(Element modelElement, GamePartPrototype prototype) {

		final Element prototypeElement = createAndAppendElement(modelElement, prototype, MODEL_ELEMENT_QNAME_PROTOTYPE);

		prototype.getRoleBinding().ifPresent((r) -> prototypeElement.setAttribute(MODEL_ATTR_NAME_BINDING, idFor(r)));
		prototype.getExtends().ifPresent((p) -> prototypeElement.setAttribute(MODEL_ATTR_NAME_EXTENDS, idFor(p)));

		writePlaces(prototypeElement, prototype);
	}

	private void writePlaces(Element prototypeElement, GamePartPrototype prototype) {
		prototype.places().forEachOrdered((p) -> writePlace(prototypeElement, p));
	}

	private void writePlace(Element prototypeElement, GamePlace place) {
		final Element placeElement = createAndAppendElement(prototypeElement, MODEL_ELEMENT_QNAME_PLACE);

		writePlaceType(placeElement, place.getType().get());
		writeProperties(placeElement, place);
	}

	private void writePlaceType(Element placeElement, GamePlaceType placeType) {
		final String typeName = prefixFor(placeType.getDeclaringPlugin()) + ":" + placeType.getLocalName();
		placeElement.setAttribute(MODEL_ATTR_NAME_TYPE, typeName);
	}

	private void writeProperties(Element element, GamePlace place) {
		place.properties().forEachOrdered((p) -> writeProperty(element, p));
	}

	private void writeProperty(Element element, GameModelProperty property) {
		final PluginName pluginName = property.getDeclaringPlugin();
		final String namespaceURI = pluginName.toURI().toString();
		final String qualifiedName = prefixFor(pluginName) + ":" + property.getLocalName();
		element.setAttributeNS(namespaceURI, qualifiedName, property.getValue());
	}

	private void writeParts(Element modelElement) {

		final Element partsElement = createAndAppendElement(modelElement, MODEL_ELEMENT_QNAME_PARTS);
		m_model.parts().forEachOrdered((p) -> writePart(partsElement, p));

	}

	private void writePart(Element modelElement, GamePartInstance part) {

		final Element partElement = createAndAppendElement(modelElement, part, MODEL_ELEMENT_QNAME_PART);
		partElement.setAttribute(MODEL_ATTR_NAME_PROTOTYPE_REF, idFor(part.getPrototype()));
	}

	private void writeStages(Element modelElement) {
		m_model.stages().forEachOrdered((s) -> writeStage(modelElement, s));
	}

	private void writeStage(Element stageContainerElement, GameStage stage) {
		final Element stageElement = createAndAppendElement(stageContainerElement, stage, MODEL_ELEMENT_QNAME_STAGE);
		stageElement.setAttribute(MODEL_ATTR_NAME_IS_TERMINAL, stage.isTerminal() ? "yes" : "no");

		stage.rules().forEachOrdered((s) -> writeRule(stageElement, s));

		stage.stages().forEachOrdered((s) -> writeStage(stageElement, s));
	}

	private void writeRule(Element stageElement, GameRule rule) {
		final Element ruleElement = createAndAppendElement(stageElement, rule, MODEL_ELEMENT_QNAME_RULE);
		writeResult(ruleElement, rule.getResult());
		rule.operationPatterns().forEachOrdered((p) -> writeOperationPattern(ruleElement, p));
	}

	private void writeOperationPattern(Element ruleElement, GameOperationPattern operationPattern) {
		QName elementName;
		switch (operationPattern.getType()) {
		case SIGNAL:
			elementName = MODEL_ELEMENT_QNAME_OP_SIGNAL;
			break;
		case MOVE:
			elementName = MODEL_ELEMENT_QNAME_OP_MOVE;
			break;
		case ORIENT:
			elementName = MODEL_ELEMENT_QNAME_OP_ORIENT;
			break;
		case JOIN:
			elementName = MODEL_ELEMENT_QNAME_OP_JOIN;
			break;
		case SPLIT:
			elementName = MODEL_ELEMENT_QNAME_OP_SPLIT;
			break;
		default:
			throw new UnsupportedOperationException("unrecognized operation type");
		}
		final Element operationPatternElement = createAndAppendElement(ruleElement, elementName);

		writePattern(operationPatternElement, operationPattern.getRolePattern());
		operationPattern.getSubjectPattern().ifPresent((p) -> writeSubjectPattern(operationPatternElement, p));
		operationPattern.getSubjectPlacePattern().ifPresent((p) -> writeSubjectPattern(operationPatternElement, p));
		operationPattern.getTargetPattern().ifPresent((p) -> writeTargetPattern(operationPatternElement, p));
		operationPattern.getTargetPlacePattern().ifPresent((p) -> writeTargetPattern(operationPatternElement, p));
		operationPattern.getQuantityPattern().ifPresent((p) -> writePattern(operationPatternElement, p));
	}

	private void writeResult(Element operationPatternElement, GameComponentRef<GameStage> resultRef) {
		operationPatternElement.setAttribute(MODEL_ATTR_NAME_RESULT, idFor(resultRef));
	}

	private void writePattern(Element operationPatternElement, GameQuantityPattern p) {
		createAndAppendElement(operationPatternElement, MODEL_ELEMENT_QNAME_PATTERN_QUANTITY);
	}

	private void writePattern(Element operationPatternElement, GameRolePattern rolePattern) {
		createAndAppendElement(operationPatternElement, MODEL_ELEMENT_QNAME_PATTERN_ROLE);
	}

	private void writeSubjectPattern(Element operationPatternElement, GamePartPattern p) {
		createAndAppendElement(operationPatternElement, MODEL_ELEMENT_QNAME_PATTERN_SUBJECT);
	}

	private void writeSubjectPattern(Element operationPatternElement, GamePlacePattern p) {
		createAndAppendElement(operationPatternElement, MODEL_ELEMENT_QNAME_PATTERN_SUBJECT);
	}

	private void writeTargetPattern(Element operationPatternElement, GamePartPattern p) {
		createAndAppendElement(operationPatternElement, MODEL_ELEMENT_QNAME_PATTERN_TARGET);
	}

	private void writeTargetPattern(Element operationPatternElement, GamePlacePattern p) {
		createAndAppendElement(operationPatternElement, MODEL_ELEMENT_QNAME_PATTERN_TARGET);
	}

	private String idFor(GameComponentRef<?> ref) {
		return idFor(ref.get());
	}

	private String idFor(Object obj) {
		if (null == obj)
			throw new IllegalArgumentException("cannot provide an id for a missing object");
		String id = m_objectIdentifierMap.get(obj.hashCode());
		if (null == id)
			m_objectIdentifierMap.put(obj.hashCode(), id = newIdFor(obj));
		return id;
	}

	private String idFormatForType(Class<?> type) {
		return type.getSimpleName().replaceAll("^(Game|Standard)*", "").toLowerCase();
	}

	private String newIdFor(Object obj) {
		final String prefix = idFormatForType(obj.getClass());
		Integer nextSequentialNumber = m_objectIdentifierSequences.get(prefix);
		if (null == nextSequentialNumber)
			m_objectIdentifierSequences.put(prefix, nextSequentialNumber = 1);
		else
			m_objectIdentifierSequences.put(prefix, ++nextSequentialNumber);
		final String id = prefix + "-" + nextSequentialNumber;

		final Element existingElement = m_objectElements.get(obj.hashCode());
		if (null != existingElement)
			existingElement.setAttribute(MODEL_ATTR_NAME_ID, id);

		return id;
	}

}
