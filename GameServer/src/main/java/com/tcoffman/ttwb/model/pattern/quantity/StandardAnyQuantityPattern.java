package com.tcoffman.ttwb.model.pattern.quantity;

import java.util.stream.Stream;

public class StandardAnyQuantityPattern extends StandardQuantityPattern implements GameAnyQuantityPattern {

	@Override
	public <T> Stream<T> limit(Stream<T> items) throws IllegalArgumentException {
		return items;
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	public static final Editor create() {
		return INSTANCE.edit();
	}

	private StandardAnyQuantityPattern() {

	}

	private static final StandardAnyQuantityPattern INSTANCE = new StandardAnyQuantityPattern();

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardQuantityPattern.Editor<StandardAnyQuantityPattern> {

	}

	@Override
	public String toString() {
		return QuantityPatternFormatter.create().toString();
	}

}
