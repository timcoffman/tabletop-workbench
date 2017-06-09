package com.tcoffman.ttwb.model.pattern.quantity;

import java.util.stream.Stream;

public interface GameQuantityPattern {

	<T> Stream<T> limit(Stream<T> items) throws IllegalArgumentException;

	interface Visitor<R, E extends Throwable> {
		R visit(GameAnyQuantityPattern pattern);

		R visit(GameRangeQuantityPattern pattern);

		R visit(GameSingleQuantityPattern pattern);
	}

	<R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E;
}
