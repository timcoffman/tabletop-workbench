package com.tcoffman.ttwb.state.mutation;

public interface GameStateInvertibleMutation extends GameStateMutation {

	AbstractGameStateMutation inverted();

}
