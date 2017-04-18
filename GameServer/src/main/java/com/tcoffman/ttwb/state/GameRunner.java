package com.tcoffman.ttwb.state;

import java.util.stream.Collectors;

public class GameRunner {

	public void step(GameState state) {

		state.allowedOperations().collect(Collectors.toList());

	}

	public void mutate(GameState state, GameOperationSet ops) {
		final GameStateLogEntry log = new GameStateLogEntry(ops.getResult(), state.getCurrentStage());
		ops.operations().forEach((op) -> {
			op.visit(new GameOperation.Visitor<Void>() {

				@Override
				public Void visit(GameOperation op) {
					throw new UnsupportedOperationException("game operation " + op.getClass().getSimpleName() + " (" + op.getType() + ") not supported");
				}

				@Override
				public Void visit(GameSignalOperation op) {
					/* no state mutations from a SIGNAL */
					return null;
				}

			});
		});

		state.mutate(log);

	}

}
