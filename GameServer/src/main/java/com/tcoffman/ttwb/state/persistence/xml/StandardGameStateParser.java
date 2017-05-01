package com.tcoffman.ttwb.state.persistence.xml;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.persistance.xml.StandardGameParser;
import com.tcoffman.ttwb.component.persistence.xml.AbstractWriter;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.persistence.xml.EventDispatcher;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.state.GameState;
import com.tcoffman.ttwb.state.StandardGameState;

public class StandardGameStateParser extends StandardGameParser {

	private final GameModel m_model;
	private final PluginFactory m_pluginFactory;
	private final ModelRefResolver m_modelResolver;

	public StandardGameStateParser(GameModel model, PluginFactory pluginFactory, ModelRefResolver modelResolver) {
		m_model = model;
		m_pluginFactory = pluginFactory;
		m_modelResolver = modelResolver;
	}

	public GameState parse(InputStream is) throws XMLStreamException, GameComponentBuilderException {
		final StandardGameState state = new StandardGameState(m_model, m_pluginFactory);

		final XMLEventReader eventReader = createXMLEventReader(is);

		final StandardGameState.Resetter resetter = state.reset();
		final StateParser stateParser = new StateParser(m_modelResolver, resetter);
		stateParser.parse(EventDispatcher.from(eventReader, GameComponentBuilderException.class));
		return state;

	}

	public void write(GameState state, OutputStream os, String modelId) throws TransformerException, GameComponentBuilderException {
		final AbstractWriter stateWriter = new StateWriter(state, m_modelResolver, modelId);
		final Document document = newDocument();

		stateWriter.write(document);
		transform(document, os);
	}
}
