package com.tcoffman.ttwb.model.pattern.place;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePlace;

public class StandardInversionPlacePattern extends StandardPlacePattern implements GameInversionPlacePattern {

	private GamePlacePattern m_pattern;

	private static final Predicate<GamePlace> ALWAYS_MATCH = (p) -> true;
	private static final Predicate<GamePart> ALWAYS_MATCH_PARTS = (p) -> true;

	@Override
	public Predicate<GamePlace> matches() {
		return m_pattern.matches().negate();
	}

	@Override
	public Predicate<GamePart> matchesParts() {
		return m_pattern.matchesParts().negate();
	}

	@Override
	public <T> Stream<T> limitQuantity(Stream<T> items) throws IllegalArgumentException {
		return items;
	}

	private StandardInversionPlacePattern() {
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	@Override
	public GamePlacePattern getPattern() {
		return m_pattern;
	}

	public static Editor create() {
		return new StandardInversionPlacePattern().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardPlacePattern.Editor<StandardInversionPlacePattern> {

		private Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
			requirePresent(CORE, "pattern", m_pattern);
		}

		public Editor setPattern(GamePlacePattern pattern) {
			requireNotDone();
			m_pattern = pattern;
			return this;
		}

	}

	@Override
	public GamePlacePattern inverted() throws GameComponentBuilderException {
		return m_pattern;
	}

	@Override
	public String toString() {
		final PlacePatternFormatter formatter = PlacePatternFormatter.create();
		formatter.matchNegatedPattern(m_pattern);
		return formatter.toString();
	}

}
