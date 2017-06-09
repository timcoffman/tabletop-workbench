package com.tcoffman.ttwb.doc.persistence.xml;

import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ATTR_NAME_FORMAT;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ATTR_NAME_ID;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ATTR_NAME_LANGUAGE;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ELEMENT_QNAME;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ELEMENT_QNAME_DESCRIPTION;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ELEMENT_QNAME_MODEL;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ELEMENT_QNAME_NAME;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ELEMENT_QNAME_OPERATION;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ELEMENT_QNAME_PROTOTYPE;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ELEMENT_QNAME_ROLE;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ELEMENT_QNAME_RULE;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ELEMENT_QNAME_STAGE;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.persistence.GameComponentRefManager;
import com.tcoffman.ttwb.component.persistence.xml.AbstractGameParser;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.doc.StandardComponentDocumentation;
import com.tcoffman.ttwb.doc.StandardComponentDocumentation.Editor;
import com.tcoffman.ttwb.doc.StandardModelDocumentation;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefManager;
import com.tcoffman.ttwb.persistence.xml.EventDispatcher;

public class DocumentationParser extends AbstractGameParser {

	private final StandardModelDocumentation.Editor m_editor;
	private final DocumentationRefManager m_documentationRefManager;

	public DocumentationParser(StandardModelDocumentation.Editor editor, DocumentationRefManager documentationRefManager) {
		super();
		m_editor = editor;
		m_documentationRefManager = documentationRefManager;
	}

	@Override
	public void parse(EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException, GameComponentBuilderException {

		dispatcher.on(DOC_ELEMENT_QNAME, this::parseDocs);
		dispatcher.read();

	}

	private void parseDocs(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
	GameComponentBuilderException {
		dispatcher.attr(DOC_ATTR_NAME_LANGUAGE, (v) -> System.out.println("lang=" + v));
		dispatcher.on(DOC_ELEMENT_QNAME_MODEL, this::parseModel);
		dispatcher.on(DOC_ELEMENT_QNAME_ROLE, this::parseRole);
		dispatcher.on(DOC_ELEMENT_QNAME_RULE, this::parseRule);
		dispatcher.on(DOC_ELEMENT_QNAME_OPERATION, this::parseOperation);
		dispatcher.on(DOC_ELEMENT_QNAME_PROTOTYPE, this::parsePrototype);
		dispatcher.on(DOC_ELEMENT_QNAME_STAGE, this::parseStage);
		dispatcher.read();
	}

	private void parseModel(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
	GameComponentBuilderException {
		createAndInitialize(m_editor::createModel, (d) -> {
			d.completed(m_documentationRefManager::setModel);
			new DocParser(d).parse(startElement, dispatcher);
		});
	}

	private void parseRole(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
	GameComponentBuilderException {
		parseDoc(startElement, dispatcher, m_editor::createRole, m_documentationRefManager.getRoleManager());
	}

	private void parseRule(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
	GameComponentBuilderException {
		parseDoc(startElement, dispatcher, m_editor::createRule, m_documentationRefManager.getRuleManager());
	}

	private void parseOperation(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
	GameComponentBuilderException {
		parseDoc(startElement, dispatcher, m_editor::createOperation, m_documentationRefManager.getOperationManager());
	}

	private void parsePrototype(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
	GameComponentBuilderException {
		parseDoc(startElement, dispatcher, m_editor::createPrototype, m_documentationRefManager.getPrototypeManager());
	}

	private void parseStage(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
	GameComponentBuilderException {
		parseDoc(startElement, dispatcher, m_editor::createStage, m_documentationRefManager.getStageManager());
	}

	private void parseDoc(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher,
			Creator<StandardComponentDocumentation.Editor> creator, GameComponentRefManager<GameComponentDocumentation> componentManager)
			throws XMLStreamException, GameComponentBuilderException {

		createAndInitialize(creator, (d) -> {
			dispatcher.attr(DOC_ATTR_NAME_ID, (id) -> d.completed((doc) -> componentManager.register(doc, id)));
			new DocParser(d).parse(startElement, dispatcher);
		});
	}

	private class DocParser {
		private final StandardComponentDocumentation.Editor m_editor;

		public DocParser(Editor editor) {
			m_editor = editor;
		}

		public void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws GameComponentBuilderException,
		XMLStreamException {

			dispatcher.on(DOC_ELEMENT_QNAME_NAME, this::parseName);
			dispatcher.on(DOC_ELEMENT_QNAME_DESCRIPTION, this::parseDescription);
			dispatcher.read();

		}

		private void parseName(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
				GameComponentBuilderException {
			GameComponentDocumentation.Format format;
			final Attribute formatAttribute = startElement.getAttributeByName(new QName(DOC_ATTR_NAME_FORMAT));
			if (null == formatAttribute)
				format = GameComponentDocumentation.Format.SHORT;
			else
				format = GameComponentDocumentation.Format.valueOf(formatAttribute.getValue().toUpperCase());
			m_editor.setName(format, dispatcher.contents());
		}

		private void parseDescription(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
				GameComponentBuilderException {
			m_editor.setDescription(dispatcher.contents());
		}

	}
}
