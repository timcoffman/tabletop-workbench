package com.tcoffman.ttwb.model.persistance.xml;

import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_BINDING;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_EXTENDS;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_ID;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_IS_TERMINAL;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_MODEL;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_PROTOTYPE_REF;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_REF;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_RESULT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_TYPE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_IMPORT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_INITIAL_STAGE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_OP_JOIN;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_OP_MOVE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_OP_ORIENT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_OP_SIGNAL;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_OP_SPLIT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PART;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PARTS;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_ANY;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_FILTER;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_INTERSECTION;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_INVERSION;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_QUANTITY;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_RELATIONHIP;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_ROLE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_ROOT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_SUBJECT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_TARGET;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PLACE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PROTOTYPE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_ROLE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_RULE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_STAGE;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import javax.xml.namespace.QName;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tcoffman.ttwb.component.GameComponent;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.GameComponentRefManager;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GameModelProperty;
import com.tcoffman.ttwb.model.GamePartInstance;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePlacePrototype;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameRule;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.part.GameAnyPartPattern;
import com.tcoffman.ttwb.model.pattern.part.GameFilterPartPattern;
import com.tcoffman.ttwb.model.pattern.part.GameIntersectionPartPattern;
import com.tcoffman.ttwb.model.pattern.part.GameInversionPartPattern;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.part.GameVariablePartPattern;
import com.tcoffman.ttwb.model.pattern.place.GameAnyPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GameFilterPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GameIntersectionPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GameInversionPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GameRelationshipPlacePattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameAnyQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameRangeQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameSingleQuantityPattern;
import com.tcoffman.ttwb.model.pattern.role.GameRolePattern;
import com.tcoffman.ttwb.model.persistance.ModelRefManager;
import com.tcoffman.ttwb.plugin.PluginName;

public class ModelWriter {

	private final GameModel m_model;
	private final Map<Integer, Element> m_objectElements = new HashMap<>();
	private final Map<Integer, String> m_objectIdentifierMap = new HashMap<>();
	private final ModelRefManager m_externalRefManager;

	public ModelWriter(GameModel model, ModelRefManager externalRefManager) {
		m_model = model;
		m_externalRefManager = externalRefManager;

	}

	private Element createAndAppendElement(Node parentNode, Object boundObject, QName name) {
		if (m_objectElements.containsKey(boundObject.hashCode()))
			throw new IllegalStateException("object already bound to an element");

		final Element childElement = createAndAppendElement(parentNode, name);

		m_objectElements.put(boundObject.hashCode(), childElement);

		final String id = m_objectIdentifierMap.get(boundObject.hashCode());
		if (null != id)
			childElement.setAttribute(MODEL_ATTR_NAME_ID, id);

		return childElement;
	}

	private Element createAndAppendElement(Node parentNode, QName name) {
		final Document document = parentNode.getNodeType() == Node.DOCUMENT_NODE ? (Document) parentNode : parentNode.getOwnerDocument();
		final Element childElement = document.createElementNS(name.getNamespaceURI(), name.getLocalPart());
		parentNode.appendChild(childElement);

		return childElement;

	}

	private final Map<String, PluginName> m_namespacePrefixes = new HashMap<>();
	private final Map<PluginName, String> m_namespaces = new HashMap<>();

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

	public void write(Document modelDocument) {
		final Element modelElement = createAndAppendElement(modelDocument, MODEL_ELEMENT_QNAME);
		writeModel(modelElement);
	}

	public void writeModel(Element modelElement) {
		for (final PluginName pluginName : m_model.getRequiredPlugins())
			modelElement.setAttribute("xmlns:" + prefixFor(pluginName), pluginName.toURI().toString());

		writeInitialStage(modelElement);
		writeRoles(modelElement);
		writeImportedModels(modelElement);
		writePrototypes(modelElement);
		writeParts(modelElement);
		writeStages(modelElement);
	}

	private void writeInitialStage(Element modelElement) {

		final Element initialStageElement = createAndAppendElement(modelElement, MODEL_ELEMENT_QNAME_INITIAL_STAGE);
		initialStageElement.setAttribute(MODEL_ATTR_NAME_REF, idForStage(m_model.getInitialStage().get()));

	}

	private void writeImportedModels(Element modelElement) {

		m_model.importedModels().forEachOrdered((r) -> writeImportedModel(modelElement, r));

	}

	private void writeImportedModel(Element modelElement, GameModel importedModel) {

		final Element importedModelElement = createAndAppendElement(modelElement, importedModel, MODEL_ELEMENT_QNAME_IMPORT);
		importedModelElement.setAttribute(MODEL_ATTR_NAME_MODEL, m_externalRefManager.getImportedModelResolver().lookupId(importedModel).get());

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

		prototype.getRoleBinding().ifPresent((r) -> prototypeElement.setAttribute(MODEL_ATTR_NAME_BINDING, idForRole(r)));
		prototype.getExtends().ifPresent((p) -> prototypeElement.setAttribute(MODEL_ATTR_NAME_EXTENDS, idForPrototype(p)));

		writePlaces(prototypeElement, prototype);
	}

	private void writePlaces(Element prototypeElement, GamePartPrototype prototype) {
		prototype.places().forEachOrdered((p) -> writePlace(prototypeElement, p));
	}

	private void writePlace(Element prototypeElement, GamePlacePrototype place) {
		final Element placeElement = createAndAppendElement(prototypeElement, MODEL_ELEMENT_QNAME_PLACE);

		writePlaceType(placeElement, place.getType().get());
		writeProperties(placeElement, place);
	}

	private void writePlaceType(Element placeElement, GamePlaceType placeType) {
		final String typeName = prefixFor(placeType.getDeclaringPlugin()) + ":" + placeType.getLocalName();
		placeElement.setAttribute(MODEL_ATTR_NAME_TYPE, typeName);
	}

	private void writeProperties(Element element, GamePlacePrototype place) {
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
		part.getRoleBinding().ifPresent((r) -> partElement.setAttribute(MODEL_ATTR_NAME_BINDING, idForRole(r)));
		partElement.setAttribute(MODEL_ATTR_NAME_PROTOTYPE_REF, idForPrototype(part.getPrototype()));
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
		// operationPattern.getQuantityPattern().ifPresent((p) ->
		// writePattern(operationPatternElement, p));
	}

	private void writeResult(Element operationPatternElement, GameComponentRef<GameStage> resultRef) {
		operationPatternElement.setAttribute(MODEL_ATTR_NAME_RESULT, idForStage(resultRef));
	}

	private void writePattern(Element operationPatternElement, GameQuantityPattern pattern) {
		final Element quantityElement = createAndAppendElement(operationPatternElement, MODEL_ELEMENT_QNAME_PATTERN_QUANTITY);
		pattern.visit(new GameQuantityPattern.Visitor<Element, RuntimeException>() {

			@Override
			public Element visit(GameAnyQuantityPattern pattern) {
				quantityElement.setAttribute("min", "0");
				quantityElement.setAttribute("max", "*");
				return null;
			}

			@Override
			public Element visit(GameRangeQuantityPattern pattern) {
				quantityElement.setAttribute("min", Long.toString(pattern.getMinimum().orElse(0L)));
				quantityElement.setAttribute("max", pattern.getMaximum().map((v) -> Long.toString(v)).orElse("*"));
				return null;
			}

			@Override
			public Element visit(GameSingleQuantityPattern pattern) {
				quantityElement.setAttribute("min", "1");
				quantityElement.setAttribute("max", "1");
				return null;
			}
		});
	}

	private void writePattern(Element operationPatternElement, GameRolePattern rolePattern) {
		createAndAppendElement(operationPatternElement, MODEL_ELEMENT_QNAME_PATTERN_ROLE);
	}

	private void writeSubjectPattern(Element operationPatternElement, GamePartPattern p) {
		createAndAppendElement(operationPatternElement, MODEL_ELEMENT_QNAME_PATTERN_SUBJECT);
	}

	private void writeSubjectPattern(Element operationPatternElement, GamePlacePattern p) {
		final Element subjectElement = createAndAppendElement(operationPatternElement, MODEL_ELEMENT_QNAME_PATTERN_SUBJECT);
		writePattern(subjectElement, p);
	}

	private void writeTargetPattern(Element operationPatternElement, GamePartPattern p) {
		createAndAppendElement(operationPatternElement, MODEL_ELEMENT_QNAME_PATTERN_TARGET);
	}

	private void writeTargetPattern(Element operationPatternElement, GamePlacePattern p) {
		final Element targetElement = createAndAppendElement(operationPatternElement, MODEL_ELEMENT_QNAME_PATTERN_TARGET);
		writePattern(targetElement, p);
	}

	private void writePattern(Element parentElement, GamePlacePattern pattern) {
		pattern.visit(new GamePlacePattern.Visitor<Element, RuntimeException>() {

			@Override
			public Element visit(GameAnyPlacePattern pattern) throws RuntimeException {
				final Element anyElement = createAndAppendElement(parentElement, MODEL_ELEMENT_QNAME_PATTERN_ANY);
				writePattern(anyElement, pattern.getQuantityPattern());
				writePattern(createAndAppendElement(anyElement, MODEL_ELEMENT_QNAME_PART), pattern.getPartPattern());
				return anyElement;
			}

			@Override
			public Element visit(GameFilterPlacePattern pattern) throws RuntimeException {
				final Element filterElement = createAndAppendElement(parentElement, MODEL_ELEMENT_QNAME_PATTERN_FILTER);
				writePattern(filterElement, pattern.getQuantityPattern());

				final Optional<GameComponentRef<GamePlaceType>> matchPlaceType = pattern.getMatchesType();
				if (matchPlaceType.isPresent()) {
					final GamePlaceType placeType = matchPlaceType.get().get();
					filterElement.setAttribute(MODEL_ATTR_NAME_TYPE, prefixFor(placeType.getDeclaringPlugin()) + ":" + placeType.getLocalName());
				}

				final Optional<GameComponentRef<GameRole>> matchRoleBinding = pattern.getMatchesRoleBinding();
				if (matchRoleBinding.isPresent()) {
					final GameRole roleBinding = matchRoleBinding.get().get();
					filterElement.setAttribute(MODEL_ATTR_NAME_TYPE, idFor(roleBinding));
				}

				writePattern(createAndAppendElement(filterElement, MODEL_ELEMENT_QNAME_PART), pattern.getPartPattern());

				return filterElement;
			}

			@Override
			public Element visit(GameIntersectionPlacePattern pattern) throws RuntimeException {
				final Element intersectionElement = createAndAppendElement(parentElement, MODEL_ELEMENT_QNAME_PATTERN_INTERSECTION);
				pattern.patterns().forEach((p) -> writePattern(intersectionElement, p));
				return intersectionElement;
			}

			@Override
			public Element visit(GameInversionPlacePattern pattern) throws RuntimeException {
				final Element inversionElement = createAndAppendElement(parentElement, MODEL_ELEMENT_QNAME_PATTERN_INVERSION);
				writePattern(inversionElement, pattern.getPattern());
				return inversionElement;
			}

			@Override
			public Element visit(GameRelationshipPlacePattern pattern) throws RuntimeException {
				final Element relElement = createAndAppendElement(parentElement, MODEL_ELEMENT_QNAME_PATTERN_RELATIONHIP);
				writePattern(createAndAppendElement(relElement, MODEL_ELEMENT_QNAME_PART), pattern.getPartPattern());
				return relElement;
			}

		});
	}

	private Element writePattern(Element parentElement, GamePartPattern pattern) {
		return pattern.visit(new GamePartPattern.Visitor<Element, RuntimeException>() {

			@Override
			public Element visit(GameAnyPartPattern pattern) throws RuntimeException {
				return createAndAppendElement(parentElement, MODEL_ELEMENT_QNAME_PATTERN_ANY);
			}

			@Override
			public Element visit(GameVariablePartPattern pattern) throws RuntimeException {
				return createAndAppendElement(parentElement, MODEL_ELEMENT_QNAME_PATTERN_ROOT);
			}

			@Override
			public Element visit(GameFilterPartPattern pattern) throws RuntimeException {
				final Element filterElement = createAndAppendElement(parentElement, MODEL_ELEMENT_QNAME_PATTERN_FILTER);

				final Optional<GameComponentRef<GamePartPrototype>> matchPrototype = pattern.getMatchesPrototype();
				if (matchPrototype.isPresent()) {
					final GamePartPrototype prototype = matchPrototype.get().get();
					filterElement.setAttribute(MODEL_ATTR_NAME_PROTOTYPE_REF, idFor(prototype));
				}

				final Optional<GameComponentRef<GameRole>> matchRoleBinding = pattern.getMatchesRoleBinding();
				if (matchRoleBinding.isPresent()) {
					final GameRole roleBinding = matchRoleBinding.get().get();
					filterElement.setAttribute(MODEL_ATTR_NAME_TYPE, idFor(roleBinding));
				}

				return filterElement;
			}

			@Override
			public Element visit(GameIntersectionPartPattern pattern) throws RuntimeException {
				final Element intersectionElement = createAndAppendElement(parentElement, MODEL_ELEMENT_QNAME_PATTERN_INTERSECTION);
				pattern.patterns().forEach((p) -> writePattern(intersectionElement, p));
				return intersectionElement;
			}

			@Override
			public Element visit(GameInversionPartPattern pattern) throws RuntimeException {
				final Element inversionElement = createAndAppendElement(parentElement, MODEL_ELEMENT_QNAME_PATTERN_INVERSION);
				writePattern(inversionElement, pattern.getPattern());
				return inversionElement;
			}

		});
	}

	private String idForPrototype(GameComponentRef<GamePartPrototype> ref) {
		return idFor(ref.get());
	}

	private String idForRole(GameComponentRef<GameRole> ref) {
		return idFor(ref.get());
	}

	private String idForStage(GameComponentRef<GameStage> ref) {
		return idFor(ref.get());
	}

	private String idFor(GamePartPrototype prototype) {
		return idFor(prototype, m_externalRefManager.getPartPrototypeManager());
	}

	private String idFor(GameStage stage) {
		return idFor(stage, m_externalRefManager.getStageManager());
	}

	private String idFor(GameRole role) {
		return idFor(role, m_externalRefManager.getRoleManager());
	}

	private <T extends GameComponent> String idFor(T component, GameComponentRefManager<T> componentRefManager) {
		final String id = componentRefManager.lookupId(component).orElseGet(() -> newIdFor(component, componentRefManager));
		return id;
	}

	private <T extends GameComponent> String newIdFor(T component, GameComponentRefManager<T> componentRefManager) {
		return idFor(component, componentRefManager::nextId);
	}

	private String idFor(Object obj, Supplier<String> idSupplier) {
		if (null == obj)
			throw new IllegalArgumentException("cannot provide an id for a missing role");
		String id = m_objectIdentifierMap.get(obj.hashCode());
		if (null == id) {
			id = idSupplier.get();
			newIdCreatedFor(id, obj);
			m_objectIdentifierMap.put(obj.hashCode(), id);
		}
		return id;
	}

	private void newIdCreatedFor(String id, Object obj) {
		final Element existingElement = m_objectElements.get(obj.hashCode());
		if (null != existingElement)
			existingElement.setAttribute(MODEL_ATTR_NAME_ID, id);
	}

}
