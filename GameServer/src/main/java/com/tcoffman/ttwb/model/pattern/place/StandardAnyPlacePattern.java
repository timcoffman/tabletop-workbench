package com.tcoffman.ttwb.model.pattern.place;

import java.util.function.Predicate;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.StandardAnyQuantityPattern;
import com.tcoffman.ttwb.state.GamePlace;

public class StandardAnyPlacePattern extends StandardNarrowingPlacePattern implements GameAnyPlacePattern {

	@Override
	public Predicate<GamePlace> matches() {
		return (p) -> true;
	}

	private StandardAnyPlacePattern() {
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	public static StandardAnyPlacePattern anyQuantityForPart(GamePartPattern pattern) throws GameComponentBuilderException {
		final GameQuantityPattern anyQuantity = StandardAnyQuantityPattern.create().done();
		return create().setQuantityPattern(anyQuantity).setPartPattern(pattern).done();
	}

	public static StandardAnyPlacePattern anyQuantity() throws GameComponentBuilderException {
		final GameQuantityPattern anyQuantity = StandardAnyQuantityPattern.create().done();
		return create().setQuantityPattern(anyQuantity).done();
	}

	public static StandardAnyPlacePattern forPart(GamePartPattern pattern) throws GameComponentBuilderException {
		return create().setPartPattern(pattern).done();
	}

	public static Editor create() {
		return new StandardAnyPlacePattern().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardNarrowingPlacePattern.Editor<StandardAnyPlacePattern> {

		private Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
		}

		@Override
		protected StandardAnyPlacePattern model() {
			return StandardAnyPlacePattern.this;
		}

		public Editor setQuantityPattern(GameQuantityPattern quantityPattern) {
			setQuantityPatternInternal(quantityPattern);
			return this;
		}

		public Editor setPartPattern(GamePartPattern partPattern) {
			setPartPatternInternal(partPattern);
			return this;
		}

	}

	@Override
	public String toString() {
		return PlacePatternFormatter.create().matchesQuantity(getQuantityPattern()).matchesPart(getPartPattern()).toString();
	}

}
