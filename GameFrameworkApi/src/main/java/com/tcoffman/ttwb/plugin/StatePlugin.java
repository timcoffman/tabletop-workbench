package com.tcoffman.ttwb.plugin;

import java.util.function.Consumer;

import com.tcoffman.ttwb.state.GameState;
import com.tcoffman.ttwb.state.GameStateException;

public interface StatePlugin extends Plugin {

	void validate(GameState state, Consumer<GameStateException> reporter);

}
