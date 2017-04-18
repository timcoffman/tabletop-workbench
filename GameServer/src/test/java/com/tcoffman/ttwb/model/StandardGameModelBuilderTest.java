package com.tcoffman.ttwb.model;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
	public void cannotBuildWithInvalidModel() throws GameModelBuilderException, PluginException {
		final ModelPlugin modelPlugin = mock(ModelPlugin.class);
		doReturn(modelPlugin).when(m_pluginFactory).create(PLUGIN_NAME);

		doAnswer(invocation -> {
			@SuppressWarnings("unchecked")
			final Consumer<GameModelBuilderException> reporter = (Consumer<GameModelBuilderException>) invocation.getArguments()[1];
			reporter.accept(new GameModelBuilderException(PLUGIN_NAME));
			return null;
		}).when(modelPlugin).validate(any(GameModel.class), any(Consumer.class));

		m_builder.addRequiredPlugin(PLUGIN_NAME);
		thrown.expect(GameModelBuilderException.class);
		m_builder.build();
	}

	@Test
	public void canBuild() throws PluginException {
		m_builder.setName(MODEL_NAME);
		m_builder.setInitialStage(() -> null);

		final GameModel model = m_builder.build();
		assertThat(model, notNullValue());
	}

}
