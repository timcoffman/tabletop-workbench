package com.tcoffman.ttwb.model.persistance;

import com.tcoffman.ttwb.component.persistence.GameComponentRefManager;
import com.tcoffman.ttwb.component.persistence.GameComponentRefResolver;
import com.tcoffman.ttwb.component.persistence.StandardComponentRefManager;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.PluginSet;

public class StandardModelRefManager implements ModelRefManager {
	private final PluginSet m_pluginSet;
	private final StandardComponentRefManager<GameStage> m_stageRefManager = new StandardComponentRefManager<GameStage>("stage");
	private final StandardComponentRefManager<GamePartPrototype> m_prototypeRefManager = new StandardComponentRefManager<GamePartPrototype>("prototype");
	private final StandardComponentRefManager<GameRole> m_roleRefManager = new StandardComponentRefManager<GameRole>("role");

	public StandardModelRefManager(PluginSet pluginSet) {
		m_pluginSet = pluginSet;
	}

	@Override
	public GameComponentRefResolver<GameStage> getStageResolver() {
		return m_stageRefManager;
	}

	@Override
	public GameComponentRefResolver<GamePartPrototype> getPartPrototypeResolver() {
		return m_prototypeRefManager;
	}

	@Override
	public GameComponentRefResolver<GameRole> getRoleResolver() {
		return m_roleRefManager;
	}

	@Override
	public GameComponentRefManager<GameStage> getStageManager() {
		return m_stageRefManager;
	}

	@Override
	public GameComponentRefManager<GamePartPrototype> getPartPrototypeManager() {
		return m_prototypeRefManager;
	}

	@Override
	public GameComponentRefManager<GameRole> getRoleManager() {
		return m_roleRefManager;
	}

	@Override
	public void resolveAll() {
		m_stageRefManager.resolveAll();
		m_prototypeRefManager.resolveAll();
		m_roleRefManager.resolveAll();
	}

	@Override
	public ModelPlugin requireModelPlugin(PluginName pluginName) throws PluginException {
		return (ModelPlugin) m_pluginSet.requirePlugin(pluginName);
	}

}