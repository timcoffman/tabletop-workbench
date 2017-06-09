package com.tcoffman.ttwb.model.pattern.part;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.state.GamePart;

public class StandardInversionPartPattern extends StandardPartPattern implements GameInversionPartPattern {

	private GamePartPattern m_pattern;

	@Override
	public Predicate<GamePart> matches() {
		return m_pattern.matches().negate();
	}

	@Override
	public <T> Stream<T> limitQuantity(Stream<T> items) throws IllegalArgumentException {
		return items;
	}

	private StandardInversionPartPattern() {
	}

	@Override
	public GamePartPattern getPattern() {
		return m_pattern;
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	public static Editor create() {
		return new StandardInversionPartPattern().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardPartPattern.Editor<StandardInversionPartPattern> {

		private Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			requirePresent(CORE, "pattern", m_pattern);
		}

		public Editor setPattern(GamePartPattern pattern) {
			requireNotDone();
			m_pattern = pattern;
			return this;
		}

	}

	@Override
	public GamePartPattern inverted() throws GameComponentBuilderException {
		return m_pattern;
	}

	@Override
	public String toString() {
		final PartPatternFormatter formatter = PartPatternFormatter.create();
		formatter.matchNegatedPattern(m_pattern);
		return formatter.toString();
	}

}
