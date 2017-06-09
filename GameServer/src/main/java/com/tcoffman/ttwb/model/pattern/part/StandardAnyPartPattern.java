package com.tcoffman.ttwb.model.pattern.part;

import java.util.function.Predicate;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;
import com.tcoffman.ttwb.state.GamePart;

public class StandardAnyPartPattern extends StandardNarrowingPartPattern implements GameAnyPartPattern {

	@Override
	public Predicate<GamePart> matches() {
		return (p) -> true;
	}

	private StandardAnyPartPattern() {
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	public static Editor create() {
		return INSTANCE.edit();
	}

	private static final StandardAnyPartPattern INSTANCE = new StandardAnyPartPattern();

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardNarrowingPartPattern.Editor<StandardAnyPartPattern> {

		private Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
		}

		public Editor setQuantityPattern(GameQuantityPattern quantityPattern) {
			setQuantityPatternInternal(quantityPattern);
			return this;
		}

	}

	@Override
	public String toString() {
		return PartPatternFormatter.create().matchesQuantity(getQuantityPattern()).toString();
	}

}
