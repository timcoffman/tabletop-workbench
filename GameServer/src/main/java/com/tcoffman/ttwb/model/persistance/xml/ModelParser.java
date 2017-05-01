package com.tcoffman.ttwb.model.persistance.xml;

import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_NS;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_ABSTRACT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_BINDING;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_DOC;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_EXTENDS;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_ID;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_IS_TERMINAL;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_PROTOTYPE_REF;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_RESULT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_STAGE_REF;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_TYPE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME;
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
import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;
import static com.tcoffman.ttwb.plugin.CorePlugins.DOC_PARSER_XML;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.xml.AbstractGameParser;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.doc.StandardComponentDocumentation;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefResolver;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.StandardGameModel;
import com.tcoffman.ttwb.model.StandardGameModelComponent;
import com.tcoffman.ttwb.model.StandardGamePartInstance;
import com.tcoffman.ttwb.model.StandardGamePartPrototype;
import com.tcoffman.ttwb.model.StandardGamePlacePrototype;
import com.tcoffman.ttwb.model.StandardGameRule;
import com.tcoffman.ttwb.model.StandardGameStage;
import com.tcoffman.ttwb.model.pattern.StandardGameOperationPattern;
import com.tcoffman.ttwb.model.pattern.StandardGameRolePattern;
import com.tcoffman.ttwb.model.persistance.ModelRefManager;
import com.tcoffman.ttwb.persistence.xml.EventDispatcher;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.state.GameOperation;
import com.tcoffman.ttwb.state.pattern.StandardGameAnyRolePattern;

class ModelParser extends AbstractGameParser {
	private final StandardGameModel.Editor m_editor;
	private final DocumentationRefResolver m_documentationRefResolver;
	private final ModelRefManager m_modelRefManager;

	public ModelParser(StandardGameModel.Editor editor, DocumentationRefResolver documentationRefResolver, ModelRefManager refManager) {
		super();
		m_editor = editor;
		m_documentationRefResolver = documentationRefResolver;
		m_modelRefManager = refManager;
	}

	@Override
	protected final ModelPlugin requireModelPlugin(final PluginName pluginName) throws GameComponentBuilderException {
		final ModelPlugin plugin;
		try {
			plugin = m_modelRefManager.requireModelPlugin(pluginName);
		} catch (final PluginException ex) {
			throw new GameComponentBuilderException(ex.getPluginName(), ex);
		}
		m_editor.addRequiredPlugin(plugin);
		return plugin;
	}

	@Override
	public void parse(EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException, GameComponentBuilderException {
		dispatcher.on(MODEL_ELEMENT_QNAME, this::parseModel);
		dispatcher.other((e, d) -> d.skip());
		dispatcher.read();
	}

	void parseModel(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
			GameComponentBuilderException {

		dispatcher.on(MODEL_ELEMENT_QNAME_NAME, this::parseName);
		dispatcher.on(MODEL_ELEMENT_QNAME_ROLE, this::parseRole);
		dispatcher.on(MODEL_ELEMENT_QNAME_PROTOTYPE, this::parsePrototype);
		dispatcher.on(MODEL_ELEMENT_QNAME_PARTS, this::parseParts);
		dispatcher.on(MODEL_ELEMENT_QNAME_INITIAL_STAGE, this::parseInitialStage);
		dispatcher.on(MODEL_ELEMENT_QNAME_STAGE, this::parseStage);
		dispatcher.other((e, d) -> d.skip());
		dispatcher.read();
		m_modelRefManager.resolveAll();
	}

	private void parseInitialStage(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
			XMLStreamException {
		dispatcher.attr(MODEL_ATTR_NAME_STAGE_REF, (id) -> m_editor.setInitialStage(m_modelRefManager.getStageManager().createRef(id)));
		dispatcher.read();
	}

	private void parseStage(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
			XMLStreamException {
		createAndInitialize(m_editor::createStage, (s) -> new StageParser(s).parse(startElement, dispatcher));
	}

	private void parseName(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException {
		m_editor.setName(dispatcher.contents());
	}

	private void parseRole(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
			XMLStreamException {
		createAndInitialize(m_editor::createRole, (r) -> {

			dispatcher.attr(MODEL_ATTR_NAME_ID, (id) -> r.completed((role) -> m_modelRefManager.getRoleManager().register(role, id)));
			dispatcher.read();
		});

	}

	private void parsePrototype(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
			XMLStreamException {
		final Creator<StandardGamePartPrototype.Editor> creator = (i) -> m_editor.createPrototype(CORE, i);
		createAndInitialize(creator, (p) -> new PartPrototypeParser(p).parse(startElement, dispatcher));
	}

	private void parseParts(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
			XMLStreamException {
		dispatcher.on(MODEL_ELEMENT_QNAME_PART, this::parsePart);
		dispatcher.read();
	}

	private void parsePart(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
			XMLStreamException {
		createAndInitialize(m_editor::createPart, (p) -> new PartParser(p).parse(startElement, dispatcher));
	}

	private GameComponentRef<GameComponentDocumentation> defaultDocumentation(StartElement startElement) throws GameComponentBuilderException {
		return defaultDocumentation(startElement, MODEL_ATTR_NAME_ID);
	}

	private GameComponentRef<GameComponentDocumentation> defaultDocumentation(StartElement startElement, String identifyingAttrName)
			throws GameComponentBuilderException {
		final StandardComponentDocumentation doc = StandardComponentDocumentation
				.create()
				.setName(
						GameComponentDocumentation.Format.SHORT,
						startElement.getName().getLocalPart() + "#"
								+ Optional.ofNullable(startElement.getAttributeByName(new QName(identifyingAttrName))).map(Attribute::getValue).orElse(""))
								.setDescription(
										startElement.getName().getLocalPart() + "@" + startElement.getLocation().getLineNumber() + ":"
												+ startElement.getLocation().getColumnNumber()).done();
		return () -> doc;
	}

	private class PartPrototypeParser {
		private final StandardGamePartPrototype.Editor m_editor;

		public PartPrototypeParser(StandardGamePartPrototype.Editor editor) {
			m_editor = editor;
		}

		public void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {

			m_editor.setDocumentation(defaultDocumentation(startElement));

			dispatcher.on(MODEL_ELEMENT_QNAME_PLACE, this::parsePlace);
			dispatcher.attr(MODEL_ATTR_NAME_DOC, (doc) -> m_editor.setDocumentation(lookupPrototoypeDocumentation(doc, startElement.getNamespaceContext())));
			dispatcher.attr(MODEL_ATTR_NAME_ABSTRACT, (abs) -> m_editor.setAbstract("yes".equals(abs)));
			dispatcher.attr(MODEL_ATTR_NAME_EXTENDS, (id) -> m_editor.setExtends(m_modelRefManager.getPartPrototypeManager().createRef(id)));
			dispatcher.attr(MODEL_ATTR_NAME_BINDING, (id) -> m_editor.setBinding(m_modelRefManager.getRoleManager().createRef(id)));
			dispatcher.attr(MODEL_ATTR_NAME_ID, (id) -> m_editor.completed((prototype) -> m_modelRefManager.getPartPrototypeManager().register(prototype, id)));
			dispatcher.read();

		}

		private void parsePlace(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {
			createAndInitialize(m_editor::createPlace, (p) -> new PlaceParser(p).parse(startElement, dispatcher));
		}
	}

	private class PartParser {
		private final StandardGamePartInstance.Editor m_editor;

		public PartParser(StandardGamePartInstance.Editor editor) {
			m_editor = editor;
		}

		public void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {

			dispatcher.attr(MODEL_ATTR_NAME_PROTOTYPE_REF, (id) -> m_editor.setPrototype(m_modelRefManager.getPartPrototypeManager().createRef(id)));
			dispatcher.attr(MODEL_ATTR_NAME_BINDING, (id) -> m_editor.setBinding(m_modelRefManager.getRoleManager().createRef(id)));
			dispatcher.attr(MODEL_ATTR_NAME_ID, (id) -> {
			});
			dispatcher.read();
		}
	}

	private class PlaceParser {
		private final StandardGamePlacePrototype.Editor m_editor;

		public PlaceParser(StandardGamePlacePrototype.Editor editor) {
			m_editor = editor;
		}

		private void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {

			m_editor.setDocumentation(defaultDocumentation(startElement, MODEL_ATTR_NAME_TYPE));

			dispatcher.attr(MODEL_ATTR_NAME_DOC, (doc) -> m_editor.setDocumentation(lookupPrototoypeDocumentation(doc, startElement.getNamespaceContext())));
			dispatcher.attr(MODEL_ATTR_NAME_TYPE, (type) -> m_editor.setType(parseType(startElement, type)));
			dispatcher.attr(this::parseProperty);
			dispatcher.other(this::parseComponent);
			dispatcher.read();
		}

		private GameComponentRef<GamePlaceType> parseType(StartElement startElement, String type) throws GameComponentBuilderException {
			return parseRef(type, startElement, (p, n) -> p.getPlaceType(n));
		}

		private void parseProperty(QName name, String value) throws XMLStreamException, GameComponentBuilderException {
			final ModelPlugin modelPlugin = requireModelPlugin(name);
			m_editor.createProperty(modelPlugin.getName(), name.getLocalPart(), (p) -> p.setValue(value));
		}

		private void parseComponent(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {
			final ModelPlugin modelPlugin = requireModelPlugin(startElement);
			final Creator<StandardGameModelComponent.Editor> creator = (i) -> m_editor.createComponent(modelPlugin.getName(), startElement.getName()
					.getLocalPart(), i);
			createAndInitialize(creator, (c) -> {

				new ComponentParser(c).parse(startElement, dispatcher);

			});
		}
	}

	private class ComponentParser {
		private final StandardGameModelComponent.Editor m_editor;

		public ComponentParser(StandardGameModelComponent.Editor editor) {
			m_editor = editor;
		}

		private void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {
			dispatcher.attr(this::parseProperty);
			dispatcher.other(this::parseComponent);
			dispatcher.read();
		}

		private void parseProperty(QName name, String value) throws GameComponentBuilderException, XMLStreamException {
			final ModelPlugin modelPlugin = requireModelPlugin(name);
			m_editor.createProperty(modelPlugin.getName(), name.getLocalPart(), (p) -> p.setValue(value));
		}

		private void parseComponent(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {
			final ModelPlugin modelPlugin = requireModelPlugin(startElement);
			final Creator<StandardGameModelComponent.Editor> creator = (i) -> m_editor.createComponent(modelPlugin.getName(), startElement.getName()
					.getLocalPart(), i);
			createAndInitialize(creator, (c) -> {

				new ComponentParser(c).parse(startElement, dispatcher);

			});
		}
	}

	private class StageParser {
		private final StandardGameStage.Editor m_editor;

		public StageParser(StandardGameStage.Editor editor) {
			m_editor = editor;
		}

		private void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {

			dispatcher.attr(MODEL_ATTR_NAME_IS_TERMINAL, (t) -> m_editor.setTerminal("yes".equals(t)));
			dispatcher.on(MODEL_ELEMENT_QNAME_STAGE, this::parseStage);
			dispatcher.on(MODEL_ELEMENT_QNAME_RULE, this::parseRule);
			dispatcher.on(MODEL_ELEMENT_QNAME_INITIAL_STAGE, this::parseInitialStage);
			dispatcher.attr(MODEL_ATTR_NAME_ID, (id) -> m_editor.completed((stage) -> m_modelRefManager.getStageManager().register(stage, id)));
			dispatcher.read();

		}

		private void parseRule(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {
			final Creator<StandardGameRule.Editor> creator = (i) -> m_editor.createRule(CORE, i);
			createAndInitialize(creator, (r) -> {

				new RuleParser(r).parse(startElement, dispatcher);

			});
		}

		private void parseInitialStage(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {

			dispatcher.attr(MODEL_ATTR_NAME_STAGE_REF, (id) -> {
			});
			dispatcher.read();
		}

		private void parseStage(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {
			createAndInitialize(m_editor::createStage, (s) -> new StageParser(s).parse(startElement, dispatcher));
		}
	}

	private class RuleParser {
		private final StandardGameRule.Editor m_editor;

		public RuleParser(StandardGameRule.Editor editor) {
			m_editor = editor;
		}

		private void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {

			dispatcher.attr(MODEL_ATTR_NAME_RESULT, (id) -> m_editor.setResult(m_modelRefManager.getStageManager().createRef(id)));
			dispatcher.on(MODEL_ELEMENT_QNAME_ROLE, this::parseRolePattern);
			dispatcher.on(MODEL_ELEMENT_QNAME_OP_SIGNAL, (e, d) -> parseOperationPattern(e, d, GameOperation.Type.SIGNAL));
			dispatcher.on(MODEL_ELEMENT_QNAME_OP_MOVE, (e, d) -> parseOperationPattern(e, d, GameOperation.Type.MOVE));
			dispatcher.on(MODEL_ELEMENT_QNAME_OP_ORIENT, (e, d) -> parseOperationPattern(e, d, GameOperation.Type.ORIENT));
			dispatcher.on(MODEL_ELEMENT_QNAME_OP_SPLIT, (e, d) -> parseOperationPattern(e, d, GameOperation.Type.SPLIT));
			dispatcher.on(MODEL_ELEMENT_QNAME_OP_JOIN, (e, d) -> parseOperationPattern(e, d, GameOperation.Type.JOIN));
			dispatcher.read();

		}

		private void parseRolePattern(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException {
			dispatcher.skip();
		}

		private void parseOperationPattern(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher, GameOperation.Type type)
				throws GameComponentBuilderException, XMLStreamException {

			createAndInitialize(m_editor::createOperationPattern, (p) -> {

				p.setType(type);
				new OperationPatternParser(p).parse(startElement, dispatcher);

			});

		}

	}

	private class OperationPatternParser {
		private final StandardGameOperationPattern.Editor m_editor;

		public OperationPatternParser(StandardGameOperationPattern.Editor editor) {
			m_editor = editor;
		}

		private void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {

			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_ROLE, this::parseRole);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_SUBJECT, this::parseSubject);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_TARGET, this::parseTarget);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_QUANTITY, this::parseQuantity);
			dispatcher.read();

		}

		private void parseRole(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {
			final StandardGameRolePattern rolePattern = StandardGameAnyRolePattern.create().done();
			m_editor.setRolePattern(rolePattern);
			dispatcher.skip();
		}

		private void parseSubject(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {
			dispatcher.skip();
		}

		private void parseTarget(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {
			dispatcher.skip();
		}

		private void parseQuantity(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
				XMLStreamException {
			dispatcher.skip();
		}

	}

	private GameComponentRef<GameComponentDocumentation> lookupPrototoypeDocumentation(String externalId, NamespaceContext nsCtx)
			throws GameComponentBuilderException {
		return lookupComponentDocumentation(externalId, m_documentationRefResolver.getPrototypeResolver()::lookup, nsCtx);
	}

	private GameComponentRef<GameComponentDocumentation> lookupRuleDocumentation(String externalId, NamespaceContext nsCtx)
			throws GameComponentBuilderException {
		return lookupComponentDocumentation(externalId, m_documentationRefResolver.getRuleResolver()::lookup, nsCtx);
	}

	private GameComponentRef<GameComponentDocumentation> lookupRoleDocumentation(String externalId, NamespaceContext nsCtx)
			throws GameComponentBuilderException {
		return lookupComponentDocumentation(externalId, m_documentationRefResolver.getRoleResolver()::lookup, nsCtx);
	}

	private GameComponentRef<GameComponentDocumentation> lookupStageDocumentation(String externalId, NamespaceContext nsCtx)
			throws GameComponentBuilderException {
		return lookupComponentDocumentation(externalId, m_documentationRefResolver.getStageResolver()::lookup, nsCtx);
	}

	private <T> GameComponentRef<T> lookupComponentDocumentation(String externalId, Function<String, Optional<GameComponentRef<T>>> lookup,
			NamespaceContext nsCtx) throws GameComponentBuilderException {
		final String[] idParts = externalId.split(":");
		if (idParts.length == 0)
			throw new GameComponentBuilderException(DOC_PARSER_XML, "missing documentation id");
		if (idParts.length == 1)
			return lookup.apply(idParts[0]).orElseThrow(missingComponent(idParts[0]));
		final String ns = nsCtx.getNamespaceURI(idParts[0]);
		if (DOC_NS.equals(ns))
			return lookup.apply(idParts[1]).orElseThrow(missingComponent(idParts[1]));
		else
			throw new GameComponentBuilderException(DOC_PARSER_XML, "unrecognized namespace \"" + ns + "\" for documentation id \"" + externalId + "\"");
	}

	private Supplier<GameComponentBuilderException> missingComponent(String id) {
		return () -> new GameComponentBuilderException(DOC_PARSER_XML, "missing component \"" + id + "\"");
	}

}