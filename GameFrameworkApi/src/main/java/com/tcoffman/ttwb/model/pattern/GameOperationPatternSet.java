package com.tcoffman.ttwb.model.pattern;

import java.util.stream.Stream;

import com.tcoffman.ttwb.model.GameStageRef;

public interface GameOperationPatternSet {

	Stream<? extends GameOperationPattern> operations();

	GameStageRef getResult();

}
