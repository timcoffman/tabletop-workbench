package com.tcoffman.ttwb.model.pattern.quantity;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;

public class StandardRangeQuantityPattern extends StandardQuantityPattern implements GameRangeQuantityPattern {

	private Optional<Long> m_minimum;
	private Optional<Long> m_maximum;

	@Override
	public <T> Stream<T> limit(Stream<T> items) throws IllegalArgumentException {
		if (m_maximum.isPresent())
			items = items.limit(m_maximum.get());
		return requireStreamSize(items, (n) -> m_minimum.map((min) -> n >= min).orElse(true) && m_maximum.map((max) -> n <= max).orElse(true));
	}

	@Override
	public Optional<Long> getMinimum() {
		return m_minimum;
	}

	@Override
	public Optional<Long> getMaximum() {
		return m_maximum;
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	public static final Editor create() {
		return new StandardRangeQuantityPattern().edit();
	}

	private StandardRangeQuantityPattern() {

	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardQuantityPattern.Editor<StandardRangeQuantityPattern> {

		private Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
			if (!m_minimum.isPresent() && !m_maximum.isPresent())
				throw new GameComponentBuilderException(CORE, "quantity range missing both minimum and maximum");
		}

		public Editor setMaximum(long max) {
			requireNotDone();
			m_maximum = Optional.of(max);
			return this;
		}

		public Editor setMinimum(long min) {
			requireNotDone();
			m_minimum = Optional.of(min);
			return this;
		}
	}

	@Override
	public String toString() {
		final QuantityPatternFormatter formatter = QuantityPatternFormatter.create();
		formatter.matchesRange(m_minimum.orElse(null), m_maximum.orElse(null));
		return formatter.toString();
	}

}
