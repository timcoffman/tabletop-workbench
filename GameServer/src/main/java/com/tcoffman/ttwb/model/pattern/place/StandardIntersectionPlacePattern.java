package com.tcoffman.ttwb.model.pattern.place;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePlace;

public class StandardIntersectionPlacePattern extends StandardPlacePattern implements GameIntersectionPlacePattern {

	private final Collection<GamePlacePattern> m_patterns = new ArrayList<GamePlacePattern>();

	private static final Predicate<GamePlace> ALWAYS_MATCH = (p) -> true;
	private static final Predicate<GamePart> ALWAYS_MATCH_PARTS = (p) -> true;

	@Override
	public Predicate<GamePlace> matches() {
		return m_patterns.stream().map(GamePlacePattern::matches).reduce((a, b) -> a.and(b)).orElse(ALWAYS_MATCH);
	}

	@Override
	public Predicate<GamePart> matchesParts() {
		return m_patterns.stream().map(GamePlacePattern::matchesParts).reduce((a, b) -> a.and(b)).orElse(ALWAYS_MATCH_PARTS);
	}

	@Override
	public <T> Stream<T> limitQuantity(Stream<T> items) throws IllegalArgumentException {
		for (final GamePlacePattern pattern : m_patterns)
			items = pattern.limitQuantity(items);
		return items;
	}

	private StandardIntersectionPlacePattern() {
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	@Override
	public Stream<? extends GamePlacePattern> patterns() {
		return m_patterns.stream();
	}

	@Override
	public int countPatterns() {
		return m_patterns.size();
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
			super.validate();
		}

		public Editor addPattern(GamePlacePattern pattern) {
			requireNotDone();
			m_patterns.add(pattern);
			return this;
		}

		public Editor addPatterns(Collection<? extends GamePlacePattern> patterns) {
			requireNotDone();
			m_patterns.addAll(patterns);
			return this;
		}

	}

	@Override
	public StandardPlacePattern and(GamePlacePattern pattern) throws GameComponentBuilderException {
		return StandardIntersectionPlacePattern.create().addPatterns(m_patterns).addPattern(pattern).done();
	}

	@Override
	public String toString() {
		final PlacePatternFormatter formatter = PlacePatternFormatter.create();
		m_patterns.forEach((p) -> formatter.matchPattern(p));
		return formatter.toString();
	}

}
