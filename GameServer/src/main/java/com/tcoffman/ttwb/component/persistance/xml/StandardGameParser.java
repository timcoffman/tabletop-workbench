package com.tcoffman.ttwb.component.persistance.xml;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public abstract class StandardGameParser {

	private static final XMLInputFactory s_xmlInputFactory = XMLInputFactory.newInstance();

	static {
		s_xmlInputFactory.setProperty(XMLInputFactory.IS_VALIDATING, false);
		s_xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING, true);
		s_xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
		s_xmlInputFactory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, true);
		s_xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
	}

	protected static XMLEventReader createXMLEventReader(InputStream is) throws XMLStreamException {
		return s_xmlInputFactory.createXMLEventReader(is);
	}

	private static final DocumentBuilderFactory s_documentFactory;
	private static final DocumentBuilder s_documentBuilder;
	private static final TransformerFactory s_transformerFactory;
	private static final Transformer s_transformer;

	static {
		s_documentFactory = DocumentBuilderFactory.newInstance();
		s_documentFactory.setValidating(false);
		s_documentFactory.setCoalescing(true);
		s_documentFactory.setNamespaceAware(true);
		s_documentFactory.setExpandEntityReferences(true);
		try {
			s_documentBuilder = s_documentFactory.newDocumentBuilder();
		} catch (final ParserConfigurationException ex) {
			throw new RuntimeException("cannot configure xml document builder", ex);
		}
		s_transformerFactory = TransformerFactory.newInstance();
		try {
			s_transformer = s_transformerFactory.newTransformer();
		} catch (final TransformerConfigurationException ex) {
			throw new RuntimeException("cannot configure xml document transformer", ex);
		}
		s_transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		s_transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	}

	protected static Document newDocument() {
		return s_documentBuilder.newDocument();
	}

	protected static void transform(Document document, OutputStream os) throws TransformerException {
		s_transformer.transform(new DOMSource(document), new StreamResult(os));
	}

}
