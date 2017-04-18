package com.tcoffman.ttwb.model.persistance.xml;

import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_ID;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_IS_TERMINAL;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_REF;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_RESULT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_INITIAL_STAGE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_NAME;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_OP_JOIN;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_OP_MOVE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_OP_ORIENT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_OP_SIGNAL;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_OP_SPLIT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_QUANTITY;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_ROLE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_SUBJECT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_PATTERN_TARGET;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_ROLE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_RULE;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_STAGE;
import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

import com.tcoffman.ttwb.model.GameModelBuilderException;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.GameStageRef;
import com.tcoffman.ttwb.model.StandardGameModel;
import com.tcoffman.ttwb.model.StandardGameRole;
import com.tcoffman.ttwb.model.StandardGameRule;
import com.tcoffman.ttwb.model.StandardGameStage;
import com.tcoffman.ttwb.model.pattern.StandardGameOperationPattern;
import com.tcoffman.ttwb.model.pattern.StandardGameRolePattern;
import com.tcoffman.ttwb.state.GameOperation;
import com.tcoffman.ttwb.state.pattern.StandardGameAnyRolePattern;

class ModelParser {
	private final StandardGameModel.Editor m_editor;
	private final GameStageResolverImpl m_resolver = new GameStageResolverImpl();

	private final Map<String, GameRole> m_roles = new HashMap<String, GameRole>();

	private static class GameStageRefImpl implements GameStageRef {

		private Map<String, GameStage> m_stages;
		private final String m_id;
		private GameStage m_stage;

		public GameStageRefImpl(Map<String, GameStage> stages, String id) {
			m_stages = stages;
			m_id = id;
		}

		@Override
		public GameStage get() {
			if (null == m_stage) {
				m_stage = m_stages.get(m_id);
				m_stages = null;
			}
			return m_stage;
		}

	}

	private static class GameStageResolverImpl {

		private final Map<String, GameStage> m_stages = new HashMap<String, GameStage>();
		private final Set<GameStageRefImpl> m_stageRefs = new HashSet<GameStageRefImpl>();

		public void register(GameStage stage, String id) {
			final GameStage registeredStage = m_stages.get(id);
			if (null != registeredStage && registeredStage != stage)
				throw new IllegalStateException("cannot register two different stages with the same id");
			m_stages.put(id, stage);
		}

		public void resolveAll() {
			m_stageRefs.forEach(GameStageRefImpl::get);
		}

		public GameStageRef createRef(String stageId) {
			final GameStageRefImpl stageRef = new GameStageRefImpl(m_stages, stageId);
			m_stageRefs.add(stageRef);
			return stageRef;
		}
	}

	public ModelParser(StandardGameModel.Editor editor) {
		m_editor = editor;
	}

	private Optional<String> getAttribute(StartElement startElement, String localName) {
		return Optional.ofNullable(startElement.getAttributeByName(QName.valueOf(localName))).map(Attribute::getValue);
	}

	void parse(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws XMLStreamException, GameModelBuilderException {
		dispatcher.on(MODEL_ELEMENT_QNAME_NAME, this::parseName);
		dispatcher.on(MODEL_ELEMENT_QNAME_ROLE, this::parseRole);
		dispatcher.on(MODEL_ELEMENT_QNAME_INITIAL_STAGE, this::parseInitialStage);
		dispatcher.on(MODEL_ELEMENT_QNAME_STAGE, this::parseStage);
		dispatcher.other((e, d) -> d.skip());
		dispatcher.read();
		m_resolver.resolveAll();
	}

	private void parseInitialStage(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
	XMLStreamException {
		final String stageId = getAttribute(startElement, MODEL_ATTR_NAME_REF).orElseThrow(ModelParser.this::missingAttribute);
		m_editor.setInitialStage(m_resolver.createRef(stageId));
		dispatcher.read();
	}

	private void parseStage(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
			XMLStreamException {
		new StageParser(m_editor.createStage()).parse(startElement, dispatcher);
	}

	private void parseName(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws XMLStreamException {
		m_editor.setName(dispatcher.contents());
	}

	private void parseRole(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
			XMLStreamException {
		final StandardGameRole.Editor editor = m_editor.createRole();
		final StandardGameRole role = editor.done();
		final Attribute attr = startElement.getAttributeByName(QName.valueOf(MODEL_ATTR_NAME_ID));
		if (null != attr)
			m_roles.put(attr.getValue(), role);
		dispatcher.skip();
	}

	private class StageParser {
		private final StandardGameStage.Editor m_editor;

		public StageParser(StandardGameStage.Editor editor) {
			m_editor = editor;
		}

		private void parse(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
				XMLStreamException {
			m_editor.setTerminal(getAttribute(startElement, MODEL_ATTR_NAME_IS_TERMINAL).map("yes"::equals).orElse(false));

			dispatcher.on(MODEL_ELEMENT_QNAME_STAGE, this::parseStage);
			dispatcher.on(MODEL_ELEMENT_QNAME_RULE, this::parseRule);
			dispatcher.on(MODEL_ELEMENT_QNAME_INITIAL_STAGE, this::parseInitialStage);
			dispatcher.read();

			final StandardGameStage stage = m_editor.done();
			getAttribute(startElement, MODEL_ATTR_NAME_ID).ifPresent((s) -> m_resolver.register(stage, s));
		}

		private void parseRule(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
				XMLStreamException {
			new RuleParser(m_editor.createRule(CORE)).parse(startElement, dispatcher);
		}

		private void parseInitialStage(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
		XMLStreamException {
			dispatcher.skip();
		}

		private void parseStage(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
				XMLStreamException {
			new StageParser(m_editor.createStage()).parse(startElement, dispatcher);
		}
	}

	private class RuleParser {
		private final StandardGameRule.Editor m_editor;

		public RuleParser(StandardGameRule.Editor editor) {
			m_editor = editor;
		}

		private void parse(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws GameModelBuilderException,
				XMLStreamException {

			final String stageId = getAttribute(startElement, MODEL_ATTR_NAME_RESULT).orElseThrow(ModelParser.this::missingAttribute);
			final GameStageRef result = m_resolver.createRef(stageId);
			m_editor.setResult(result);

			dispatcher.on(MODEL_ELEMENT_QNAME_ROLE, this::parseRolePattern);
			dispatcher.on(MODEL_ELEMENT_QNAME_OP_SIGNAL, (e, d) -> parseOperationPattern(e, d, GameOperation.Type.SIGNAL));
			dispatcher.on(MODEL_ELEMENT_QNAME_OP_MOVE, (e, d) -> parseOperationPattern(e, d, GameOperation.Type.MOVE));
			dispatcher.on(MODEL_ELEMENT_QNAME_OP_ORIENT, (e, d) -> parseOperationPattern(e, d, GameOperation.Type.ORIENT));
			dispatcher.on(MODEL_ELEMENT_QNAME_OP_SPLIT, (e, d) -> parseOperationPattern(e, d, GameOperation.Type.SPLIT));
			dispatcher.on(MODEL_ELEMENT_QNAME_OP_JOIN, (e, d) -> parseOperationPattern(e, d, GameOperation.Type.JOIN));
			dispatcher.read();

			m_editor.done();
		}

		private void parseRolePattern(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher) throws XMLStreamException {
			dispatcher.skip();
		}

		private void parseOperationPattern(StartElement startElement, EventDispatcher<GameModelBuilderException> dispatcher, GameOperation.Type type)
				throws GameModelBuilderException, XMLStreamException {
			m_editor.setType(type);
			final StandardGameOperationPattern.Editor editor = m_editor.createOperationPattern();

			editor.setType(type);
			new OperationPatternParser(editor).parse(startElement, dispatcher);
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

			m_editor.done();
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