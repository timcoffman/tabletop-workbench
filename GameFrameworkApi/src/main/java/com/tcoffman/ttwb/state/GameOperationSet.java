package com.tcoffman.ttwb.state;

import java.util.stream.Stream;

import com.tcoffman.ttwb.model.GameStageRef;

public interface GameOperationSet {

	Stream<? extends GameOperation> operations();

	GameStageRef getResult();

}
