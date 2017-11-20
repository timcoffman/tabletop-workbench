package com.tcoffman.ttwb.plugin;

import java.util.HashMap;
import java.util.Map;

public class PluginSet {

	private final PluginSet m_parent;
	private final PluginFactory m_pluginFactory;

	private final Map<PluginName, Plugin> m_plugins = new HashMap<>();

	public PluginSet(PluginFactory pluginFactory) {
		this(null, pluginFactory);
	}

	public PluginSet(PluginSet parent, PluginFactory pluginFactory) {
		m_parent = parent;
		m_pluginFactory = pluginFactory;
	}

	public Plugin requirePlugin(PluginName name) throws PluginException {
		Plugin plugin = m_plugins.get(name);
		if (null == plugin && null != m_parent)
			plugin = m_parent.requirePlugin(name);
		if (null == plugin)
			m_plugins.put(name, plugin = m_pluginFactory.create(name));
		return plugin;
	}

}
