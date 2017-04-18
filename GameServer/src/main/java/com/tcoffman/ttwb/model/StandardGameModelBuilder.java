package com.tcoffman.ttwb.model;

import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.PluginSet;

public class StandardGameModelBuilder {

	private final StandardGameModel.Editor m_editor;
	private final PluginSet m_plugins;

	public StandardGameModelBuilder(PluginFactory pluginFactory) {
		m_editor = StandardGameModel.create();
		m_plugins = new PluginSet(pluginFactory);
	}

	public GameModel build() throws GameModelBuilderException {
		return m_editor.done();
	}

	public StandardGameModelBuilder setName(String name) {
		m_editor.setName(name);
		return this;
	}

	public StandardGameModelBuilder addRequiredPlugin(PluginName name) throws PluginException {
		m_editor.addRequiredPlugin(name, m_plugins.requirePlugin(name));
		return this;
	}

	public StandardGameRole.Editor createRole() throws PluginException {
		return m_editor.createRole();
	}

	public StandardGameModelBuilder setInitialStage(GameStageRef stageRef) throws PluginException {
		m_editor.setInitialStage(stageRef);
		return this;
	}

	public StandardGameStage.Editor createStage() throws PluginException {
		return m_editor.createStage();
	}

}
