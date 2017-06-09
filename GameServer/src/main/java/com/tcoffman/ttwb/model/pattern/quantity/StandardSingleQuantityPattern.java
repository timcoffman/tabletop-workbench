package com.tcoffman.ttwb.model.pattern.quantity;

import java.util.stream.Stream;

public class StandardSingleQuantityPattern extends StandardQuantityPattern implements GameSingleQuantityPattern {

	@Override
	public <T> Stream<T> limit(Stream<T> items) throws IllegalArgumentException {
		return requireStreamSize(items.limit(1), (n) -> n == 1);
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	public static final Editor create() {
		return INSTANCE.edit();
	}

	private StandardSingleQuantityPattern() {

	}

	private static final StandardSingleQuantityPattern INSTANCE = new StandardSingleQuantityPattern();

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardQuantityPattern.Editor<StandardSingleQuantityPattern> {
		private Editor() {

		}
	}

	@Override
	public String toString() {
		final QuantityPatternFormatter formatter = QuantityPatternFormatter.create();
		formatter.matchesRange(1L, 1L);
		return formatter.toString();
	}

}
