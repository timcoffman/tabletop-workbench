package com.tcoffman.ttwb.state;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;

public interface QueryExecutor {

	Stream<? extends GamePart> find(GamePartPattern pattern);

	@Deprecated
	Stream<? extends GamePart> find(Predicate<GamePart> matcher);

	Stream<? extends GamePlace> find(GamePlacePattern pattern);

	GamePart findOne(GamePartPattern pattern);

	GamePlace findOne(GamePlacePattern pattern);

	Optional<? extends GamePart> findOneOrZero(GamePartPattern pattern);

	Optional<? extends GamePlace> findOneOrZero(GamePlacePattern pattern);
}