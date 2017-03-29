package com.tcoffman.ttwb.plugin;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PluginFactoryTest {

	private static final PluginName PLUGIN_NAME = new PluginName("a.b.c", "1.0");

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void cannotCreateUnknownPlugin() throws PluginException {
		final DefaultPluginFactory pluginFactory = new DefaultPluginFactory();

		thrown.expect(PluginException.class);
		thrown.expectMessage(containsString(PLUGIN_NAME.toString()));
		pluginFactory.create(PLUGIN_NAME);
	}

	private static class PrivateTestPlugin implements Plugin {

	}

	public static class TestPlugin implements Plugin {

	}

	public static class MissingConstructorTestPlugin implements Plugin {
		public MissingConstructorTestPlugin(int i) {
		};
	}

	@Test
	public void canCreateRegisteredPlugin() throws PluginException {
		final DefaultPluginFactory pluginFactory = new DefaultPluginFactory();
		pluginFactory.install(PLUGIN_NAME, TestPlugin.class);

		final Plugin plugin = pluginFactory.create(PLUGIN_NAME);
		assertThat(plugin, instanceOf(TestPlugin.class));
	}

	@Test
	public void cannotCreatePluginLackingANoArgConstructor() throws PluginException {
		final DefaultPluginFactory pluginFactory = new DefaultPluginFactory();
		pluginFactory.install(PLUGIN_NAME, MissingConstructorTestPlugin.class);

		thrown.expect(PluginException.class);
		thrown.expectMessage(containsString(PLUGIN_NAME.toString()));
		final Plugin plugin = pluginFactory.create(PLUGIN_NAME);
		assertThat(plugin, instanceOf(TestPlugin.class));
	}

	@Test
	public void cannotCreatePrivatePlugin() throws PluginException {
		final DefaultPluginFactory pluginFactory = new DefaultPluginFactory();
		pluginFactory.install(PLUGIN_NAME, PrivateTestPlugin.class);

		thrown.expect(PluginException.class);
		thrown.expectMessage(containsString(PLUGIN_NAME.toString()));
		pluginFactory.create(PLUGIN_NAME);
	}
}
