package com.tcoffman.ttwb.doc.persistence.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefResolver;
import com.tcoffman.ttwb.component.persistence.xml.StandardGameParser;
import com.tcoffman.ttwb.doc.StandardModelDocumentation;
import com.tcoffman.ttwb.doc.persistence.StandardDocumentationRefManager;
import com.tcoffman.ttwb.persistence.xml.EventDispatcher;

public class StandardGameDocumentationParser extends StandardGameParser {

	public StandardGameDocumentationParser() {
	}

	private final Map<String, StandardDocumentationRefManager> m_resolvers = new HashMap<String, StandardDocumentationRefManager>();

	public DocumentationRefResolver createResolver(String modelDocId) {
		final DocumentationRefResolver modelResolver = m_resolvers.get(modelDocId);
		if (null == modelResolver)
			throw new IllegalStateException("no external reference resolver for documentation module \"" + modelDocId + "\"");
		return modelResolver;
	}

	private StandardDocumentationRefManager requireManager(String modelDocId) {
		StandardDocumentationRefManager standardDocumentationResolver = m_resolvers.get(modelDocId);
		if (null == standardDocumentationResolver)
			m_resolvers.put(modelDocId, standardDocumentationResolver = new StandardDocumentationRefManager());
		return standardDocumentationResolver;
	}

	public StandardModelDocumentation parse(InputStream is, String modelDocId) throws XMLStreamException, GameComponentBuilderException {

		final XMLEventReader eventReader = createXMLEventReader(is);

		final StandardModelDocumentation.Editor editor = StandardModelDocumentation.create();
		final DocumentationParser docsParser = new DocumentationParser(editor, requireManager(modelDocId));
		docsParser.parse(EventDispatcher.from(eventReader, GameComponentBuilderException.class));
		return editor.done();

	}

	public void write(StandardModelDocumentation docs, OutputStream os, String modelDocId) throws TransformerException, GameComponentBuilderException {
		final DocumentationWriter docsWriter = new DocumentationWriter(docs, requireManager(modelDocId));
		final Document document = newDocument();

		docsWriter.write(document);
		transform(document, os);

	}

}
