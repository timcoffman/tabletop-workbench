package com.tcoffman.ttwb.model.pattern.part;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.pattern.StandardPatternContext;
import com.tcoffman.ttwb.state.GamePart;

public class StandardVariablePartPattern extends StandardPartPattern implements GameVariablePartPattern {

	private String m_token;

	@Override
	public Predicate<GamePart> matches() {
		return StandardPatternContext.current().requirePartMatcher(m_token);
	}

	@Override
	public <T> Stream<T> limitQuantity(Stream<T> items) throws IllegalArgumentException {
		return items.limit(1);
	}

	@Override
	public String getToken() {
		return m_token;
	}

	private StandardVariablePartPattern() {
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	public static StandardVariablePartPattern forToken(String token) throws GameComponentBuilderException {
		return create().setToken(token).done();
	}

	public static Editor create() {
		return INSTANCE.edit();
	}

	private static final StandardVariablePartPattern INSTANCE = new StandardVariablePartPattern();

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardPartPattern.Editor<StandardVariablePartPattern> {

		private Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
			requirePresent(CORE, "token", m_token);
		}

		public Editor setToken(String token) {
			requireNotDone();
			m_token = token;
			return this;
		}

	}

	@Override
	public String toString() {
		final PartPatternFormatter formatter = PartPatternFormatter.create();
		formatter.equalProperty(":" + m_token, "?");
		return formatter.toString();
	}
}
