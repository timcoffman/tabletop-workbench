package com.tcoffman.ttwb.model;

import java.util.stream.Stream;

public interface GameStageContainer {

	GameModel model();

	Stream<? extends GameStage> stages();

}
