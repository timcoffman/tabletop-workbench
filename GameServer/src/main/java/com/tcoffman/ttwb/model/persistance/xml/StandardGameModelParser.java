package com.tcoffman.ttwb.model.persistance.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.persistance.xml.StandardGameParser;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefResolver;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.StandardGameModel;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.model.persistance.StandardModelRefManager;
import com.tcoffman.ttwb.persistence.xml.EventDispatcher;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.plugin.PluginSet;

public class StandardGameModelParser extends StandardGameParser {

	private final PluginFactory m_pluginFactory;
	private final DocumentationRefResolver m_documentationRefResolver;

	public StandardGameModelParser(PluginFactory pluginFactory, DocumentationRefResolver documentationRefResolver) {
		m_pluginFactory = pluginFactory;
		m_documentationRefResolver = documentationRefResolver;
	}

	private final Map<String, StandardModelRefManager> m_resolvers = new HashMap<String, StandardModelRefManager>();

	public ModelRefResolver createResolver(String modelId) {
		final ModelRefResolver modelResolver = m_resolvers.get(modelId);
		if (null == modelResolver)
			throw new IllegalStateException("no external reference resolver for model \"" + modelId + "\"");
		return modelResolver;
	}

	private StandardModelRefManager requireResolver(String modelId) {
		StandardModelRefManager standardModelResolver = m_resolvers.get(modelId);
		if (null == standardModelResolver)
			m_resolvers.put(modelId, standardModelResolver = new StandardModelRefManager(new PluginSet(m_pluginFactory)));
		return standardModelResolver;
	}

	public GameModel parse(InputStream is, String modelId) throws XMLStreamException, GameComponentBuilderException {

		final XMLEventReader eventReader = createXMLEventReader(is);

		final StandardGameModel.Editor editor = StandardGameModel.create();
		final ModelParser modelParser = new ModelParser(editor, m_documentationRefResolver, requireResolver(modelId));
		modelParser.parse(EventDispatcher.from(eventReader, GameComponentBuilderException.class));
		return editor.done();

	}

	public void write(GameModel model, OutputStream os, String modelId) throws TransformerException {
		final ModelWriter modelWriter = new ModelWriter(model, requireResolver(modelId));
		final Document document = newDocument();

		modelWriter.write(document);
		transform(document, os);

	}
}
