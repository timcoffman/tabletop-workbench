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
import com.tcoffman.ttwb.component.persistence.GameComponentRefResolver;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefResolver;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.StandardGameModel;
import com.tcoffman.ttwb.model.persistance.ModelRefManager;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.model.persistance.StandardModelRefManager;
import com.tcoffman.ttwb.persistence.xml.EventDispatcher;
import com.tcoffman.ttwb.plugin.PluginSet;

public class StandardGameModelParser extends StandardGameParser {

	private final PluginSet m_pluginSet;
	private final DocumentationRefResolver m_documentationRefResolver;
	private final GameComponentRefResolver<GameModel> m_importedModelRefResolver;

	public StandardGameModelParser(PluginSet pluginSet, GameComponentRefResolver<GameModel> importedModelRefResolver,
			DocumentationRefResolver documentationRefResolver) {
		m_pluginSet = pluginSet;
		m_importedModelRefResolver = importedModelRefResolver;
		m_documentationRefResolver = documentationRefResolver;
	}

	private final Map<String, ModelRefManager> m_refManagers = new HashMap<String, ModelRefManager>();

	public ModelRefResolver createResolver(String modelId) {
		final ModelRefResolver modelRefResolver = m_refManagers.get(modelId);
		if (null == modelRefResolver)
			throw new IllegalStateException("no external reference resolver for model \"" + modelId + "\"");
		return modelRefResolver;
	}

	private ModelRefManager requireResolver(String modelId) {
		ModelRefManager modelRefManager = m_refManagers.get(modelId);
		if (null == modelRefManager)
			m_refManagers.put(modelId, modelRefManager = new StandardModelRefManager(m_pluginSet, m_importedModelRefResolver));
		return modelRefManager;
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
