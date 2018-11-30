package com.tcoffman.ttwb.state.persistence.xml;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;
import static com.tcoffman.ttwb.plugin.CorePlugins.GRID;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.junit.Before;
import org.junit.Test;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefResolver;
import com.tcoffman.ttwb.component.persistence.GameComponentRefResolver;
import com.tcoffman.ttwb.component.persistence.xml.BundleHelper;
import com.tcoffman.ttwb.core.Core;
import com.tcoffman.ttwb.core.Grid;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.PluginSet;
import com.tcoffman.ttwb.plugin.StatePlugin;
import com.tcoffman.ttwb.state.GameAuthorizationManager;
import com.tcoffman.ttwb.state.GameState;
import com.tcoffman.ttwb.state.StandardGameState;

public class StandardGameStateParserTest {

	private PluginSet m_pluginSet;
	private GameAuthorizationManager m_authMgr;
	private GameModel m_completeModel;
	private GameModel m_minimalModel;
	private static final String COMPLETE_MODEL_ID = "complete";
	private static final String MINIMAL_MODEL_ID = "minimal";
	private StandardGameStateParser m_standardGameStateCompleteParser;
	private StandardGameStateParser m_standardGameStateMinimalParser;
	private static final String STATE_ID = "state";

	private interface CombinedPlugin extends ModelPlugin, StatePlugin {

	}

	private GameComponentRef<GameComponentDocumentation> mockDocumentationForId(final String id) {
		final GameComponentDocumentation documentation = mock(GameComponentDocumentation.class);
		when(documentation.getName(any(GameComponentDocumentation.Format.class))).thenReturn(id);
		when(documentation.getDescription()).thenReturn(id);
		when(documentation.toString()).thenReturn(id);
		final GameComponentRef<GameComponentDocumentation> docRef = GameComponentRef.wrap(documentation);
		return docRef;
	}

	@Before
	public void setup() throws PluginException, XMLStreamException {
		m_pluginSet = mock(PluginSet.class);

		final Core corePlugin = new Core();
		corePlugin.setName(CORE);

		final Grid gridPlugin = new Grid();
		gridPlugin.setName(GRID);

		final Map<PluginName, StatePlugin> mockPlugins = new HashMap<>();

		when(m_pluginSet.requirePlugin(any(PluginName.class))).thenAnswer(invocation -> {
			final PluginName pluginName = (PluginName) invocation.getArguments()[0];
			if (CORE.equals(pluginName))
				return corePlugin;
			else if (GRID.equals(pluginName))
				return gridPlugin;
			else {
				StatePlugin genericPlugin = mockPlugins.get(pluginName);
				if (null == genericPlugin) {
					genericPlugin = mock(CombinedPlugin.class);
					when(genericPlugin.getName()).thenReturn(pluginName);
					mockPlugins.put(pluginName, genericPlugin);
				}
				return genericPlugin;
			}
		});

		m_authMgr = mock(GameAuthorizationManager.class);
		when(m_authMgr.deserializeAuthorization(anyString())).thenReturn("AUTHORIZATION");
		when(m_authMgr.serializeAuthorization(any())).thenReturn("AUTHORIZATION");

		@SuppressWarnings("unchecked")
		final GameComponentRefResolver<GameComponentDocumentation> genericDocumentationResolver = mock(GameComponentRefResolver.class);
		when(genericDocumentationResolver.lookup(anyString())).thenAnswer(invocation -> {
			final String id = (String) invocation.getArguments()[0];
			final GameComponentRef<GameComponentDocumentation> docRef = mockDocumentationForId(id);
			return Optional.of(docRef);
		});

		final DocumentationRefResolver documentationRefResolver = mock(DocumentationRefResolver.class);
		when(documentationRefResolver.getModel()).thenAnswer(invocation -> {
			return mockDocumentationForId("model");
		});
		when(documentationRefResolver.getPrototypeResolver()).thenReturn(genericDocumentationResolver);
		when(documentationRefResolver.getStageResolver()).thenReturn(genericDocumentationResolver);
		when(documentationRefResolver.getRoleResolver()).thenReturn(genericDocumentationResolver);
		when(documentationRefResolver.getRuleResolver()).thenReturn(genericDocumentationResolver);
		when(documentationRefResolver.getOperationResolver()).thenReturn(genericDocumentationResolver);

		m_completeModel = BundleHelper.instance().getBundle(COMPLETE_MODEL_ID).getModel();
		m_standardGameStateCompleteParser = new StandardGameStateParser(m_authMgr, (modelId) -> BundleHelper.instance().createModelProvider(modelId, "en-us"));

		m_minimalModel = BundleHelper.instance().getBundle(MINIMAL_MODEL_ID).getModel();
		m_standardGameStateMinimalParser = new StandardGameStateParser(m_authMgr, (modelId) -> BundleHelper.instance().createModelProvider(modelId, "en-us"));
	}

	@Test
	public void canParseCompleteState() throws GameComponentBuilderException, XMLStreamException, TransformerException {
		final GameState state = m_standardGameStateCompleteParser.parse(BundleHelper.class.getResourceAsStream("complete-state.xml"), STATE_ID);
		assertThat(state, notNullValue());
		assertThat(state.getModel(), equalTo(m_completeModel));

		// m_standardGameStateCompleteParser.write(state, System.out,
		// COMPLETE_MODEL_ID);
	}

	@Test
	public void canParseMinimalState() throws GameComponentBuilderException, XMLStreamException, TransformerException {
		final GameState state = m_standardGameStateMinimalParser.parse(BundleHelper.getResourceAsStream("minimal-state.xml"), STATE_ID);
		assertThat(state, notNullValue());
		assertThat(state.getModel(), equalTo(m_minimalModel));

		// m_standardGameStateMinimalParser.write(state, System.out,
		// MINIMAL_MODEL_ID);
	}

	@Test
	public void canWriteMinimalState() throws TransformerException, PluginException, UnsupportedEncodingException, XMLStreamException {
		final StandardGameState state = new StandardGameState(m_minimalModel, m_pluginSet);

		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		m_standardGameStateMinimalParser.write(state, os, MINIMAL_MODEL_ID);
		final String xml = os.toString("UTF-8");
		assertThat(xml, containsString("minimal"));

	}
}
