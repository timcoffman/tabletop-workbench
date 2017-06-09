package com.tcoffman.ttwb.state;

import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;

public interface StateView {

	GameComponentRef<GameStage> getCurrentStage();

	Stream<? extends GamePart> parts();

	Stream<? extends GamePartRelationship> relationships();

	Stream<? extends GamePart> find(GamePartPattern pattern);

	Stream<? extends GamePlace> find(GamePlacePattern pattern);

	QueryExecutor queryExecutor();

}
