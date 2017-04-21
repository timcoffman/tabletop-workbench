package com.tcoffman.ttwb.model.persistance.xml;

import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_ABSTRACT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_BINDING;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_EXTENDS;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_ID;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_IS_TERMINAL;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_PROTOTYPE_REF;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_RESULT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_STAGE_REF;
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
import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.net.URI;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;

import com.tcoffman.ttwb.model.AbstractEditor;
import com.tcoffman.ttwb.model.GameComponentRef;
import com.tcoffman.ttwb.model.GameModelBuilderException;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.StandardGameModel;
import com.tcoffman.ttwb.model.StandardGameModelComponent;
import com.tcoffman.ttwb.model.StandardGamePartInstance;
import com.tcoffman.ttwb.model.StandardGamePartPrototype;
import com.tcoffman.ttwb.model.StandardGamePlace;
import com.tcoffman.ttwb.model.StandardGameRule;
import com.tcoffman.ttwb.model.StandardGameStage;
import com.tcoffman.ttwb.model.pattern.StandardGameOperationPattern;
import com.tcoffman.ttwb.model.pattern.StandardGameRolePattern;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.PluginSet;
import com.tcoffman.ttwb.state.GameOperation;
import com.tcoffman.ttwb.state.pattern.StandardGameAnyRolePattern;

class ModelParser {
	private final PluginSet m_pluginSet;
	private final StandardGameModel.Editor m_editor;
	private final GameModelComponentRefManager<GameStage> m_stageRefManager = new GameModelComponentRefManager<GameStage>();
	private final GameModelComponentRefManager<GameRole> m_roleManager = new GameModelComponentRefManager<GameRole>();
	private final GameModelComponentRefManager<GamePartPrototype> m_prototypeRefManager = new GameModelComponentRefManager<GamePartPrototype>();

	public ModelParser(PluginFactory pluginFactory, StandardGameModel.Editor editor) {
		m_pluginSet = new PluginSet(pluginFactory);
		m_editor = editor;
	}

	private ModelPlugin requireModelPlugin(StartElement startElement, String prefix) throws GameModelBuilderException {
		return requireModelPlugin(startElement.getNamespaceURI(prefix));
	}

	private ModelPlugin requireModelPlugin(QName qualifiedName) throws GameModelBuilderException {
		return requireModelPlugin(qualifiedName.getNamespaceURI());
	}

	private ModelPlugin requireModelPlugin(StartElement startElement) throws GameModelBuilderException {
		return requireModelPlugin(startElement.getName());
	}

	private ModelPlugin requireModelPlugin(String uri) throws GameModelBuilderException {
		final PluginName pluginName = PluginName.create(URI.create(uri));
		final ModelPlugin plugin;
		try {
			plugin = (ModelPlugin) m_pluginSet.requirePlugin(pluginName);
		} catch (final PluginException ex) {
			throw new GameModelBuilderException(ex.getPluginName(), ex);
		}
		m_editor.addRequiredPlugin(plugin);
		return plugin;
	}

	private interface Creator<E extends AbstractEditor<?>> {
		void create(AbstractEditor.Initializer<E> initializer) throws GameModelBuilderException;
	}

	private interface XmlInitializer<E extends AbstractEditor<?>> {
		void initialize(E editor) throws GameModelBuilderException, XMLStreamException;
	}

	private static final PluginName PLUGIN_PARSER_XML = new PluginName("com.tcoffman.ttwb.model.persistance.xml", "1.0");

	private <E extends AbstractEditor<?>> void createAndInitialize(Creator<E> creator, XmlInitializer<E> initializer) throws XMLStreamException,
			GameModelBuilderException {
		try {

			creator.create((e) -> {

				try {
					initializer.initialize(e);
				} catch (final XMLStreamException ex) {
					throw new GameModelBuilderException(PLUGIN_PARSER_XML, ex);
				}

			});

		} catch (final GameModelBuilderException ex) {
			if (PLUGIN_PARSER_XML.equals(ex.getPluginName()) && ex.getCause() instanceof XMLStreamException)
				throw (XMLStreamException) ex.getCause();
			else
				throw ex;
		}
	}

	void parse(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws XMLStreamException, GameModelBuilderException {

		dispatcher.on(MODEL_ELEMENT_QNAME_NAME, this::parseName);
		dispatcher.on(MODEL_ELEMENT_QNAME_ROLE, this::parseRole);
		dispatcher.on(MODEL_ELEMENT_QNAME_PROTOTYPE, this::parsePrototype);
		dispatcher.on(MODEL_ELEMENT_QNAME_PARTS, this::parseParts);
		dispatcher.on(MODEL_ELEMENT_QNAME_INITIAL_STAGE, this::parseInitialStage);
		dispatcher.on(MODEL_ELEMENT_QNAME_STAGE, this::parseStage);
		dispatcher.other((e, d) -> d.skip());
		dispatcher.read();
		m_stageRefManager.resolveAll();
		m_prototypeRefManager.resolveAll();
		m_roleManager.resolveAll();
	}

	private void parseInitialStage(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
			XMLStreamException {
		dispatcher.attr(MODEL_ATTR_NAME_STAGE_REF, (id) -> m_editor.setInitialStage(m_stageRefManager.createRef(id)));
		dispatcher.read();
	}

	private void parseStage(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
	XMLStreamException {
		createAndInitialize(m_editor::createStage, (s) -> new StageParser(s).parse(startElement, dispatcher));
	}

	private void parseName(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws XMLStreamException {
		m_editor.setName(dispatcher.contents());
	}

	private void parseRole(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
	XMLStreamException {
		createAndInitialize(m_editor::createRole, (r) -> {

			dispatcher.attr(MODEL_ATTR_NAME_ID, (id) -> r.completed((role) -> m_roleManager.register(role, id)));
			dispatcher.read();
		});

	}

	private void parsePrototype(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
	XMLStreamException {
		final Creator<StandardGamePartPrototype.Editor> creator = (i) -> m_editor.createPrototype(CORE, i);
		createAndInitialize(creator, (p) -> new PartPrototypeParser(p).parse(startElement, dispatcher));
	}

	private void parseParts(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
			XMLStreamException {
		dispatcher.on(MODEL_ELEMENT_QNAME_PART, this::parsePart);
		dispatcher.read();
	}

	private void parsePart(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
			XMLStreamException {
		createAndInitialize(m_editor::createPart, (p) -> new PartParser(p).parse(startElement, dispatcher));
	}

	private class PartPrototypeParser {
		private final StandardGamePartPrototype.Editor m_editor;

		public PartPrototypeParser(StandardGamePartPrototype.Editor editor) {
			m_editor = editor;
		}

		public void parse(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
				XMLStreamException {

			dispatcher.on(MODEL_ELEMENT_QNAME_PLACE, this::parsePlace);
			dispatcher.attr(MODEL_ATTR_NAME_ABSTRACT, (abs) -> m_editor.setAbstract("yes".equals(abs)));
			dispatcher.attr(MODEL_ATTR_NAME_EXTENDS, (id) -> m_editor.setExtends(m_prototypeRefManager.createRef(id)));
			dispatcher.attr(MODEL_ATTR_NAME_BINDING, (id) -> m_editor.setBinding(m_roleManager.createRef(id)));
			dispatcher.attr(MODEL_ATTR_NAME_ID, (id) -> m_editor.completed((prototype) -> m_prototypeRefManager.register(prototype, id)));
			dispatcher.read();

		}

		private void parsePlace(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
				XMLStreamException {
			createAndInitialize(m_editor::createPlace, (p) -> new PlaceParser(p).parse(startElement, dispatcher));
		}
	}

	private class PartParser {
		private final StandardGamePartInstance.Editor m_editor;

		public PartParser(StandardGamePartInstance.Editor editor) {
			m_editor = editor;
		}

		public void parse(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
		XMLStreamException {

			dispatcher.attr(MODEL_ATTR_NAME_PROTOTYPE_REF, (id) -> m_editor.setPrototype(m_prototypeRefManager.createRef(id)));
			dispatcher.attr(MODEL_ATTR_NAME_BINDING, (id) -> m_editor.setBinding(m_roleManager.createRef(id)));
			dispatcher.attr(MODEL_ATTR_NAME_ID, (id) -> {
			});
			dispatcher.read();
		}
	}

	private class PlaceParser {
		private final StandardGamePlace.Editor m_editor;

		public PlaceParser(StandardGamePlace.Editor editor) {
			m_editor = editor;
		}

		private GameComponentRef<GamePlaceType> parseType(StartElement startElement, String type) throws GameModelBuilderException {
			final String[] typeParts = type.split(":", 2);
			if (typeParts.length == 0)
				throw missingAttribute();

			ModelPlugin modelPlugin;
			String name;
			if (typeParts.length == 1) {
				modelPlugin = requireModelPlugin(startElement);
				name = typeParts[0];
			} else {
				modelPlugin = requireModelPlugin(startElement, typeParts[0]);
				name = typeParts[1];
			}
			try {
				return modelPlugin.getPlaceType(name);
			} catch (final PluginException ex) {
				throw new GameModelBuilderException(ex.getPluginName(), ex);
			}
		}

		private void parse(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
		XMLStreamException {

			// final String type = getAttribute(startElement,
			// MODEL_ATTR_NAME_TYPE).orElseThrow(ModelParser.this::missingAttribute);
			// m_editor.setType(parseType(startElement, type));

			dispatcher.attr(MODEL_ATTR_NAME_TYPE, (type) -> m_editor.setType(parseType(startElement, type)));
			dispatcher.attr(this::parseProperty);
			dispatcher.other(this::parseComponent);
			dispatcher.read();
		}

		private void parseProperty(QName name, String value) throws XMLStreamException, GameModelBuilderException {
			final ModelPlugin modelPlugin = requireModelPlugin(name);
			m_editor.createProperty(modelPlugin.getName(), name.getLocalPart(), (p) -> p.setValue(value));
		}

		private void parseComponent(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
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

		private void parse(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
				XMLStreamException {
			dispatcher.attr(this::parseProperty);
			dispatcher.other(this::parseComponent);
			dispatcher.read();
		}

		private void parseProperty(QName name, String value) throws GameModelBuilderException, XMLStreamException {
			final ModelPlugin modelPlugin = requireModelPlugin(name);
			m_editor.createProperty(modelPlugin.getName(), name.getLocalPart(), (p) -> p.setValue(value));
		}

		private void parseComponent(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
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

		private void parse(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
				XMLStreamException {

			dispatcher.attr(MODEL_ATTR_NAME_IS_TERMINAL, (t) -> m_editor.setTerminal("yes".equals(t)));
			dispatcher.on(MODEL_ELEMENT_QNAME_STAGE, this::parseStage);
			dispatcher.on(MODEL_ELEMENT_QNAME_RULE, this::parseRule);
			dispatcher.on(MODEL_ELEMENT_QNAME_INITIAL_STAGE, this::parseInitialStage);
			dispatcher.attr(MODEL_ATTR_NAME_ID, (id) -> m_editor.completed((stage) -> m_stageRefManager.register(stage, id)));
			dispatcher.read();

		}

		private void parseRule(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
		XMLStreamException {
			final Creator<StandardGameRule.Editor> creator = (i) -> m_editor.createRule(CORE, i);
			createAndInitialize(creator, (r) -> {

				new RuleParser(r).parse(startElement, dispatcher);

			});
		}

		private void parseInitialStage(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
				XMLStreamException {

			dispatcher.attr(MODEL_ATTR_NAME_STAGE_REF, (id) -> {
			});
			dispatcher.read();
		}

		private void parseStage(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
		XMLStreamException {
			createAndInitialize(m_editor::createStage, (s) -> new StageParser(s).parse(startElement, dispatcher));
		}
	}

	private class RuleParser {
		private final StandardGameRule.Editor m_editor;

		public RuleParser(StandardGameRule.Editor editor) {
			m_editor = editor;
		}

		private void parse(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
		XMLStreamException {

			dispatcher.attr(MODEL_ATTR_NAME_RESULT, (id) -> m_editor.setResult(m_stageRefManager.createRef(id)));
			dispatcher.on(MODEL_ELEMENT_QNAME_ROLE, this::parseRolePattern);
			dispatcher.on(MODEL_ELEMENT_QNAME_OP_SIGNAL, (e, d) -> parseOperationPattern(e, d, GameOperation.Type.SIGNAL));
			dispatcher.on(MODEL_ELEMENT_QNAME_OP_MOVE, (e, d) -> parseOperationPattern(e, d, GameOperation.Type.MOVE));
			dispatcher.on(MODEL_ELEMENT_QNAME_OP_ORIENT, (e, d) -> parseOperationPattern(e, d, GameOperation.Type.ORIENT));
			dispatcher.on(MODEL_ELEMENT_QNAME_OP_SPLIT, (e, d) -> parseOperationPattern(e, d, GameOperation.Type.SPLIT));
			dispatcher.on(MODEL_ELEMENT_QNAME_OP_JOIN, (e, d) -> parseOperationPattern(e, d, GameOperation.Type.JOIN));
			dispatcher.read();

		}

		private void parseRolePattern(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws XMLStreamException {
			dispatcher.skip();
		}

		private void parseOperationPattern(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher, GameOperation.Type type)
				throws GameModelBuilderException, XMLStreamException {
			m_editor.setType(type);

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

		private void parse(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
		XMLStreamException {

			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_ROLE, this::parseRole);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_SUBJECT, this::parseSubject);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_TARGET, this::parseTarget);
			dispatcher.on(MODEL_ELEMENT_QNAME_PATTERN_QUANTITY, this::parseQuantity);
			dispatcher.read();

		}

		private void parseRole(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
		XMLStreamException {
			final StandardGameRolePattern rolePattern = StandardGameAnyRolePattern.create().done();
			m_editor.setRolePattern(rolePattern);
			dispatcher.skip();
		}

		private void parseSubject(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
		XMLStreamException {
			dispatcher.skip();
		}

		private void parseTarget(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
		XMLStreamException {
			dispatcher.skip();
		}

		private void parseQuantity(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
		XMLStreamException {
			dispatcher.skip();
		}

	}

	private IllegalStateException missingAttribute() {
		return new IllegalStateException("missing attribute");
	}

}