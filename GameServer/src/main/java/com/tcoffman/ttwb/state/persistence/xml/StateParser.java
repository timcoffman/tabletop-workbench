package com.tcoffman.ttwb.state.persistence.xml;

import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_NS;
import static com.tcoffman.ttwb.plugin.CorePlugins.STATE_PARSER_XML;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_BINDING;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_DST;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_ID;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_PROTOTYPE_REF;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_SRC;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_STAGE_REF;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_TYPE;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_CURRENT_STAGE;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_PART;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_PARTS;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_RELATIONSHIP;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ELEMENT_QNAME_RELATIONSHIPS;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.StandardComponentRefManager;
import com.tcoffman.ttwb.component.persistence.xml.AbstractGameParser;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.persistence.xml.EventDispatcher;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePlace;
import com.tcoffman.ttwb.state.StandardGameState;
import com.tcoffman.ttwb.state.StandardPartRelationship;
import com.tcoffman.ttwb.state.pattern.StandardFilterPartPattern;

class StateParser extends AbstractGameParser {
	private final ModelRefResolver m_modelRefResolver;
	private final StandardGameState.Resetter m_resetter;
	private final StandardComponentRefManager<GamePart> m_partManager = new StandardComponentRefManager<GamePart>("part");

	public StateParser(ModelRefResolver modelRefResolver, StandardGameState.Resetter resetter) {
		super();
		m_modelRefResolver = modelRefResolver;
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
		dispatcher.on(STATE_ELEMENT_QNAME, this::parseState);
		dispatcher.other((e, d) -> d.skip());
		dispatcher.read();
	}

	void parseState(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
			GameComponentBuilderException {

		dispatcher.on(STATE_ELEMENT_QNAME_PARTS, this::parseParts);
		dispatcher.on(STATE_ELEMENT_QNAME_RELATIONSHIPS, this::parseRelationships);
		dispatcher.on(STATE_ELEMENT_QNAME_CURRENT_STAGE, this::parseCurrentStage);
		dispatcher.other((e, d) -> d.skip());
		dispatcher.read();
		m_partManager.resolveAll();
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
		dispatcher.attr(STATE_ATTR_NAME_PROTOTYPE_REF, (id) -> p.setMatchPrototype(lookupPrototype(id, startElement.getNamespaceContext())));
		dispatcher.attr(STATE_ATTR_NAME_BINDING, (id) -> p.setMatchBinding(lookupRole(id, startElement.getNamespaceContext())));
		dispatcher.read();

		final GamePart part = m_resetter.takePart(p.done());
		m_partManager.register(part, idAttr.getValue());
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

			dispatcher.attr(STATE_ATTR_NAME_SRC, (qn, v) -> m_editor.setSource(parsePlaceRef(v, startElement)));
			dispatcher.attr(STATE_ATTR_NAME_DST, (qn, v) -> m_editor.setDestination(parsePlaceRef(v, startElement)));
			dispatcher.attr(STATE_ATTR_NAME_TYPE, (qn, v) -> m_editor.setType(parseRelationshipType(v, startElement)));
			dispatcher.read();
		}

		public GameComponentRef<GamePlace> parsePlaceRef(String placeRef, StartElement startElement) throws GameComponentBuilderException, XMLStreamException {
			final String[] placeRefParts = placeRef.split("/");
			if (placeRefParts.length < 2)
				throw new GameComponentBuilderException(STATE_PARSER_XML, "incomplete place reference");
			final GameComponentRef<GamePart> part = m_partManager.createRef(placeRefParts[0]);

			final GameComponentRef<GamePlaceType> placeType = parsePlaceType(placeRefParts[1], startElement);
			return () -> part.get().findPlace(placeType);
		}

		public GameComponentRef<GamePlaceType> parsePlaceType(String placePattern, StartElement startElement) throws GameComponentBuilderException,
				XMLStreamException {
			final java.util.regex.Pattern re = java.util.regex.Pattern.compile("([^\\[]+)|\\[([^\\]=]+)(=([^\\]]+))?\\]");
			final Matcher matcher = re.matcher(placePattern);
			if (!matcher.find())
				throw new GameComponentBuilderException(STATE_PARSER_XML, "missing place type");
			final String placeTypeRef = matcher.group(1);
			if (null == placeTypeRef || placeTypeRef.isEmpty())
				throw new GameComponentBuilderException(STATE_PARSER_XML, "missing place type");

			final GameComponentRef<GamePlaceType> placeType = parseRef(placeTypeRef, startElement, (p, n) -> p.getPlaceType(n));

			while (matcher.find()) {
				final String predicate = matcher.group(2);
				if (null == predicate || predicate.isEmpty())
					throw new GameComponentBuilderException(STATE_PARSER_XML, "missing place predicate");
			}

			return placeType;
		}

		public GameComponentRef<GamePartRelationshipType> parseRelationshipType(String placeTypeRef, StartElement startElement)
				throws GameComponentBuilderException, XMLStreamException {
			return parseRef(placeTypeRef, startElement, (p, n) -> p.getRelationshipType(n));
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

	private <T> GameComponentRef<T> lookupComponent(String externalId, Function<String, Optional<GameComponentRef<T>>> lookup, NamespaceContext nsCtx)
			throws GameComponentBuilderException {
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