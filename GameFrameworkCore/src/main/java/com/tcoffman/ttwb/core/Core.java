package com.tcoffman.ttwb.core;

import java.util.function.Consumer;

import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GameModelBuilderException;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.StatePlugin;
import com.tcoffman.ttwb.state.GameState;
import com.tcoffman.ttwb.state.GameStateException;

public class Core implements ModelPlugin, StatePlugin {

	@Override
	public void validate(GameState state, Consumer<GameStateException> reporter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate(GameModel model, Consumer<GameModelBuilderException> reporter) {
		// TODO Auto-generated method stub

	}

}
