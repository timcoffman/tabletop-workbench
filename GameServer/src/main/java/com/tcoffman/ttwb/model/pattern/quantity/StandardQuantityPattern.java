package com.tcoffman.ttwb.model.pattern.quantity;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;

public abstract class StandardQuantityPattern implements GameQuantityPattern {

	public abstract class Editor<T extends StandardQuantityPattern> extends AbstractEditor<T> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			/* nothing to validate */
		}

		@Override
		protected T model() {
			@SuppressWarnings("unchecked")
			final T self = (T) StandardQuantityPattern.this;
			return self;
		}

	}

	protected <T> Stream<T> requireStreamSize(Stream<T> items, Predicate<Long> sizeRequirement) throws IllegalArgumentException {
		final List<T> itemList = items.collect(Collectors.toList());
		final long count = itemList.size();
		if (!sizeRequirement.test(count))
			throw new IllegalArgumentException("encountered (" + count + ") items; failed to match " + this);
		return itemList.stream();
	}

}
