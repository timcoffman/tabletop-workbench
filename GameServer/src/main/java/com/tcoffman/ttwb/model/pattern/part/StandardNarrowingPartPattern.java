package com.tcoffman.ttwb.model.pattern.part;

import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.StandardSingleQuantityPattern;

public abstract class StandardNarrowingPartPattern extends StandardPartPattern {

	private GameQuantityPattern m_quantityPattern;

	public StandardNarrowingPartPattern() {
		super();
	}

	public GameQuantityPattern getQuantityPattern() {
		return m_quantityPattern;
	}

	@Override
	public <T> Stream<T> limitQuantity(Stream<T> items) throws IllegalArgumentException {
		return getQuantityPattern().limit(items);
	}

	public abstract class Editor<T extends StandardNarrowingPartPattern> extends StandardPartPattern.Editor<T> {

		protected Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			if (null == m_quantityPattern)
				m_quantityPattern = StandardSingleQuantityPattern.create().done();
		}

		protected void setQuantityPatternInternal(GameQuantityPattern quantityPattern) {
			requireNotDone();
			m_quantityPattern = quantityPattern;
		}

	}

}