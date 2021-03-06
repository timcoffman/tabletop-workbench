package com.tcoffman.ttwb.model;

import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPattern;

public interface GameRule {

	Stream<? extends GameOperationPattern> operationPatterns();

	GameComponentRef<GameStage> getResult();

}
