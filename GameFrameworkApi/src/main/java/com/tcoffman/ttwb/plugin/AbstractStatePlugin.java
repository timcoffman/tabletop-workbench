package com.tcoffman.ttwb.plugin;

import java.util.function.Consumer;

import com.tcoffman.ttwb.state.GameState;
import com.tcoffman.ttwb.state.GameStateException;

public class AbstractStatePlugin extends AbstractPlugin implements StatePlugin {

	@Override
	public void validate(GameState state, Consumer<GameStateException> reporter) {
		/* nothing to report */
	}

}
