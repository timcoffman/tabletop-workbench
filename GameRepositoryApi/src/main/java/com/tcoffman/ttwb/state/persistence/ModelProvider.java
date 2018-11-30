package com.tcoffman.ttwb.state.persistence;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.plugin.PluginSet;

public interface ModelProvider {
	GameModel getModel() throws GameComponentBuilderException;

	PluginSet getPluginSet() throws GameComponentBuilderException;

	ModelRefResolver getModelRefResolver() throws GameComponentBuilderException;
}