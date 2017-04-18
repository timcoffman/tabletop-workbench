package com.tcoffman.ttwb.plugin;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class PluginSetTests {

	private static final PluginName PLUGIN_NAME = new PluginName("a.b.c", "1.0");

	@Test
	public void canRequirePlugin() throws PluginException {
		final PluginFactory pluginFactory = mock(PluginFactory.class);
		final Plugin plugin = mock(Plugin.class);
		when(pluginFactory.create(PLUGIN_NAME)).thenReturn(plugin);

		final PluginSet pluginSet = new PluginSet(pluginFactory);

		verify(pluginFactory, times(0)).create(PLUGIN_NAME);

		assertThat(pluginSet.requirePlugin(PLUGIN_NAME), is(plugin));
		verify(pluginFactory, times(1)).create(PLUGIN_NAME);

		assertThat(pluginSet.requirePlugin(PLUGIN_NAME), is(plugin));
		verify(pluginFactory, times(1)).create(PLUGIN_NAME);
	}

}
