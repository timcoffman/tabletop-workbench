package com.tcoffman.ttwb.model.pattern.part;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.state.GamePart;

public class StandardIntersectionPartPattern extends StandardPartPattern implements GameIntersectionPartPattern {

	private static final Predicate<GamePart> ALWAYS_MATCH = (p) -> true;

	private final Collection<GamePartPattern> m_patterns = new ArrayList<GamePartPattern>();

	@Override
	public Predicate<GamePart> matches() {
		return m_patterns.stream().map(GamePartPattern::matches).reduce((a, b) -> a.and(b)).orElse(ALWAYS_MATCH);
	}

	@Override
	public <T> Stream<T> limitQuantity(Stream<T> items) throws IllegalArgumentException {
		for (final GamePartPattern pattern : m_patterns)
			items = pattern.limitQuantity(items);
		return items;
	}

	private StandardIntersectionPartPattern() {
	}

	@Override
	public Stream<? extends GamePartPattern> patterns() {
		return m_patterns.stream();
	}

	@Override
	public int countPatterns() {
		return m_patterns.size();
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	public static Editor create() {
		return new StandardIntersectionPartPattern().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardPartPattern.Editor<StandardIntersectionPartPattern> {

		private Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
		}

		public Editor addPattern(GamePartPattern pattern) {
			requireNotDone();
			m_patterns.add(pattern);
			return this;
		}

		public Editor addPatterns(Collection<? extends GamePartPattern> patterns) {
			requireNotDone();
			m_patterns.addAll(patterns);
			return this;
		}

	}

	@Override
	public StandardPartPattern and(GamePartPattern pattern) throws GameComponentBuilderException {
		return StandardIntersectionPartPattern.create().addPatterns(m_patterns).addPattern(pattern).done();
	}

	@Override
	public String toString() {
		final PartPatternFormatter formatter = PartPatternFormatter.create();
		m_patterns.forEach((p) -> formatter.matchPattern(p));
		return formatter.toString();
	}

}
