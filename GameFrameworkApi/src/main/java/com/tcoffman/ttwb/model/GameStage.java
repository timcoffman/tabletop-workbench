package com.tcoffman.ttwb.model;

import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameDocumentableComponent;

public interface GameStage extends GameStageContainer, GameDocumentableComponent {

	boolean isTerminal();

	Stream<GameRule> rules();

	@Override
	default GameModel model() {
		return container().model();
	}

	GameStageContainer container();
}
