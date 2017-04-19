package com.tcoffman.ttwb.model.pattern;

import java.util.stream.Stream;

import com.tcoffman.ttwb.model.GameComponentRef;
import com.tcoffman.ttwb.model.GameStage;

public interface GameOperationPatternSet {

	Stream<? extends GameOperationPattern> operations();

	GameComponentRef<GameStage> getResult();

}
