package com.tcoffman.ttwb.doc.persistence.xml;

import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ATTR_NAME_FORMAT;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ELEMENT_QNAME;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ELEMENT_QNAME_DESCRIPTION;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ELEMENT_QNAME_MODEL;
import static com.tcoffman.ttwb.doc.persistence.xml.XmlConstants.DOC_ELEMENT_QNAME_NAME;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.persistence.xml.AbstractWriter;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.doc.StandardModelDocumentation;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefManager;

public class DocumentationWriter extends AbstractWriter {

	private final StandardModelDocumentation m_docs;

	public DocumentationWriter(StandardModelDocumentation docs, DocumentationRefManager documentationRefManager) {
		m_docs = docs;
	}

	@Override
	public void write(Document document) throws GameComponentBuilderException {
		writeDocumentation(document);
	}

	private void writeDocumentation(Document document) {
		final Element documentationElement = createAndAppendElement(document, DOC_ELEMENT_QNAME);
		writeModel(documentationElement, m_docs.model());
	}

	private void writeModel(Element documentationElement, GameComponentDocumentation model) {
		final Element modelElement = createAndAppendElement(documentationElement, DOC_ELEMENT_QNAME_MODEL);
		writeDocumentationElements(modelElement, m_docs.model());
	}

	private void writeDocumentationElements(Element element, GameComponentDocumentation doc) {
		for (final GameComponentDocumentation.Format format : GameComponentDocumentation.Format.values()) {
			final Element nameElement = createAndAppendElement(element, DOC_ELEMENT_QNAME_NAME);
			nameElement.setAttribute(DOC_ATTR_NAME_FORMAT, format.name().toLowerCase());
			nameElement.setTextContent(doc.getName(format));
		}
		final Element descElement = createAndAppendElement(element, DOC_ELEMENT_QNAME_DESCRIPTION);
		descElement.setTextContent(doc.getDescription());
	}
}
