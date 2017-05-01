package com.tcoffman.ttwb.state.pattern;

import java.util.function.Predicate;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.pattern.GamePartPattern;
import com.tcoffman.ttwb.state.GamePlace;

public class StandardAnyPlacePattern extends StandardPlacePattern {

	@Override
	public Predicate<GamePlace> matches() {
		return (p) -> true;
	}

	private StandardAnyPlacePattern() {
	}

	public static Editor create() {
		return new StandardAnyPlacePattern().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardPlacePattern.Editor<StandardAnyPlacePattern> {

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

		public Editor setPartPattern(GamePartPattern partPattern) {
			setPartPatternInternal(partPattern);
			return this;
		}

	}

}
