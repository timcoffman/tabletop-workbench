package com.tcoffman.ttwb.state.pattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.pattern.GamePartPattern;
import com.tcoffman.ttwb.state.GamePart;

public class StandardIntersectionPartPattern implements GamePartPattern {

	private static final Predicate<GamePart> ALWAYS_MATCH = (p) -> true;

	private final Collection<GamePartPattern> m_patterns = new ArrayList<GamePartPattern>();

	@Override
	public Predicate<GamePart> matches() {
		return m_patterns.stream().map(GamePartPattern::matches).reduce((a, b) -> a.and(b)).orElse(ALWAYS_MATCH);
	}

	private StandardIntersectionPartPattern() {
	}

	public static Editor create() {
		return new StandardIntersectionPartPattern().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends AbstractEditor<StandardIntersectionPartPattern> {

		private Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
		}

		@Override
		protected StandardIntersectionPartPattern model() {
			return StandardIntersectionPartPattern.this;
		}

		public Editor addPattern(GamePartPattern pattern) {
			requireNotDone();
			m_patterns.add(pattern);
			return this;
		}

	}

	@Override
	public String toString() {
		final PartPatternFormatter formatter = PartPatternFormatter.create();
		m_patterns.forEach((p) -> formatter.matchPattern(p));
		return formatter.toString();
	}

}
