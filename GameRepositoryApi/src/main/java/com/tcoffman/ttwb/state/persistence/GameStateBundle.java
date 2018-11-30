package com.tcoffman.ttwb.state.persistence;

import com.tcoffman.ttwb.plugin.PluginSet;
import com.tcoffman.ttwb.state.GameState;

public interface GameStateBundle {

	PluginSet getPluginSet();

	String getStateId();

	GameState getState();

	StateRefResolver getStateRefResolver();

	String getModelId();

}