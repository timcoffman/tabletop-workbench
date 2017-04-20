package com.tcoffman.ttwb.model.persistence.xml;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.junit.Before;
import org.junit.Test;

import com.tcoffman.ttwb.core.Core;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GameModelBuilderException;
import com.tcoffman.ttwb.model.StandardGameModelBuilder;
import com.tcoffman.ttwb.model.persistance.xml.StandardGameModelParser;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginFactory;

public class StandardGameModelParserTest {

	private PluginFactory m_pluginFactory;
	private StandardGameModelParser m_standardGameModelParser;

	@Before
	public void setup() throws PluginException {
		m_pluginFactory = mock(PluginFactory.class);
		final Core corePlugin = new Core();
		corePlugin.setName(CORE);
		when(m_pluginFactory.create(CORE)).thenReturn(corePlugin);
		m_standardGameModelParser = new StandardGameModelParser(m_pluginFactory);
	}

	@Test
	public void canParseCompleteModel() throws GameModelBuilderException, XMLStreamException, TransformerException {
		final GameModel model = m_standardGameModelParser.parse(StandardGameModelParserTest.class.getResourceAsStream("complete-model/model.xml"));
		assertThat(model, notNullValue());
		assertThat(model.getName(), equalTo("Tic-Tac-Toe"));

		assertThat(model.prototypes().collect(Collectors.counting()), equalTo(5L));

		m_standardGameModelParser.write(model, System.out);
	}

	@Test
	public void canParseMinimalModel() throws GameModelBuilderException, XMLStreamException {
		final GameModel model = m_standardGameModelParser.parse(StandardGameModelParserTest.class.getResourceAsStream("minimal-model/model.xml"));
		assertThat(model, notNullValue());
		assertThat(model.getName(), equalTo("Minimal"));

	}

	@Test
	public void canWriteMinimalModel() throws TransformerException, PluginException, UnsupportedEncodingException {
		final StandardGameModelBuilder builder = new StandardGameModelBuilder(m_pluginFactory);
		builder.setName("minimal");
		builder.createStage((s) -> {
		});
		final GameModel model = builder.build();

		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		m_standardGameModelParser.write(model, os);
		final String xml = os.toString("UTF-8");
		assertThat(xml, containsString("minimal"));

	}
}
