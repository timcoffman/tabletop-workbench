package com.tcoffman.ttwb.model.pattern.part;

import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.state.GamePart;

public interface GamePartPattern {

	@Deprecated
	Predicate<GamePart> matches();

	@Deprecated
	<T> Stream<T> limitQuantity(Stream<T> items) throws IllegalArgumentException;

	interface Visitor<R, E extends Throwable> {
		R visit(GameAnyPartPattern pattern) throws E;

		R visit(GameVariablePartPattern pattern) throws E;

		R visit(GameFilterPartPattern pattern) throws E;

		R visit(GameIntersectionPartPattern pattern) throws E;

		R visit(GameInversionPartPattern pattern) throws E;

	}

	<R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E;

}
