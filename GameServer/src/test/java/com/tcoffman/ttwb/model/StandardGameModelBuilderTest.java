package com.tcoffman.ttwb.model;

import static com.tcoffman.ttwb.component.TestComponentRef.emptyRef;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.plugin.PluginName;

public class StandardGameModelBuilderTest {

	private static final String MODEL_NAME = "MODEL_NAME";
	private static final PluginName PLUGIN_NAME = new PluginName("a.b.c", "1.0");

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private PluginFactory m_pluginFactory;
	private StandardGameModelBuilder m_builder;

	@Before
	public void setupStandardGameModelBuilder() throws PluginException {
		m_pluginFactory = mock(PluginFactory.class);
		doThrow(PluginException.class).when(m_pluginFactory).create(any(PluginName.class));

		m_builder = new StandardGameModelBuilder(m_pluginFactory);
	}

	@Test
	public void cannotBuildWithoutRequiredPlugin() throws PluginException {
		thrown.expect(PluginException.class);
		m_builder.addRequiredPlugin(PLUGIN_NAME);
	}

	@Test
	public void cannotBuildWithInvalidModel() throws GameComponentBuilderException, PluginException {
		final ModelPlugin modelPlugin = mock(ModelPlugin.class);
		when(modelPlugin.getName()).thenReturn(PLUGIN_NAME);
		doReturn(modelPlugin).when(m_pluginFactory).create(PLUGIN_NAME);

		doAnswer(invocation -> {
			@SuppressWarnings("unchecked")
			final Consumer<GameComponentBuilderException> reporter = (Consumer<GameComponentBuilderException>) invocation.getArguments()[1];
			reporter.accept(new GameComponentBuilderException(PLUGIN_NAME));
			return null;
		}).when(modelPlugin).validate(any(GameModel.class), any(Consumer.class));

		m_builder.addRequiredPlugin(PLUGIN_NAME);
		thrown.expect(GameComponentBuilderException.class);
		m_builder.build();
	}

	@Test
	public void canBuild() throws PluginException {
		m_builder.setInitialStage(emptyRef());

		final GameModel model = m_builder.setDocumentation(GameComponentRef.wrap(mock(GameComponentDocumentation.class))).build();
		assertThat(model, notNullValue());
	}

}
