package com.tcoffman.ttwb.model.pattern.place;

import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.part.StandardAnyPartPattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.StandardSingleQuantityPattern;
import com.tcoffman.ttwb.state.GamePart;

public abstract class StandardNarrowingPlacePattern extends StandardPlacePattern {

	private GamePartPattern m_partPattern;
	private GameQuantityPattern m_quantityPattern;

	protected StandardNarrowingPlacePattern() {
		super();
	}

	public GameQuantityPattern getQuantityPattern() {
		return m_quantityPattern;
	}

	public GamePartPattern getPartPattern() {
		return m_partPattern;
	}

	@Override
	public Predicate<GamePart> matchesParts() {
		return getPartPattern().matches();
	}

	@Override
	public <T> Stream<T> limitQuantity(Stream<T> items) throws IllegalArgumentException {
		return getQuantityPattern().limit(items);
	}

	public abstract class Editor<T extends StandardNarrowingPlacePattern> extends StandardPlacePattern.Editor<T> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
			if (null == m_partPattern)
				m_partPattern = StandardAnyPartPattern.create().done();
			if (null == m_quantityPattern)
				m_quantityPattern = StandardSingleQuantityPattern.create().done();
		}

		protected void setPartPatternInternal(GamePartPattern partPattern) {
			requireNotDone();
			m_partPattern = partPattern;
		}

		protected void setQuantityPatternInternal(GameQuantityPattern quantityPattern) {
			requireNotDone();
			m_quantityPattern = quantityPattern;
		}

	}

}