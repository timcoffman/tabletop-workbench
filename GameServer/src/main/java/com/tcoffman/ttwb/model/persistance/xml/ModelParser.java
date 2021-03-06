package com.tcoffman.ttwb.model.persistance.xml;

import static com.tcoffman.ttwb.core.Core.TOKEN_ROOT;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_NS;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_ABSTRACT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_BINDING;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_DOC;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_EXTENDS;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_ID;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_IS_TERMINAL;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_MAXIMUM;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_MINIMUM;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_MODEL;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_PATTERN_TOKEN;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_PROTOTYPE_REF;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_RESULT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_STAGE_REF;
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
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_PART;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_QUANTITY;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_RELATED;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_RELATIONSHIP;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_ROLE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_ROOT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_SUBJECT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_TARGET;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_VARIABLE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PLACE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PROTOTYPE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_ROLE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_RULE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_STAGE;
import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;
import static com.tcoffman.ttwb.plugin.CorePlugins.DOC_PARSER_XML;

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
import com.tcoffman.ttwb.doc.persistence.DocumentationRefResolver;
import com.tcoffman.ttwb.component.persistence.xml.AbstractGameParser;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.doc.StandardComponentDocumentation;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.StandardGameModel;
import com.tcoffman.ttwb.model.StandardGameModelComponent;
import com.tcoffman.ttwb.model.StandardGamePartInstance;
import com.tcoffman.ttwb.model.StandardGamePartPrototype;
import com.tcoffman.ttwb.model.StandardGamePlacePrototype;
import com.tcoffman.ttwb.model.StandardGameRule;
import com.tcoffman.ttwb.model.StandardGameStage;
import com.tcoffman.ttwb.model.pattern.operation.StandardGameOperationPattern;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.part.StandardAnyPartPattern;
import com.tcoffman.ttwb.model.pattern.part.StandardFilterPartPattern;
import com.tcoffman.ttwb.model.pattern.part.StandardIntersectionPartPattern;
import com.tcoffman.ttwb.model.pattern.part.StandardVariablePartPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.model.pattern.place.StandardAnyPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.StandardFilterPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.StandardIntersectionPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.StandardRelationshipPlacePattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.StandardAnyQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.StandardRangeQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.StandardSingleQuantityPattern;
import com.tcoffman.ttwb.model.pattern.role.StandardGameAnyRolePattern;
import com.tcoffman.ttwb.model.pattern.role.StandardGameRolePattern;
import com.tcoffman.ttwb.model.persistance.ModelRefManager;
import com.tcoffman.ttwb.persistence.xml.EventDispatcher;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.state.mutation.GameOperation;

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

	void parseModel(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
			throws XMLStreamException, GameComponentBuilderException {
		m_editor.setDocumentation(m_documentationRefResolver.getModel());

		dispatcher.on(MODEL_ELEMENT_QNAME_IMPORT, this::parseImport);
		dispatcher.on(MODEL_ELEMENT_QNAME_ROLE, this::parseRole);
		dispatcher.on(MODEL_ELEMENT_QNAME_PROTOTYPE, this::parsePrototype);
		dispatcher.on(MODEL_ELEMENT_QNAME_PARTS, this::parseParts);
		dispatcher.on(MODEL_ELEMENT_QNAME_INITIAL_STAGE, this::parseInitialStage);
		dispatcher.on(MODEL_ELEMENT_QNAME_STAGE, this::parseStage);
		dispatcher.other((e, d) -> d.skip());
		dispatcher.read();
		m_modelRefManager.resolveAll();
	}

	private void parseImport(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
			throws GameComponentBuilderException, XMLStreamException {
		dispatcher.attr(MODEL_ATTR_NAME_MODEL,
				(id) -> m_editor.addImportedModel(m_modelRefManager.getImportedModelResolver().lookup(id).orElseThrow(missingComponent(id)).get()));
		dispatcher.read();
	}

	private void parseInitialStage(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
			throws GameComponentBuilderException, XMLStreamException {
		dispatcher.attr(MODEL_ATTR_NAME_STAGE_REF, (id) -> m_editor.setInitialStage(m_modelRefManager.getStageManager().createRef(id)));
		dispatcher.read();
	}

	private void parseStage(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
			throws GameComponentBuilderException, XMLStreamException {
		createAndInitialize(m_editor::createStage, (s) -> new StageParser(s).parse(startElement, dispatcher));
	}

	private void parseRole(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
			throws GameComponentBuilderException, XMLStreamException {
		createAndInitialize(m_editor::createRole, (r) -> {

			r.setDocumentation(defaultDocumentation(startElement, MODEL_ATTR_NAME_TYPE));

			dispatcher.attr(MODEL_ATTR_NAME_ID, (id) -> r.completed((role) -> m_modelRefManager.getRoleManager().register(role, id)));
			dispatcher.attr(MODEL_ATTR_NAME_DOC, (doc) -> r.setDocumentation(lookupRoleDocumentation(doc, startElement.getNamespaceContext())));
			dispatcher.read();
		});

	}

	private void parsePrototype(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
			throws GameComponentBuilderException, XMLStreamException {
		final Creator<StandardGamePartPrototype.Editor> creator = (i) -> m_editor.createPrototype(CORE, i);
		createAndInitialize(creator, (p) -> new PartPrototypeParser(p).parse(startElement, dispatcher));
	}

	private void parseParts(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
			throws GameComponentBuilderException, XMLStreamException {
		dispatcher.on(MODEL_ELEMENT_QNAME_PART, this::parsePart);
		dispatcher.read();
	}

	private void parsePart(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
			throws GameComponentBuilderException, XMLStreamException {
		createAndInitialize(m_editor::createPart, (p) -> new PartParser(p).parse(startElement, dispatcher));
	}

	private GameComponentRef<GameComponentDocumentation> defaultDocumentation(StartElement startElement) throws GameComponentBuilderException {
		return defaultDocumentation(startElement, MODEL_ATTR_NAME_ID);
	}

	private GameComponentRef<GameComponentDocumentation> defaultDocumentation(StartElement startElement, String identifyingAttrName)
			throws GameComponentBuilderException {
		final StandardComponentDocumentation doc = StandardComponentDocumentation.create()
				.setName(GameComponentDocumentation.Format.SHORT,
						Optional.ofNullable(startElement.getAttributeByName(new QName(identifyingAttrName))).map(Attribute::getValue).orElse(""))
				.setDescription(startElement.getName().getLocalPart() + "@" + startElement.getLocation().getLineNumber() + ":"
						+ startElement.getLocation().getColumnNumber())
				.done();
		return doc.self(GameComponentDocumentation.class);
	}

	private class PartPrototypeParser {
		private final StandardGamePartPrototype.Editor m_editor;

		public PartPrototypeParser(StandardGamePartPrototype.Editor editor) {
			m_editor = editor;
		}

		public void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {

			m_editor.setDocumentation(defaultDocumentation(startElement));

			dispatcher.on(MODEL_ELEMENT_QNAME_PLACE, this::parsePlace);
			dispatcher.attr(MODEL_ATTR_NAME_DOC, (doc) -> m_editor.setDocumentation(lookupPrototoypeDocumentation(doc, startElement.getNamespaceContext())));
			dispatcher.attr(MODEL_ATTR_NAME_ABSTRACT, (abs) -> m_editor.setAbstract("yes".equals(abs)));
			dispatcher.attr(MODEL_ATTR_NAME_EXTENDS, (id) -> m_editor.setExtends(m_modelRefManager.getPartPrototypeManager().createRef(id)));
			dispatcher.attr(MODEL_ATTR_NAME_BINDING, (id) -> m_editor.setBinding(m_modelRefManager.getRoleManager().createRef(id)));
			dispatcher.attr(MODEL_ATTR_NAME_ID, (id) -> m_editor.completed((prototype) -> m_modelRefManager.getPartPrototypeManager().register(prototype, id)));
			dispatcher.read();

		}

		private void parsePlace(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			createAndInitialize(m_editor::createPlace, (p) -> new PlaceParser(p).parse(startElement, dispatcher));
		}
	}

	private class PartParser {
		private final StandardGamePartInstance.Editor m_editor;

		public PartParser(StandardGamePartInstance.Editor editor) {
			m_editor = editor;
		}

		public void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {

			dispatcher.attr(MODEL_ATTR_NAME_PROTOTYPE_REF, (id) -> m_editor.setPrototype(m_modelRefManager.getPartPrototypeManager().createRef(id)));
			dispatcher.attr(MODEL_ATTR_NAME_BINDING, (id) -> m_editor.setBinding(m_modelRefManager.getRoleManager().createRef(id)));
			dispatcher.attr(MODEL_ATTR_NAME_ID, (id) -> {
			});
			dispatcher.read();
		}
	}

	private GameComponentRef<GamePlaceType> parseType(StartElement startElement, String type) throws GameComponentBuilderException {
		return parseRef(type, startElement, (p, n) -> p.getPlaceType(n));
	}

	private class PlaceParser {
		private final StandardGamePlacePrototype.Editor m_editor;

		public PlaceParser(StandardGamePlacePrototype.Editor editor) {
			m_editor = editor;
		}

		private void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {

			m_editor.setDocumentation(defaultDocumentation(startElement, MODEL_ATTR_NAME_TYPE));

			dispatcher.attr(MODEL_ATTR_NAME_DOC, (doc) -> m_editor.setDocumentation(lookupPrototoypeDocumentation(doc, startElement.getNamespaceContext())));
			dispatcher.attr(MODEL_ATTR_NAME_TYPE, (type) -> m_editor.setType(parseType(startElement, type)));
			dispatcher.attr(this::parseProperty);
			dispatcher.other(this::parseComponent);
			dispatcher.read();
		}

		private void parseProperty(QName name, String value) throws XMLStreamException, GameComponentBuilderException {
			final ModelPlugin modelPlugin = requireModelPlugin(name);
			m_editor.createProperty(modelPlugin.getName(), name.getLocalPart(), (p) -> p.setValue(value));
		}

		private void parseComponent(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			final ModelPlugin modelPlugin = requireModelPlugin(startElement);
			final Creator<StandardGameModelComponent.Editor> creator = (i) -> m_editor.createComponent(modelPlugin.getName(),
					startElement.getName().getLocalPart(), i);
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

		private void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			dispatcher.attr(this::parseProperty);
			dispatcher.other(this::parseComponent);
			dispatcher.read();
		}

		private void parseProperty(QName name, String value) throws GameComponentBuilderException, XMLStreamException {
			final ModelPlugin modelPlugin = requireModelPlugin(name);
			m_editor.createProperty(modelPlugin.getName(), name.getLocalPart(), (p) -> p.setValue(value));
		}

		private void parseComponent(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			final ModelPlugin modelPlugin = requireModelPlugin(startElement);
			final Creator<StandardGameModelComponent.Editor> creator = (i) -> m_editor.createComponent(modelPlugin.getName(),
					startElement.getName().getLocalPart(), i);
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

		private void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {

			m_editor.setDocumentation(defaultDocumentation(startElement, MODEL_ATTR_NAME_TYPE));

			dispatcher.attr(MODEL_ATTR_NAME_IS_TERMINAL, (t) -> m_editor.setTerminal("yes".equals(t)));
			dispatcher.attr(MODEL_ATTR_NAME_DOC, (doc) -> m_editor.setDocumentation(lookupStageDocumentation(doc, startElement.getNamespaceContext())));
			dispatcher.on(MODEL_ELEMENT_QNAME_STAGE, this::parseStage);
			dispatcher.on(MODEL_ELEMENT_QNAME_RULE, this::parseRule);
			dispatcher.on(MODEL_ELEMENT_QNAME_INITIAL_STAGE, this::parseInitialStage);
			dispatcher.attr(MODEL_ATTR_NAME_ID, (id) -> m_editor.completed((stage) -> m_modelRefManager.getStageManager().register(stage, id)));
			dispatcher.read();

		}

		private void parseInitialStage(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			dispatcher.attr(MODEL_ATTR_NAME_STAGE_REF, (id) -> m_editor.setInitialStage(m_modelRefManager.getStageManager().createRef(id)));
			dispatcher.read();
		}

		private void parseRule(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			final Creator<StandardGameRule.Editor> creator = (i) -> m_editor.createRule(CORE, i);
			createAndInitialize(creator, (r) -> {

				new RuleParser(r).parse(startElement, dispatcher);

			});
		}

		private void parseStage(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			createAndInitialize(m_editor::createStage, (s) -> new StageParser(s).parse(startElement, dispatcher));
		}
	}

	private class RuleParser {
		private final StandardGameRule.Editor m_editor;

		public RuleParser(StandardGameRule.Editor editor) {
			m_editor = editor;
		}

		private void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {

			m_editor.setDocumentation(defaultDocumentation(startElement, MODEL_ATTR_NAME_TYPE));

			dispatcher.attr(MODEL_ATTR_NAME_RESULT, (id) -> m_editor.setResult(m_modelRefManager.getStageManager().createRef(id)));
			dispatcher.attr(MODEL_ATTR_NAME_DOC, (doc) -> m_editor.setDocumentation(lookupRuleDocumentation(doc, startElement.getNamespaceContext())));
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

		private void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {

			m_editor.setDocumentation(defaultDocumentation(startElement));

			dispatcher.attr(MODEL_ATTR_NAME_DOC, (doc) -> m_editor.setDocumentation(lookupOperationDocumentation(doc, startElement.getNamespaceContext())));
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_ROLE, this::parseRole);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_SUBJECT, this::parseSubject);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_TARGET, this::parseTarget);
			dispatcher.read();

		}

		private void parseRole(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			final StandardGameRolePattern rolePattern = StandardGameAnyRolePattern.create().done();
			m_editor.setRolePattern(rolePattern);
			dispatcher.skip();
		}

		private void parseSubject(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			parsePlacePattern(startElement, dispatcher, m_editor::setSubjectPlacePattern);
		}

		private void parseTarget(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			parsePlacePattern(startElement, dispatcher, m_editor::setTargetPlacePattern);
		}

	}

	private void parsePlacePattern(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher, Consumer<GamePlacePattern> consumer)
			throws GameComponentBuilderException, XMLStreamException {
		final StandardIntersectionPlacePattern.Editor editor = StandardIntersectionPlacePattern.create();
		new IntersectionPlacePatternParser(editor).parse(startElement, dispatcher);
		final StandardIntersectionPlacePattern intersectionPlacePattern = editor.done();

		if (intersectionPlacePattern.countPatterns() == 0)
			consumer.accept(StandardAnyPlacePattern.create().done());
		else if (intersectionPlacePattern.countPatterns() == 1)
			consumer.accept(intersectionPlacePattern.patterns().findAny().get());
		else
			consumer.accept(intersectionPlacePattern);
	}

	private class IntersectionPlacePatternParser {
		private final StandardIntersectionPlacePattern.Editor m_editor;

		public IntersectionPlacePatternParser(StandardIntersectionPlacePattern.Editor editor) {
			m_editor = editor;
		}

		private void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_FILTER, this::parseFilterPlacePattern);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_RELATIONSHIP, this::parseRelationshipPlacePattern);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_INTERSECTION, this::parseIntersectionPlacePattern);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_ANY, this::parseAnyPlacePattern);
			dispatcher.read();

		}

		private void parseAnyPlacePattern(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			final StandardAnyPlacePattern.Editor editor = StandardAnyPlacePattern.create().completed(m_editor::addPattern);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_PART, (e, d) -> parsePartPattern(e, d, editor::setPartPattern));
			// no contents, just part pattern
			dispatcher.read();
			editor.done();
		}

		private void parseRelationshipPlacePattern(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			final StandardRelationshipPlacePattern.Editor editor = StandardRelationshipPlacePattern.create().completed(m_editor::addPattern);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_PART, (e, d) -> parsePartPattern(e, d, editor::setPartPattern));
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_RELATED, (e, d) -> parsePlacePattern(e, d, editor::setRelatedPlacePattern));
			dispatcher.read();
			editor.done();
		}

		private void parseFilterPlacePattern(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			final StandardFilterPlacePattern.Editor editor = StandardFilterPlacePattern.create().completed(m_editor::addPattern);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_QUANTITY, (e, d) -> parseQuantityPattern(e, d, editor::setQuantityPattern));
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_PART, (e, d) -> parsePartPattern(e, d, editor::setPartPattern));
			dispatcher.attr(MODEL_ATTR_NAME_TYPE, (qn) -> editor.setMatchType(parseType(startElement, qn)));
			dispatcher.attr(MODEL_ATTR_NAME_BINDING, (id) -> editor.setMatchBinding(m_modelRefManager.getRoleManager().createRef(id)));
			dispatcher.read();
			editor.done();
		}

		private void parseIntersectionPlacePattern(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			final StandardIntersectionPlacePattern.Editor editor = StandardIntersectionPlacePattern.create().completed(m_editor::addPattern);
			// plenty of contents
			dispatcher.skip();

			editor.done();
		}

		private void parsePartPattern(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher, Consumer<GamePartPattern> consumer)
				throws GameComponentBuilderException, XMLStreamException {
			final StandardIntersectionPartPattern.Editor editor = StandardIntersectionPartPattern.create();
			new IntersectionPartPatternParser(editor).parse(startElement, dispatcher);
			final StandardIntersectionPartPattern intersectionPartPattern = editor.done();

			if (intersectionPartPattern.countPatterns() == 0)
				consumer.accept(StandardAnyPartPattern.create().done());
			else if (intersectionPartPattern.countPatterns() == 1)
				consumer.accept(intersectionPartPattern.patterns().findAny().get());
			else
				consumer.accept(intersectionPartPattern);
		}
	}

	private class IntersectionPartPatternParser {
		private final StandardIntersectionPartPattern.Editor m_editor;

		public IntersectionPartPatternParser(StandardIntersectionPartPattern.Editor editor) {
			m_editor = editor;
		}

		private void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_FILTER, this::parseFilterPartPattern);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_INTERSECTION, this::parseIntersectionPartPattern);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_ROOT, this::parseRootPartPattern);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_VARIABLE, this::parseVariablePartPattern);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_ANY, this::parseAnyPartPattern);
			dispatcher.read();

		}

		private void parseRootPartPattern(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			final StandardVariablePartPattern.Editor editor = StandardVariablePartPattern.create().completed(m_editor::addPattern);
			// no contents
			editor.setToken(TOKEN_ROOT);
			dispatcher.read();
			editor.done();
		}

		private void parseVariablePartPattern(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			final StandardVariablePartPattern.Editor editor = StandardVariablePartPattern.create().completed(m_editor::addPattern);
			dispatcher.attr(MODEL_ATTR_NAME_PATTERN_TOKEN, editor::setToken);
			dispatcher.read();
			editor.done();
		}

		private void parseAnyPartPattern(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			final StandardAnyPartPattern.Editor editor = StandardAnyPartPattern.create().completed(m_editor::addPattern);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_QUANTITY, (e, d) -> parseQuantityPattern(e, d, editor::setQuantityPattern));
			dispatcher.read();
			editor.done();
		}

		private void parseFilterPartPattern(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			final StandardFilterPartPattern.Editor editor = StandardFilterPartPattern.create().completed(m_editor::addPattern);
			dispatcher.attr(MODEL_ATTR_NAME_PROTOTYPE_REF, (id) -> editor.setMatchPrototype(m_modelRefManager.getPartPrototypeManager().createRef(id)));
			dispatcher.attr(MODEL_ATTR_NAME_BINDING, (id) -> editor.setMatchBinding(m_modelRefManager.getRoleManager().createRef(id)));
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_QUANTITY, (e, d) -> parseQuantityPattern(e, d, editor::setQuantityPattern));
			dispatcher.read();
			editor.done();
		}

		private void parseIntersectionPartPattern(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher)
				throws GameComponentBuilderException, XMLStreamException {
			final StandardIntersectionPartPattern.Editor editor = StandardIntersectionPartPattern.create().completed(m_editor::addPattern);
			// plenty of contents
			dispatcher.skip();

			editor.done();
		}

	}

	private static Long parseLong(String v) {
		try {
			return Long.parseLong(v);
		} catch (final NumberFormatException ex) {
			return null;
		}
	}

	private void parseQuantityPattern(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher,
			Consumer<GameQuantityPattern> consumer) throws GameComponentBuilderException, XMLStreamException {
		final Optional<Long> min = Optional.ofNullable(startElement.getAttributeByName(QName.valueOf(MODEL_ATTR_NAME_MINIMUM))).map(Attribute::getValue)
				.map(ModelParser::parseLong);
		final Optional<Long> max = Optional.ofNullable(startElement.getAttributeByName(QName.valueOf(MODEL_ATTR_NAME_MAXIMUM))).map(Attribute::getValue)
				.map(ModelParser::parseLong);
		dispatcher.read();

		if (!max.isPresent() && min.map(v -> v == 0).orElse(true))
			// no max AND (min==0 OR no min)
			consumer.accept(StandardAnyQuantityPattern.create().done());
		else if (max.map(v -> v == 1).orElse(false) && min.map(v -> v == 1).orElse(false))
			// max==1 AND min==1
			// consumer.accept(StandardSingleQuantityPattern.create().done());
			consumer.accept(StandardSingleQuantityPattern.create().done());
		else {
			// max is significant AND/OR min is significant
			final StandardRangeQuantityPattern.Editor editor = StandardRangeQuantityPattern.create();
			min.ifPresent(editor::setMinimum);
			max.ifPresent(editor::setMaximum);
			consumer.accept(editor.done());
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

	private GameComponentRef<GameComponentDocumentation> lookupOperationDocumentation(String externalId, NamespaceContext nsCtx)
			throws GameComponentBuilderException {
		return lookupComponentDocumentation(externalId, m_documentationRefResolver.getOperationResolver()::lookup, nsCtx);
	}

	private GameComponentRef<GameComponentDocumentation> lookupRoleDocumentation(String externalId, NamespaceContext nsCtx)
			throws GameComponentBuilderException {
		return lookupComponentDocumentation(externalId, m_documentationRefResolver.getRoleResolver()::lookup, nsCtx);
	}

	private GameComponentRef<GameComponentDocumentation> lookupStageDocumentation(String externalId, NamespaceContext nsCtx)
			throws GameComponentBuilderException {
		return lookupComponentDocumentation(externalId, m_documentationRefResolver.getStageResolver()::lookup, nsCtx);
	}

	private <T extends GameComponent> GameComponentRef<T> lookupComponentDocumentation(String externalId,
			Function<String, Optional<GameComponentRef<T>>> lookup, NamespaceContext nsCtx) throws GameComponentBuilderException {
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
		return () -> createMissingComponentException(id);
	}

	private GameComponentBuilderException createMissingComponentException(String id) {
		return new GameComponentBuilderException(DOC_PARSER_XML, "missing component \"" + id + "\"");
	}

}