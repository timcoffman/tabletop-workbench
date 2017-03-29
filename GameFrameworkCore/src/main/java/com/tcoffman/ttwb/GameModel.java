package com.tcoffman.ttwb;

import com.tcoffman.ttwb.plugin.Plugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.PluginSet;

public class GameModel {

	private final PluginSet m_pluginSet;

	public GameModel(PluginFactory pluginFactory) {
		m_pluginSet = new PluginSet(pluginFactory);
	}

	private Plugin requirePlugin(PluginName name) throws PluginException {
		return m_pluginSet.requirePlugin(name);
	}

}
