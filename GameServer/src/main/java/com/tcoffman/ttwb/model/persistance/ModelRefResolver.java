package com.tcoffman.ttwb.model.persistance;

import com.tcoffman.ttwb.component.persistence.GameComponentRefResolver;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;

public interface ModelRefResolver {

	GameComponentRefResolver<GameStage> getStageResolver();

	GameComponentRefResolver<GamePartPrototype> getPartPrototypeResolver();

	GameComponentRefResolver<GameRole> getRoleResolver();

	GameComponentRefResolver<GameModel> getImportedModelResolver();

	ModelPlugin requireModelPlugin(PluginName pluginName) throws PluginException;

}