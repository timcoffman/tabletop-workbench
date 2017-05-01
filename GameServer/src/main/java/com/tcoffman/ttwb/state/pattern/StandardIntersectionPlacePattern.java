package com.tcoffman.ttwb.state.pattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.pattern.GamePlacePattern;
import com.tcoffman.ttwb.state.GamePlace;

public class StandardIntersectionPlacePattern extends StandardPlacePattern {

	private final Collection<GamePlacePattern> m_patterns = new ArrayList<GamePlacePattern>();

	private static final Predicate<GamePlace> ALWAYS_MATCH = (p) -> true;

	@Override
	public Predicate<GamePlace> matches() {
		return m_patterns.stream().map(GamePlacePattern::matches).reduce((a, b) -> a.and(b)).orElse(ALWAYS_MATCH);
	}

	private StandardIntersectionPlacePattern() {
	}

	public static Editor create() {
		return new StandardIntersectionPlacePattern().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardPlacePattern.Editor<StandardIntersectionPlacePattern> {

		private Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			final StandardIntersectionPartPattern.Editor p = StandardIntersectionPartPattern.create();
			m_patterns.stream().map(GamePlacePattern::getPartPattern).forEach(p::addPattern);
			setPartPatternInternal(p.done());
		}

		@Override
		protected StandardIntersectionPlacePattern model() {
			return StandardIntersectionPlacePattern.this;
		}

		public Editor addPattern(GamePlacePattern pattern) {
			requireNotDone();
			m_patterns.add(pattern);
			return this;
		}

	}

}
