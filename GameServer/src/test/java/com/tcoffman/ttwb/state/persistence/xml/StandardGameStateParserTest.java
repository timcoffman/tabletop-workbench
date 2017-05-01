package com.tcoffman.ttwb.state.persistence.xml;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;
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
import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.junit.Before;
import org.junit.Test;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.GameComponentRefResolver;
import com.tcoffman.ttwb.component.persistence.xml.BundleHelper;
import com.tcoffman.ttwb.core.Core;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefResolver;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.persistance.xml.StandardGameModelParser;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.StatePlugin;
import com.tcoffman.ttwb.state.GameState;
import com.tcoffman.ttwb.state.StandardGameState;

public class StandardGameStateParserTest {

	private PluginFactory m_pluginFactory;
	private GameModel m_completeModel;
	private GameModel m_minimalModel;
	private static final String COMPLETE_MODEL_ID = "complete-model";
	private static final String MINIMAL_MODEL_ID = "minimal-model";
	private static final String DOC_NAME = "COMPONENT-NAME";
	private static final String DOC_DESC = "COMPONENT-DESC";
	private StandardGameStateParser m_standardGameStateCompleteParser;
	private StandardGameStateParser m_standardGameStateMinimalParser;

	private interface CombinedPlugin extends ModelPlugin, StatePlugin {

	}

	@Before
	public void setup() throws PluginException, XMLStreamException {
		m_pluginFactory = mock(PluginFactory.class);

		when(m_pluginFactory.create(any(PluginName.class))).thenAnswer(invocation -> {
			final PluginName pluginName = (PluginName) invocation.getArguments()[0];
			if (CORE.equals(pluginName)) {
				final Core corePlugin = new Core();
				corePlugin.setName(CORE);
				return corePlugin;
			} else {
				final StatePlugin genericPlugin = mock(CombinedPlugin.class);
				when(genericPlugin.getName()).thenReturn(pluginName);
				return genericPlugin;
			}
		});

		@SuppressWarnings("unchecked")
		final GameComponentRefResolver<GameComponentDocumentation> prototypeDocumentationResolver = mock(GameComponentRefResolver.class);
		when(prototypeDocumentationResolver.lookup(anyString())).thenAnswer(invocation -> {
			final String id = (String) invocation.getArguments()[0];
			final GameComponentDocumentation documentation = mock(GameComponentDocumentation.class);
			when(documentation.getName(any(GameComponentDocumentation.Format.class))).thenReturn(id);
			when(documentation.getDescription()).thenReturn(id);
			when(documentation.toString()).thenReturn(id);
			final GameComponentRef<GameComponentDocumentation> docRef = () -> documentation;
			return Optional.of(docRef);
		});

		final DocumentationRefResolver documentationRefResolver = mock(DocumentationRefResolver.class);
		when(documentationRefResolver.getPrototypeResolver()).thenReturn(prototypeDocumentationResolver);

		final StandardGameModelParser modelParser = new StandardGameModelParser(m_pluginFactory, documentationRefResolver);

		m_completeModel = modelParser.parse(BundleHelper.getResourceAsStream(COMPLETE_MODEL_ID + "/model.xml"), COMPLETE_MODEL_ID);
		m_standardGameStateCompleteParser = new StandardGameStateParser(m_completeModel, m_pluginFactory, modelParser.createResolver(COMPLETE_MODEL_ID));

		m_minimalModel = modelParser.parse(BundleHelper.getResourceAsStream(MINIMAL_MODEL_ID + "/model.xml"), MINIMAL_MODEL_ID);
		m_standardGameStateMinimalParser = new StandardGameStateParser(m_minimalModel, m_pluginFactory, modelParser.createResolver(MINIMAL_MODEL_ID));
	}

	@Test
	public void canParseCompleteState() throws GameComponentBuilderException, XMLStreamException, TransformerException {
		final GameState state = m_standardGameStateCompleteParser.parse(StandardGameStateParserTest.class.getResourceAsStream("complete-state.xml"));
		assertThat(state, notNullValue());
		assertThat(state.getModel(), equalTo(m_completeModel));

		m_standardGameStateCompleteParser.write(state, System.out, COMPLETE_MODEL_ID);
	}

	@Test
	public void canParseMinimalState() throws GameComponentBuilderException, XMLStreamException, TransformerException {
		final GameState state = m_standardGameStateMinimalParser.parse(StandardGameStateParserTest.class.getResourceAsStream("minimal-state.xml"));
		assertThat(state, notNullValue());
		assertThat(state.getModel(), equalTo(m_minimalModel));

		m_standardGameStateMinimalParser.write(state, System.out, MINIMAL_MODEL_ID);
	}

	@Test
	public void canWriteMinimalState() throws TransformerException, PluginException, UnsupportedEncodingException {
		final StandardGameState state = new StandardGameState(m_completeModel, m_pluginFactory);

		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		m_standardGameStateCompleteParser.write(state, os, MINIMAL_MODEL_ID);
		final String xml = os.toString("UTF-8");
		assertThat(xml, containsString("minimal"));

	}
}
