package com.tcoffman.ttwb.model;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.AbstractEditor.Initializer;
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

	public GameModel build() throws GameComponentBuilderException {
		return m_editor.done();
	}

	public StandardGameModelBuilder setName(String name) {
		m_editor.setName(name);
		return this;
	}

	public StandardGameModelBuilder addRequiredPlugin(PluginName name) throws PluginException {
		m_editor.addRequiredPlugin(m_plugins.requirePlugin(name));
		return this;
	}

	public StandardGameModelBuilder createRole(Initializer<StandardGameRole.Editor> initializer) throws GameComponentBuilderException {
		m_editor.createRole(initializer);
		return this;
	}

	public StandardGameModelBuilder setInitialStage(GameComponentRef<GameStage> stageRef) {
		m_editor.setInitialStage(stageRef);
		return this;
	}

	public StandardGameModelBuilder createStage(AbstractEditor.Initializer<StandardGameStage.Editor> initializer) throws GameComponentBuilderException {
		m_editor.createStage(initializer);
		return this;
	}

	public StandardGameModelBuilder createPrototype(PluginName declaringPlugin, AbstractEditor.Initializer<StandardGamePartPrototype.Editor> initializer)
			throws GameComponentBuilderException {
		m_editor.createPrototype(declaringPlugin, initializer);
		return this;
	}

	public StandardGameModelBuilder createPart(AbstractEditor.Initializer<StandardGamePartInstance.Editor> initializer) throws GameComponentBuilderException {
		m_editor.createPart(initializer);
		return this;
	}

}
