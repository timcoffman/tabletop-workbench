package com.tcoffman.ttwb.model.persistence.xml;

import static com.tcoffman.ttwb.doc.GameComponentDocumentation.Format.SHORT;
import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ELEMENT_QNAME_INITIAL_STAGE;
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
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.junit.Before;
import org.junit.Test;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.GameComponentRefResolver;
import com.tcoffman.ttwb.component.persistence.xml.BundleHelper;
import com.tcoffman.ttwb.core.Core;
import com.tcoffman.ttwb.core.Grid;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefResolver;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.StandardGameModelBuilder;
import com.tcoffman.ttwb.model.persistance.xml.StandardGameModelParser;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.plugin.PluginName;

public class StandardGameModelParserTest {

	private PluginFactory m_pluginFactory;
	private StandardGameModelParser m_standardGameModelParser;

	private GameComponentRef<GameComponentDocumentation> mockDocumentationForId(final String id) {
		final GameComponentDocumentation documentation = mock(GameComponentDocumentation.class);
		when(documentation.getName(any(GameComponentDocumentation.Format.class))).thenReturn(id);
		when(documentation.getDescription()).thenReturn(id);
		when(documentation.toString()).thenReturn(id);
		final GameComponentRef<GameComponentDocumentation> docRef = GameComponentRef.wrap(documentation);
		return docRef;
	}

	@Before
	public void setup() throws PluginException {
		m_pluginFactory = mock(PluginFactory.class);

		when(m_pluginFactory.create(any(PluginName.class))).thenAnswer(invocation -> {
			final PluginName pluginName = (PluginName) invocation.getArguments()[0];
			if (CORE.equals(pluginName)) {
				final Core corePlugin = new Core();
				corePlugin.setName(CORE);
				return corePlugin;
			} else if (GRID.equals(pluginName)) {
				final Grid gridPlugin = new Grid();
				gridPlugin.setName(GRID);
				return gridPlugin;
			} else {
				final ModelPlugin genericPlugin = mock(ModelPlugin.class);
				when(genericPlugin.getName()).thenReturn(pluginName);
				return genericPlugin;
			}
		});

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

		m_standardGameModelParser = new StandardGameModelParser(m_pluginFactory, documentationRefResolver);
	}

	@Test
	public void canParseCompleteModel() throws GameComponentBuilderException, XMLStreamException, TransformerException {
		final GameModel model = m_standardGameModelParser.parse(BundleHelper.getResourceAsStream("complete-model/model.xml"), "complete-model");
		assertThat(model, notNullValue());
		assertThat(model.getDocumentation().getName(SHORT), equalTo("model"));

		assertThat(model.prototypes().collect(Collectors.counting()), equalTo(5L));

		// m_standardGameModelParser.write(model, System.out, "complete-model");
	}

	@Test
	public void canParseMinimalModel() throws GameComponentBuilderException, XMLStreamException {
		final GameModel model = m_standardGameModelParser.parse(BundleHelper.getResourceAsStream("minimal-model/model.xml"), "minimal-model");
		assertThat(model, notNullValue());
		assertThat(model.getDocumentation().getName(SHORT), equalTo("model"));

	}

	@Test
	public void canWriteMinimalModel() throws TransformerException, PluginException, UnsupportedEncodingException {
		final StandardGameModelBuilder builder = new StandardGameModelBuilder(m_pluginFactory);
		builder.setDocumentation(GameComponentRef.wrap(mock(GameComponentDocumentation.class))).createStage((s) -> {
			s.setDocumentation(GameComponentRef.wrap(mock(GameComponentDocumentation.class)));
		});
		final GameModel model = builder.build();

		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		m_standardGameModelParser.write(model, os, "minimal-model");
		final String xml = os.toString("UTF-8");
		assertThat(xml, containsString(MODEL_ELEMENT_QNAME_INITIAL_STAGE.getLocalPart()));

	}
}
