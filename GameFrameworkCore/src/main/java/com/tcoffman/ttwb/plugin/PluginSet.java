package com.tcoffman.ttwb.plugin;

import java.util.HashMap;
import java.util.Map;

public class PluginSet {

	private final PluginFactory m_pluginFactory;

	private final Map<PluginName, Plugin> m_plugins = new HashMap<PluginName, Plugin>();

	public PluginSet(PluginFactory pluginFactory) {
		m_pluginFactory = pluginFactory;
	}

	public Plugin requirePlugin(PluginName name) throws PluginException {
		Plugin plugin = m_plugins.get(name);
		if (null == plugin)
			m_plugins.put(name, plugin = m_pluginFactory.create(name));
		return plugin;
	}

}
