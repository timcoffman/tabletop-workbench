package com.tcoffman.ttwb.model.persistance.xml;

import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME;

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
import org.w3c.dom.Element;

import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GameModelBuilderException;
import com.tcoffman.ttwb.model.StandardGameModel;
import com.tcoffman.ttwb.plugin.PluginFactory;

public class StandardGameModelParser {

	private static final XMLInputFactory s_xmlInputFactory = XMLInputFactory.newInstance();

	static {
		s_xmlInputFactory.setProperty(XMLInputFactory.IS_VALIDATING, false);
		s_xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING, true);
		s_xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
		s_xmlInputFactory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, true);
		s_xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
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

	private final PluginFactory m_pluginFactory;

	public StandardGameModelParser(PluginFactory pluginFactory) {
		m_pluginFactory = pluginFactory;
	}

	public GameModel parse(InputStream is) throws XMLStreamException, GameModelBuilderException {

		final XMLEventReader eventReader = s_xmlInputFactory.createXMLEventReader(is);

		final StandardGameModel.Editor editor = StandardGameModel.create();
		final ModelParser modelParser = new ModelParser(editor);
		final EventDispatcher<GameModelBuilderException> dispatcher = EventDispatcher.from(eventReader, GameModelBuilderException.class);
		dispatcher.on(MODEL_ELEMENT_QNAME, modelParser::parse);
		dispatcher.other((e, d) -> d.skip());
		return dispatcher.produce(editor::done);

	}

	public void write(GameModel model, OutputStream os) throws TransformerException {
		final ModelWriter modelWriter = new ModelWriter(model);
		final Document document = s_documentBuilder.newDocument();

		final Element modelElement = document.createElementNS(MODEL_ELEMENT_QNAME.getNamespaceURI(), MODEL_ELEMENT_QNAME.getLocalPart());
		document.appendChild(modelElement);
		modelWriter.write(modelElement);
		s_transformer.transform(new DOMSource(document), new StreamResult(os));
	}
}
