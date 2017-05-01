package com.tcoffman.ttwb.state.persistence.xml;

import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_NS;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_DST;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_MODEL;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_PROTOTYPE_REF;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_REF;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_SRC;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_TYPE;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_CURRENT_STAGE;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_PART;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_PARTS;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_RELATIONSHIP;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_RELATIONSHIPS;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_ROLE;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.StandardComponentRefManager;
import com.tcoffman.ttwb.component.persistence.xml.AbstractWriter;
import com.tcoffman.ttwb.model.GameModelProperty;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GamePlacePrototype;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePartRelationship;
import com.tcoffman.ttwb.state.GamePlace;
import com.tcoffman.ttwb.state.GameState;

public class StateWriter extends AbstractWriter {

	private static final String PREFIX_MODEL = "m";

	private final GameState m_state;
	private final ModelRefResolver m_modelResolver;
	private final StandardComponentRefManager<GamePart> m_partRefManager = new StandardComponentRefManager<GamePart>("part");
	private final String m_modelId;

	public StateWriter(GameState state, ModelRefResolver modelResolver, String modelId) {
		m_state = state;
		m_modelResolver = modelResolver;
		m_modelId = modelId;
	}

	@Override
	public void write(Document stateDocument) throws GameComponentBuilderException {
		final Element stateElement = createAndAppendElement(stateDocument, STATE_ELEMENT_QNAME);
		stateElement.setAttribute(STATE_ATTR_NAME_MODEL, PREFIX_MODEL + ":" + m_modelId);
		writeState(stateElement);
	}

	public void writeState(Element stateElement) throws GameComponentBuilderException {
		// for (final PluginName pluginName : m_state.getRequiredPlugins())
		// stateElement.setAttribute("xmlns:" + prefixFor(pluginName),
		// pluginName.toURI().toString());
		stateElement.setAttribute("xmlns:" + PREFIX_MODEL, MODEL_NS);

		writeCurrentStage(stateElement);
		writeRoles(stateElement);
		writeParts(stateElement);
		writeRelationships(stateElement);
	}

	private void writeCurrentStage(Element stateElement) throws GameComponentBuilderException {

		final Element initialStageElement = createAndAppendElement(stateElement, STATE_ELEMENT_QNAME_CURRENT_STAGE);
		initialStageElement.setAttribute(STATE_ATTR_NAME_REF, externalIdForStage(m_state.getCurrentStage()));

	}

	private void writeRoles(Element stateElement) {

		m_state.roles().forEachOrdered((r) -> writeRole(stateElement, r));

	}

	private void writeRole(Element stateElement, GameRole role) {

		createAndAppendElement(stateElement, role, STATE_ELEMENT_QNAME_ROLE);

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

	private void writeParts(Element stateElement) {

		final Element partsElement = createAndAppendElement(stateElement, STATE_ELEMENT_QNAME_PARTS);
		m_state.parts().forEachOrdered((p) -> writePart(partsElement, p));

	}

	private void writePart(Element partsElement, GamePart part) {

		final Element partElement = createAndAppendElement(partsElement, part, STATE_ELEMENT_QNAME_PART);
		partElement.setAttribute(STATE_ATTR_NAME_PROTOTYPE_REF, externalIdForPartPrototype(part.getPrototype()));
	}

	private void writeRelationships(Element stateElement) {
		final Element relationshipsElement = createAndAppendElement(stateElement, STATE_ELEMENT_QNAME_RELATIONSHIPS);
		m_state.relationships().forEachOrdered((s) -> writeRelationship(relationshipsElement, s));
	}

	private void writeRelationship(Element partsElement, GamePartRelationship rel) {

		final Element partElement = createAndAppendElement(partsElement, rel, STATE_ELEMENT_QNAME_RELATIONSHIP);

		final GamePartRelationshipType type = rel.getType().get();
		partElement.setAttribute(STATE_ATTR_NAME_TYPE, prefixFor(type.getDeclaringPlugin()) + ":" + type.getLocalName());

		final GamePlace srcPlace = rel.getSource().get();
		final GamePart srcPart = srcPlace.getOwner();
		final GamePlaceType srcPlaceType = srcPlace.getPrototype().get().getType().get();
		partElement.setAttribute(STATE_ATTR_NAME_SRC, idFor(srcPart) + "/" + prefixFor(srcPlaceType.getDeclaringPlugin()) + ":" + srcPlaceType.getLocalName());

		final GamePlace dstPlace = rel.getDestination().get();
		final GamePart dstPart = dstPlace.getOwner();
		final GamePlaceType dstPlaceType = dstPlace.getPrototype().get().getType().get();
		partElement.setAttribute(STATE_ATTR_NAME_DST, idFor(dstPart) + "/" + prefixFor(dstPlaceType.getDeclaringPlugin()) + ":" + dstPlaceType.getLocalName());
	}

	private String externalIdForPartPrototype(GameComponentRef<GamePartPrototype> componentRef) {
		return PREFIX_MODEL + ":" + m_modelResolver.getPartPrototypeResolver().lookupId(componentRef).orElseThrow(missingComponent(componentRef));
	}

	private String externalIdForStage(GameComponentRef<GameStage> componentRef) throws GameComponentBuilderException {
		return PREFIX_MODEL + ":" + m_modelResolver.getStageResolver().lookupId(componentRef).orElseThrow(missingComponent(componentRef));
	}

	private String idForPart(GameComponentRef<GamePart> ref) {
		return idFor(ref.get());
	}

	private String idFor(GamePart part) {
		return idFor(part, () -> newIdFor(part));
	}

	private String newIdFor(GamePart part) {
		final String id = m_partRefManager.nextId();
		newIdCreatedFor(id, part);
		return id;
	}

}
