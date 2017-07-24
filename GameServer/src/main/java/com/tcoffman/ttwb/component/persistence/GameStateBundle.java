package com.tcoffman.ttwb.component.persistence;

import com.tcoffman.ttwb.plugin.PluginSet;
import com.tcoffman.ttwb.state.GameState;
import com.tcoffman.ttwb.state.persistence.StateRefResolver;

public interface GameStateBundle {

	PluginSet getPluginSet();

	String getStateId();

	GameState getState();

	StateRefResolver getStateRefResolver();

	String getModelId();

}