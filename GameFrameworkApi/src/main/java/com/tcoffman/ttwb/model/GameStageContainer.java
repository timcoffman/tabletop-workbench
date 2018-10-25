package com.tcoffman.ttwb.model;

import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;

public interface GameStageContainer {

	GameModel model();

	Stream<? extends GameStage> stages();

	Optional<GameComponentRef<GameStage>> getInitialStage();

}
