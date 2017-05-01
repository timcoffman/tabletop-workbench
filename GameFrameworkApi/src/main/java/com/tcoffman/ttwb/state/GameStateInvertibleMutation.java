package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GameRole;

public abstract class GameStateInvertibleMutation extends GameStateMutation {

	public GameStateInvertibleMutation(GameRole role, GameOperation.Type type) {
		super(role, type);
	}

	public abstract GameStateMutation inverted();

}