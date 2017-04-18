package com.tcoffman.ttwb.model;

import java.util.stream.Stream;

public interface GameStage extends GameStageContainer {

	boolean isTerminal();

	Stream<GameRule> rules();

	@Override
	default GameModel model() {
		return container().model();
	}

	GameStageContainer container();
}
