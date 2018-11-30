package com.tcoffman.ttwb.model.pattern.place;

import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePlace;

public interface GamePlacePattern {

	@Deprecated
	Predicate<GamePlace> matches();

	@Deprecated
	Predicate<GamePart> matchesParts();

	@Deprecated
	<T> Stream<T> limitQuantity(Stream<T> items) throws IllegalArgumentException;

	interface Visitor<R, E extends Throwable> {
		R visit(GameAnyPlacePattern pattern) throws E;

		R visit(GameFilterPlacePattern pattern) throws E;

		R visit(GameIntersectionPlacePattern pattern) throws E;

		R visit(GameRelationshipPlacePattern pattern) throws E;

		R visit(GameInversionPlacePattern pattern) throws E;
	}

	<R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E;

}
