package com.tcoffman.ttwb.model.persistence.xml;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.junit.Before;
import org.junit.Test;

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
	public void setup() {
		m_pluginFactory = mock(PluginFactory.class);
		m_standardGameModelParser = new StandardGameModelParser(m_pluginFactory);
	}

	@Test
	public void canParseCompleteModel() throws GameModelBuilderException, XMLStreamException {
		final GameModel model = m_standardGameModelParser.parse(StandardGameModelParserTest.class.getResourceAsStream("model.xml"));
		assertThat(model, notNullValue());
		assertThat(model.getName(), equalTo("Tic-Tac-Toe"));

	}

	@Test
	public void canParseMinimalModel() throws GameModelBuilderException, XMLStreamException {
		final GameModel model = m_standardGameModelParser.parse(StandardGameModelParserTest.class.getResourceAsStream("minimal.xml"));
		assertThat(model, notNullValue());
		assertThat(model.getName(), equalTo("Minimal"));

	}

	@Test
	public void canWriteMinimalModel() throws TransformerException, PluginException {
		final StandardGameModelBuilder builder = new StandardGameModelBuilder(m_pluginFactory);
		builder.setName("minimal");
		builder.createStage().done();
		final GameModel model = builder.build();
		m_standardGameModelParser.write(model, System.out);

	}
}
