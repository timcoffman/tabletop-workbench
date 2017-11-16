package com.tcoffman.ttwb.state.persistence.xml;

import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_BINDING;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_NS;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_BINDING;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_LOG_FORWARD_RESULT;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_LOG_MUTATION_REL_DST;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_LOG_MUTATION_REL_SRC;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_LOG_OPERATION_TYPE;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_LOG_REL_TYPE;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_LOG_ROLE;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_LOG_ROLLBACK_RESULT;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_MODEL;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_PROTOTYPE_REF;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_REF;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_REL_DST;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_REL_SRC;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_REL_TYPE;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_AUTHORIZATION;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_CURRENT_STAGE;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_LOG;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_LOG_ENTRY;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_LOG_FORWARD_MUTATIONS;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_LOG_MUTATION_REL_ADD;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_LOG_MUTATION_REL_REM;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_LOG_ROLLBACK_MUTATIONS;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_PART;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_PARTICIPANT;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_PARTS;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_RELATIONSHIP;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_RELATIONSHIPS;

import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.StandardComponentRefManager;
import com.tcoffman.ttwb.component.persistence.xml.AbstractWriter;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.state.GameAuthorizationManager;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePartRelationship;
import com.tcoffman.ttwb.state.GameParticipant;
import com.tcoffman.ttwb.state.GamePlace;
import com.tcoffman.ttwb.state.GameState;
import com.tcoffman.ttwb.state.mutation.AbstractGameStateMutation;
import com.tcoffman.ttwb.state.mutation.GameStateAddRelationship;
import com.tcoffman.ttwb.state.mutation.GameStateLogEntry;
import com.tcoffman.ttwb.state.mutation.GameStateMutation;
import com.tcoffman.ttwb.state.mutation.GameStateRelationshipMutation;
import com.tcoffman.ttwb.state.mutation.GameStateRemoveRelationship;

public class StateWriter extends AbstractWriter {

	private static final String PREFIX_MODEL = "m";

	private final GameState m_state;
	private final ModelRefResolver m_modelResolver;
	private final StandardComponentRefManager<GamePart> m_partRefManager = new StandardComponentRefManager<>("part");
	private final String m_modelId;

	private final GameAuthorizationManager m_authorizationManager;

	public StateWriter(GameState state, ModelRefResolver modelResolver, String modelId, GameAuthorizationManager authorizationManager) {
		m_state = state;
		m_modelResolver = modelResolver;
		m_modelId = modelId;
		m_authorizationManager = authorizationManager;
	}

	@Override
	public void write(Document stateDocument) throws GameComponentBuilderException {
		final Element stateElement = createAndAppendElement(stateDocument, STATE_ELEMENT_QNAME);
		stateElement.setAttribute(STATE_ATTR_NAME_MODEL, PREFIX_MODEL + ":" + m_modelId);
		writeState(stateElement);
	}

	public void writeState(Element stateElement) throws GameComponentBuilderException {
		for (final PluginName pluginName : m_state.getModel().getRequiredPlugins())
			stateElement.setAttribute("xmlns:" + prefixFor(pluginName), pluginName.toURI().toString());
		stateElement.setAttribute("xmlns:" + PREFIX_MODEL, MODEL_NS);

		writeCurrentStage(stateElement);
		writeParticipants(stateElement);
		writeParts(stateElement);
		writeRelationships(stateElement);

		writeLogEntries(stateElement);
	}

	private void writeCurrentStage(Element stateElement) throws GameComponentBuilderException {

		final Element initialStageElement = createAndAppendElement(stateElement, STATE_ELEMENT_QNAME_CURRENT_STAGE);
		initialStageElement.setAttribute(STATE_ATTR_NAME_REF, externalIdForStage(m_state.getCurrentStage()));

	}

	private void writeParticipants(Element stateElement) {

		m_state.participants().forEachOrdered((p) -> writeParticipant(stateElement, p));

	}

	private void writeParticipant(Element stateElement, GameParticipant participant) {

		final Element participantElement = createAndAppendElement(stateElement, participant, STATE_ELEMENT_QNAME_PARTICIPANT);
		participantElement.setAttribute(STATE_ATTR_NAME_BINDING, externalIdForRole(participant.getRole()));
		final Element authElement = createAndAppendElement(stateElement, STATE_ELEMENT_QNAME_AUTHORIZATION);
		authElement.setTextContent(m_authorizationManager.serializeAuthorization(participant.getAuthorization()));
	}

	private void writeParts(Element stateElement) {

		final Element partsElement = createAndAppendElement(stateElement, STATE_ELEMENT_QNAME_PARTS);
		m_state.parts().forEachOrdered((p) -> writePart(partsElement, p));

	}

	private void writePart(Element partsElement, GamePart part) {

		final Element partElement = createAndAppendElement(partsElement, part, STATE_ELEMENT_QNAME_PART);
		part.getRoleBinding().ifPresent((r) -> partElement.setAttribute(MODEL_ATTR_NAME_BINDING, externalIdForRole(r.get())));
		partElement.setAttribute(STATE_ATTR_NAME_PROTOTYPE_REF, externalIdForPartPrototype(part.getPrototype()));
		part.places().forEach((place) -> {
			partElement.appendChild(partElement.getOwnerDocument().createComment(place.toString()));
			place.outgoingRelationships().forEach((rel) -> {
				partElement.appendChild(partElement.getOwnerDocument().createComment("to " + rel.getDestination().get().toString()));
			});
			place.incomingRelationships().forEach((rel) -> {
				partElement.appendChild(partElement.getOwnerDocument().createComment("from " + rel.getSource().get().toString()));
			});
		});
	}

	private void writeRelationships(Element stateElement) {
		final Element relationshipsElement = createAndAppendElement(stateElement, STATE_ELEMENT_QNAME_RELATIONSHIPS);
		m_state.relationships().forEachOrdered((s) -> writeRelationship(relationshipsElement, s));
	}

	private void writeRelationship(Element partsElement, GamePartRelationship rel) {

		final Element partElement = createAndAppendElement(partsElement, rel, STATE_ELEMENT_QNAME_RELATIONSHIP);

		final GamePartRelationshipType type = rel.getType().get();
		partElement.setAttribute(STATE_ATTR_NAME_REL_TYPE, prefixFor(type.getDeclaringPlugin()) + ":" + type.getLocalName());

		final GamePlace srcPlace = rel.getSource().get();
		final GamePart srcPart = srcPlace.getOwner();
		final GamePlaceType srcPlaceType = srcPlace.getPrototype().get().getType().get();
		partElement.setAttribute(STATE_ATTR_NAME_REL_SRC,
				idFor(srcPart) + "/" + prefixFor(srcPlaceType.getDeclaringPlugin()) + ":" + srcPlaceType.getLocalName());

		final GamePlace dstPlace = rel.getDestination().get();
		final GamePart dstPart = dstPlace.getOwner();
		final GamePlaceType dstPlaceType = dstPlace.getPrototype().get().getType().get();
		partElement.setAttribute(STATE_ATTR_NAME_REL_DST,
				idFor(dstPart) + "/" + prefixFor(dstPlaceType.getDeclaringPlugin()) + ":" + dstPlaceType.getLocalName());
	}

	private void writeLogEntries(Element stateElement) throws GameComponentBuilderException {
		final Element logEntriesElement = createAndAppendElement(stateElement, STATE_ELEMENT_QNAME_LOG);

		final Iterator<GameStateLogEntry> i = m_state.log().sequential().iterator();
		while (i.hasNext())
			writeLogEntry(logEntriesElement, i.next());

	}

	private void writeLogEntry(Element logEntriesElement, GameStateLogEntry logEntry) throws GameComponentBuilderException {

		final Element logEntryElement = createAndAppendElement(logEntriesElement, logEntry, STATE_ELEMENT_QNAME_LOG_ENTRY);
		logEntryElement.setAttribute(STATE_ATTR_NAME_LOG_FORWARD_RESULT, externalIdForStage(logEntry.getForward()));
		logEntryElement.setAttribute(STATE_ATTR_NAME_LOG_ROLLBACK_RESULT, externalIdForStage(logEntry.getRollback()));
		final Element forwardMutationsElement = createAndAppendElement(logEntryElement, STATE_ELEMENT_QNAME_LOG_FORWARD_MUTATIONS);
		final Element rollbackMutationsElement = createAndAppendElement(logEntryElement, STATE_ELEMENT_QNAME_LOG_ROLLBACK_MUTATIONS);

		Iterator<? extends GameStateMutation> i;

		i = logEntry.forwardMutations().iterator();
		while (i.hasNext())
			writeMutation(forwardMutationsElement, i.next());
		i = logEntry.rollbackMutations().iterator();
		while (i.hasNext())
			writeMutation(rollbackMutationsElement, i.next());
	}

	private void writeMutation(Element mutationsElement, GameStateMutation mutation) throws GameComponentBuilderException {
		mutationsElement.setAttribute(STATE_ATTR_NAME_LOG_ROLE, externalIdForRole(mutation.getRole()));
		mutationsElement.setAttribute(STATE_ATTR_NAME_LOG_OPERATION_TYPE, mutation.getType().name());
		mutation.visit(new AbstractGameStateMutation.Visitor<Element, GameComponentBuilderException>() {

			@Override
			public Element visit(GameStateAddRelationship addRelationship) throws GameComponentBuilderException {
				final Element mutationElement = createAndAppendElement(mutationsElement, addRelationship, STATE_ELEMENT_QNAME_LOG_MUTATION_REL_ADD);
				return writeRelationshipMutation(mutationElement, addRelationship);
			}

			@Override
			public Element visit(GameStateRemoveRelationship removeRelationship) throws GameComponentBuilderException {
				final Element mutationElement = createAndAppendElement(mutationsElement, removeRelationship, STATE_ELEMENT_QNAME_LOG_MUTATION_REL_REM);
				return writeRelationshipMutation(mutationElement, removeRelationship);
			}

		});
	}

	public Element writeRelationshipMutation(Element mutationElement, GameStateRelationshipMutation relationshipMutation) throws GameComponentBuilderException {
		final GamePartRelationshipType relType = relationshipMutation.getRelationshipType().get();
		mutationElement.setAttribute(STATE_ATTR_NAME_LOG_REL_TYPE, prefixFor(relType.getDeclaringPlugin()) + ":" + relType.getLocalName());

		final GamePlace src = relationshipMutation.getSource();
		final GamePlaceType srcType = src.getPrototype().get().getType().get();
		mutationElement.setAttribute(STATE_ATTR_NAME_LOG_MUTATION_REL_SRC,
				idFor(src.getOwner()) + "/" + prefixFor(srcType.getDeclaringPlugin()) + ":" + srcType.getLocalName());

		final GamePlace dst = relationshipMutation.getDestination();
		final GamePlaceType dstType = dst.getPrototype().get().getType().get();
		mutationElement.setAttribute(STATE_ATTR_NAME_LOG_MUTATION_REL_DST,
				idFor(dst.getOwner()) + "/" + prefixFor(dstType.getDeclaringPlugin()) + ":" + dstType.getLocalName());
		return mutationElement;
	}

	private String externalIdForRole(GameRole role) {
		return PREFIX_MODEL + ":" + m_modelResolver.getRoleResolver().lookupId(role).orElseThrow(missingComponent(role));
	}

	private String externalIdForPartPrototype(GameComponentRef<GamePartPrototype> componentRef) {
		return PREFIX_MODEL + ":" + m_modelResolver.getPartPrototypeResolver().lookupId(componentRef).orElseThrow(missingComponent(componentRef));
	}

	private String externalIdForStage(GameComponentRef<GameStage> componentRef) throws GameComponentBuilderException {
		return PREFIX_MODEL + ":" + m_modelResolver.getStageResolver().lookupId(componentRef).orElseThrow(missingComponent(componentRef));
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
