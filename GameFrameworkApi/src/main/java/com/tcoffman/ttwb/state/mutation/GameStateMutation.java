package com.tcoffman.ttwb.state.mutation;

import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.state.GameState;

public interface GameStateMutation {

	void apply(GameState.Mutator stateMutator);

	GameOperation.Type getType();

	GameRole getRole();

	<R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E;

	interface Visitor<R, E extends Throwable> {
		R visit(GameStateAddRelationship addRelationship) throws E;

		R visit(GameStateRemoveRelationship removeRelationship) throws E;
	}

}
