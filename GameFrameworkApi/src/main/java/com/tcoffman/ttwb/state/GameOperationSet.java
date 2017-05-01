package com.tcoffman.ttwb.state;

import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GameStage;

public interface GameOperationSet {

	Stream<? extends GameOperation> operations();

	GameComponentRef<GameStage> getResult();

}
