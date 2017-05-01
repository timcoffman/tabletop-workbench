package com.tcoffman.ttwb.state;

import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.pattern.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.GamePlacePattern;

public interface StateView {

	GameComponentRef<GameStage> getCurrentStage();

	Stream<? extends GamePart> parts();

	Stream<? extends GamePartRelationship> relationships();

	Stream<? extends GamePart> find(GamePartPattern pattern);

	Stream<? extends GamePlace> find(GamePlacePattern pattern);

	Optional<? extends GamePlace> findOneOrZero(GamePlacePattern pattern);

	Optional<? extends GamePart> findOneOrZero(GamePartPattern pattern);

	GamePlace findOne(GamePlacePattern pattern);

	GamePart findOne(GamePartPattern pattern);

}
