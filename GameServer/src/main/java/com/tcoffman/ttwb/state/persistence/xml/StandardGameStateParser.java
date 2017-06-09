package com.tcoffman.ttwb.state.persistence.xml;

import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_NS;
import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_MODEL;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.persistance.xml.StandardGameParser;
import com.tcoffman.ttwb.component.persistence.xml.AbstractWriter;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.persistence.xml.EventDispatcher;
import com.tcoffman.ttwb.plugin.PluginSet;
import com.tcoffman.ttwb.state.GameAuthorizationManager;
import com.tcoffman.ttwb.state.GameState;
import com.tcoffman.ttwb.state.StandardGameState;
import com.tcoffman.ttwb.state.persistence.StandardStateRefManager;
import com.tcoffman.ttwb.state.persistence.StateRefManager;
import com.tcoffman.ttwb.state.persistence.StateRefResolver;

public class StandardGameStateParser extends StandardGameParser {

	private final Function<String, ModelProvider> m_modelProviderFactory;
	private final GameAuthorizationManager m_authorizationManager;

	public interface ModelProvider {
		GameModel getModel() throws GameComponentBuilderException, XMLStreamException;

		PluginSet getPluginSet() throws GameComponentBuilderException, XMLStreamException;

		ModelRefResolver getModelRefResolver() throws GameComponentBuilderException, XMLStreamException;
	}

	public StandardGameStateParser(GameAuthorizationManager authorizationManager, Function<String, ModelProvider> modelProviderFactory) {
		m_modelProviderFactory = modelProviderFactory;
		m_authorizationManager = authorizationManager;
	}

	private final Map<String, StateRefManager> m_refManagers = new HashMap<String, StateRefManager>();

	public StateRefResolver createResolver(String stateId) {
		final StateRefResolver stateRefResolver = m_refManagers.get(stateId);
		if (null == stateRefResolver)
			throw new IllegalStateException("no external reference resolver for state \"" + stateId + "\"");
		return stateRefResolver;
	}

	public StateRefManager createManager(String stateId) {
		final StateRefManager stateRefManager = m_refManagers.get(stateId);
		if (null == stateRefManager)
			throw new IllegalStateException("no external reference manager for state \"" + stateId + "\"");
		return stateRefManager;
	}

	public void registerManager(String stateId, StateRefManager stateRefManager) {
		if (m_refManagers.containsKey(stateId))
			throw new IllegalStateException("cannot replace external reference manager for state \"" + stateId + "\"");
		m_refManagers.put(stateId, stateRefManager);
	}

	private StateRefManager requireManager(PluginSet pluginSet, String stateId) {
		StateRefManager stateRefManager = m_refManagers.get(stateId);
		if (null == stateRefManager)
			m_refManagers.put(stateId, stateRefManager = new StandardStateRefManager(pluginSet));
		return stateRefManager;
	}

	public GameState parse(InputStream is, String stateId) throws XMLStreamException, GameComponentBuilderException {
		final XMLEventReader eventReader = createXMLEventReader(is);
		final EventDispatcher<GameComponentBuilderException> dispatcher = EventDispatcher.from(eventReader, GameComponentBuilderException.class);
		final ModelBasedStateParser modelBasedStateParser = new ModelBasedStateParser(stateId);
		dispatcher.on(XmlConstants.STATE_ELEMENT_QNAME, modelBasedStateParser::parse);
		dispatcher.read();
		return modelBasedStateParser.getState();
	}

	private class ModelBasedStateParser {
		private final String m_stateId;
		private StandardGameState m_state;

		public ModelBasedStateParser(String stateId) {
			m_stateId = stateId;
		}

		public StandardGameState getState() {
			return m_state;
		}

		private ModelProvider parseModelIdRef(String ref, NamespaceContext nsCtx) {
			final String[] refParts = ref.split(":", 2);
			if (refParts.length == 0)
				throw new IllegalArgumentException("missing reference");
			String modelId;
			if (refParts.length == 1)
				modelId = refParts[0];
			else {
				final String namespace = nsCtx.getNamespaceURI(refParts[0]);
				if (MODEL_NS.equals(namespace))
					modelId = refParts[1];
				else
					throw new IllegalArgumentException("cannot resolve model id reference in " + namespace);
			}

			return m_modelProviderFactory.apply(modelId);
		}

		public void parse(StartElement startElement, EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException,
		GameComponentBuilderException {
			final String modelIdRef = startElement.getAttributeByName(new QName(STATE_ATTR_NAME_MODEL)).getValue();
			final ModelProvider modelProvider = parseModelIdRef(modelIdRef, startElement.getNamespaceContext());
			final GameModel model = modelProvider.getModel();
			final PluginSet pluginSet = modelProvider.getPluginSet();
			final ModelRefResolver modelResolver = modelProvider.getModelRefResolver();

			m_state = new StandardGameState(model, pluginSet);
			try (final StandardGameState.Resetter resetter = m_state.reset()) {
				final StateParser stateParser = new StateParser(modelResolver, resetter, requireManager(pluginSet, m_stateId));
				stateParser.parse(dispatcher);
			} catch (final Exception ex) {
				throw new GameComponentBuilderException(CORE, ex);
			}
		}
	}

	public void write(GameState state, OutputStream os, String modelId) throws TransformerException, GameComponentBuilderException, XMLStreamException {
		final ModelProvider modelProvider = m_modelProviderFactory.apply(modelId);
		final ModelRefResolver modelRefResolver = modelProvider.getModelRefResolver();

		final AbstractWriter stateWriter = new StateWriter(state, modelRefResolver, modelId, m_authorizationManager);
		final Document document = newDocument();

		stateWriter.write(document);
		transform(document, os);
	}
}
