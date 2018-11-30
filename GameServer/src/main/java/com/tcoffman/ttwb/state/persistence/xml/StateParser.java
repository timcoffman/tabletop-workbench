package com.tcoffman.ttwb.state.persistence.xml;

import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_NS;
import static com.tcoffman.ttwb.plugin.CorePlugins.STATE_PARSER_XML;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_BINDING;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_ID;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_LOG_FORWARD_RESULT;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_LOG_MUTATION_REL_DST;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_LOG_MUTATION_REL_SRC;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_LOG_OPERATION_TYPE;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_LOG_ROLE;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_LOG_ROLLBACK_RESULT;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_PROTOTYPE_REF;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_REL_DST;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_REL_SRC;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_REL_TYPE;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_STAGE_REF;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_CURRENT_STAGE;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_LOG;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_LOG_ENTRY;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_LOG_FORWARD_MUTATIONS;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_LOG_MUTATION_REL_ADD;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_LOG_MUTATION_REL_REM;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_LOG_ROLLBACK_MUTATIONS;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_PART;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_PARTS;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_RELATIONSHIP;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_RELATIONSHIPS;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

import com.tcoffman.ttwb.component.GameComponent;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.component.persistence.xml.AbstractGameParser;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.pattern.part.StandardFilterPartPattern;
import com.tcoffman.ttwb.model.pattern.quantity.StandardAnyQuantityPattern;
import com.tcoffman.ttwb.persistence.xml.EventDispatcher;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePlace;
import com.tcoffman.ttwb.state.StandardGameState;
import com.tcoffman.ttwb.state.StandardPartRelationship;
import com.tcoffman.ttwb.state.mutation.GameOperation;
import com.tcoffman.ttwb.state.mutation.GameStateAddRelationship;
import com.tcoffman.ttwb.state.mutation.GameStateLogEntry;
import com.tcoffman.ttwb.state.mutation.GameStateMutation;
import com.tcoffman.ttwb.state.mutation.GameStateRemoveRelationship;
import com.tcoffman.ttwb.state.persistence.StateRefManager;

class StateParser extends AbstractGameParser {
	private final ModelRefResolver m_modelRefResolver;
	private final StandardGameState.Resetter m_resetter;
	private final StateRefManager m_externalRefManager;

	public StateParser(ModelRefResolver modelRefResolver, StandardGameState.Resetter resetter, StateRefManager externalRefManager) {
		super();
		m_modelRefResolver = modelRefResolver;
		m_externalRefManager = externalRefManager;
		m_resetter = resetter;
	}

	@Override
	protected final ModelPlugin requireModelPlugin(final PluginName pluginName) throws GameComponentBuilderException {
		final ModelPlugin plugin;
		try {
			plugin = m_modelRefResolver.requireModelPlugin(pluginName);
		} catch (final PluginException ex) {
			throw new GameComponentBuilderException(ex.getPluginName(), ex);
		}
		return plugin;
	}

	@Override
	public void parse(EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException, GameComponentBuilderException {

		dispatcher.on(STATE_ELEMENT_QNAME_PARTS, this::parseParts);
		dispatcher.on(STATE_ELEMENT_QNAME_RELATIONSHIPS, this::parseRelationships);
		dispatcher.on(STATE_ELEMENT_QNAME_CURRENT_STAGE, this::parseCurrentStage);
		dispatcher.on(STATE_ELEMENT_QNAME_LOG, this::parseLogEntries);
		dispatcher.read();
		m_externalRefManager.resolveAll();
	}

	private void parseCurrentStage(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
			GameComponentBuilderException {
		dispatcher.attr(STATE_ATTR_NAME_STAGE_REF, (id) -> m_resetter.setCurrentStage(lookupStage(id, startElement.getNamespaceContext())));
		dispatcher.read();
	}

	private void parseParts(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
			GameComponentBuilderException {
		dispatcher.on(STATE_ELEMENT_QNAME_PART, this::parsePart);
		dispatcher.read();
	}

	private void parsePart(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
			GameComponentBuilderException {
		final Attribute idAttr = startElement.getAttributeByName(QName.valueOf(STATE_ATTR_NAME_ID));
		if (null == idAttr) {
			dispatcher.skip();
			return;
		}

		final StandardFilterPartPattern.Editor p = StandardFilterPartPattern.create();
		p.setQuantityPattern(StandardAnyQuantityPattern.create().done());
		dispatcher.attr(STATE_ATTR_NAME_PROTOTYPE_REF, (id) -> p.setMatchPrototype(lookupPrototype(id, startElement.getNamespaceContext())));
		dispatcher.attr(STATE_ATTR_NAME_BINDING, (id) -> p.setMatchBinding(lookupRole(id, startElement.getNamespaceContext())));
		dispatcher.read();

		final GamePart part = m_resetter.takePart(p.done());
		m_externalRefManager.getPartManager().register(part, idAttr.getValue());
	}

	private void parseRelationships(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
	GameComponentBuilderException {
		dispatcher.on(STATE_ELEMENT_QNAME_RELATIONSHIP, this::parseRelationship);
		dispatcher.read();
	}

	private void parseRelationship(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
			GameComponentBuilderException {
		createAndInitialize(m_resetter::createRelationship, (r) -> new RelationshipParser(r).parse(startElement, dispatcher));
	}

	private class RelationshipParser {
		private final StandardPartRelationship.Editor m_editor;

		public RelationshipParser(StandardPartRelationship.Editor editor) {
			m_editor = editor;
		}

		public void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
		XMLStreamException {

			dispatcher.attr(STATE_ATTR_NAME_REL_SRC, (qn, v) -> m_editor.setSource(parsePlaceRef(v, startElement)));
			dispatcher.attr(STATE_ATTR_NAME_REL_DST, (qn, v) -> m_editor.setDestination(parsePlaceRef(v, startElement)));
			dispatcher.attr(STATE_ATTR_NAME_REL_TYPE, (qn, v) -> m_editor.setType(parseRelationshipType(v, startElement)));
			dispatcher.read();
		}

		public GameComponentRef<GamePlace> parsePlaceRef(String placeRef, StartElement startElement) throws GameComponentBuilderException, XMLStreamException {
			final String[] placeRefParts = placeRef.split("/");
			if (placeRefParts.length < 2)
				throw new GameComponentBuilderException(STATE_PARSER_XML, "incomplete place reference");
			final GameComponentRef<GamePart> part = m_externalRefManager.getPartManager().createRef(placeRefParts[0]);

			final GameComponentRef<GamePlaceType> placeType = parsePlaceType(placeRefParts[1], startElement);
			return GameComponentRef.deferred(() -> part.get().findPlace(placeType));
		}

		public GameComponentRef<GamePlaceType> parsePlaceType(String placeTypeRef, StartElement startElement) throws GameComponentBuilderException,
				XMLStreamException {
			return parseRef(placeTypeRef, startElement, (p, n) -> p.getPlaceType(n));
		}

		public GameComponentRef<GamePartRelationshipType> parseRelationshipType(String placeTypeRef, StartElement startElement)
				throws GameComponentBuilderException, XMLStreamException {
			return parseRef(placeTypeRef, startElement, (p, n) -> p.getRelationshipType(n));
		}
	}

	private void parseLogEntries(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
			GameComponentBuilderException {
		dispatcher.on(STATE_ELEMENT_QNAME_LOG_ENTRY, this::parseLogEntry);
		dispatcher.read();
	}

	private void parseLogEntry(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
			GameComponentBuilderException {
		final GameComponentRef<GameStage> forward = lookupStage(startElement.getAttributeByName(QName.valueOf(STATE_ATTR_NAME_LOG_FORWARD_RESULT)).getValue(),
				startElement.getNamespaceContext());
		final GameComponentRef<GameStage> rollback = lookupStage(
				startElement.getAttributeByName(QName.valueOf(STATE_ATTR_NAME_LOG_ROLLBACK_RESULT)).getValue(), startElement.getNamespaceContext());
		final GameStateLogEntry logEntry = new GameStateLogEntry(forward, rollback);

		new LogEntryParser(logEntry).parse(startElement, dispatcher);
		m_resetter.appendLogEntry(logEntry);
	}

	private class LogEntryParser {
		private final GameStateLogEntry m_logEntry;
		private final List<GameStateMutation> m_forwardMutations = new ArrayList<GameStateMutation>();
		private final List<GameStateMutation> m_rollbackMutations = new ArrayList<GameStateMutation>();

		public LogEntryParser(GameStateLogEntry logEntry) {
			m_logEntry = logEntry;
		}

		public void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {
			dispatcher.on(STATE_ELEMENT_QNAME_LOG_FORWARD_MUTATIONS, (s, d) -> parseMutations(s, d, m_forwardMutations::add));
			dispatcher.on(STATE_ELEMENT_QNAME_LOG_ROLLBACK_MUTATIONS, (s, d) -> parseMutations(s, d, m_rollbackMutations::add));
			dispatcher.read();
			final Iterator<GameStateMutation> i = m_forwardMutations.iterator();
			final ListIterator<GameStateMutation> j = m_rollbackMutations.listIterator(m_rollbackMutations.size());
			m_logEntry.apply(i.next(), j.previous());
		}

		private void parseMutations(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher, Consumer<GameStateMutation> consumer)
				throws GameComponentBuilderException, XMLStreamException {
			final String roleId = startElement.getAttributeByName(QName.valueOf(STATE_ATTR_NAME_LOG_ROLE)).getValue();
			final GameComponentRef<GameRole> role = lookupRole(roleId, startElement.getNamespaceContext());
			final String operationTypeName = startElement.getAttributeByName(QName.valueOf(STATE_ATTR_NAME_LOG_OPERATION_TYPE)).getValue();
			final GameOperation.Type operationType = GameOperation.Type.valueOf(operationTypeName);
			dispatcher.on(STATE_ELEMENT_QNAME_LOG_MUTATION_REL_ADD, (s, d) -> parseRelAddMutation(s, d, role.get(), operationType, consumer));
			dispatcher.on(STATE_ELEMENT_QNAME_LOG_MUTATION_REL_REM, (s, d) -> parseRelRemMutation(s, d, role.get(), operationType, consumer));
			dispatcher.read();
		}

		private void parseRelAddMutation(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher, GameRole role,
				GameOperation.Type operationType, Consumer<GameStateMutation> consumer) throws GameComponentBuilderException, XMLStreamException {
			final String relTypeId = startElement.getAttributeByName(QName.valueOf(STATE_ATTR_NAME_REL_TYPE)).getValue();
			final String sourceId = startElement.getAttributeByName(QName.valueOf(STATE_ATTR_NAME_LOG_MUTATION_REL_SRC)).getValue();
			final String destinationId = startElement.getAttributeByName(QName.valueOf(STATE_ATTR_NAME_LOG_MUTATION_REL_DST)).getValue();
			dispatcher.read();
			final GameComponentRef<GamePartRelationshipType> relationshipType = parseRef(relTypeId, startElement, (p, n) -> p.getRelationshipType(n));
			final GamePlace source = parsePlaceRef(startElement, sourceId);
			final GamePlace destination = parsePlaceRef(startElement, destinationId);
			final GameStateMutation mutation = new GameStateAddRelationship(role, operationType, relationshipType, source, destination);
			consumer.accept(mutation);
		}

		private void parseRelRemMutation(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher, GameRole role,
				GameOperation.Type operationType, Consumer<GameStateMutation> consumer) throws GameComponentBuilderException, XMLStreamException {
			final String relTypeId = startElement.getAttributeByName(QName.valueOf(STATE_ATTR_NAME_REL_TYPE)).getValue();
			final String sourceId = startElement.getAttributeByName(QName.valueOf(STATE_ATTR_NAME_LOG_MUTATION_REL_SRC)).getValue();
			final String destinationId = startElement.getAttributeByName(QName.valueOf(STATE_ATTR_NAME_LOG_MUTATION_REL_DST)).getValue();
			dispatcher.read();
			final GameComponentRef<GamePartRelationshipType> relationshipType = parseRef(relTypeId, startElement, (p, n) -> p.getRelationshipType(n));
			final GamePlace source = parsePlaceRef(startElement, sourceId);
			final GamePlace destination = parsePlaceRef(startElement, destinationId);
			final GameStateMutation mutation = new GameStateRemoveRelationship(role, operationType, relationshipType, source, destination);
			consumer.accept(mutation);
		}

		private GamePlace parsePlaceRef(StartElement startElement, String placeId) throws GameComponentBuilderException {
			final String[] idParts = placeId.split("/");
			final GameComponentRef<GamePart> part = m_externalRefManager.getPartResolver().lookup(idParts[0]).orElseThrow(missingComponent(idParts[0]));
			final GameComponentRef<GamePlaceType> placeType = parseRef(idParts[1], startElement, (p, n) -> p.getPlaceType(n));
			return part.get().findPlace(placeType);
		}

	}

	private GameComponentRef<GameStage> lookupStage(String externalId, NamespaceContext nsCtx) throws GameComponentBuilderException {
		return lookupComponent(externalId, m_modelRefResolver.getStageResolver()::lookup, nsCtx);
	}

	private GameComponentRef<GamePartPrototype> lookupPrototype(String externalId, NamespaceContext nsCtx) throws GameComponentBuilderException {
		return lookupComponent(externalId, m_modelRefResolver.getPartPrototypeResolver()::lookup, nsCtx);
	}

	private GameComponentRef<GameRole> lookupRole(String externalId, NamespaceContext nsCtx) throws GameComponentBuilderException {
		return lookupComponent(externalId, m_modelRefResolver.getRoleResolver()::lookup, nsCtx);
	}

	private <T extends GameComponent> GameComponentRef<T> lookupComponent(String externalId, Function<String, Optional<GameComponentRef<T>>> lookup,
			NamespaceContext nsCtx) throws GameComponentBuilderException {
		final String[] idParts = externalId.split(":");
		if (idParts.length == 0)
			throw new GameComponentBuilderException(STATE_PARSER_XML, "missing component id");
		if (idParts.length == 1)
			return lookup.apply(idParts[0]).orElseThrow(missingComponent(idParts[0]));
		final String ns = nsCtx.getNamespaceURI(idParts[0]);
		if (MODEL_NS.equals(ns))
			return lookup.apply(idParts[1]).orElseThrow(missingComponent(idParts[1]));
		else
			throw new GameComponentBuilderException(STATE_PARSER_XML, "unrecognized namespace \"" + ns + "\" for component id \"" + externalId + "\"");
	}

	private Supplier<GameComponentBuilderException> missingComponent(String id) {
		return () -> new GameComponentBuilderException(STATE_PARSER_XML, "missing component \"" + id + "\"");
	}
}