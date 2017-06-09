package com.tcoffman.ttwb.state.persistence;

import com.tcoffman.ttwb.component.persistence.GameComponentRefManager;
import com.tcoffman.ttwb.component.persistence.GameComponentRefResolver;
import com.tcoffman.ttwb.component.persistence.StandardComponentRefManager;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.PluginSet;
import com.tcoffman.ttwb.plugin.StatePlugin;
import com.tcoffman.ttwb.state.GamePart;

public class StandardStateRefManager implements StateRefManager {
	private final PluginSet m_pluginSet;
	private final StandardComponentRefManager<GamePart> m_partRefManager = new StandardComponentRefManager<GamePart>("part");

	public StandardStateRefManager(PluginSet pluginSet) {
		m_pluginSet = pluginSet;
	}

	@Override
	public GameComponentRefResolver<GamePart> getPartResolver() {
		return m_partRefManager;
	}

	@Override
	public GameComponentRefManager<GamePart> getPartManager() {
		return m_partRefManager;
	}

	@Override
	public void resolveAll() {
		m_partRefManager.resolveAll();
	}

	@Override
	public StatePlugin requireStatePlugin(PluginName pluginName) throws PluginException {
		return (StatePlugin) m_pluginSet.requirePlugin(pluginName);
	}

}