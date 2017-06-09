package com.tcoffman.ttwb.model.pattern.role;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.function.Predicate;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.pattern.StandardPatternContext;
import com.tcoffman.ttwb.state.GameState;

public class StandardGameVariableRolePattern extends StandardGameRolePattern implements GameVariableRolePattern {

	String m_token;

	@Override
	public Predicate<GameRole> allows(GameState state) {
		return StandardPatternContext.current().requireRoleMatcher(m_token);
	}

	@Override
	public String getToken() {
		return m_token;
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	public static StandardGameVariableRolePattern forToken(String token) throws GameComponentBuilderException {
		return create().setToken(token).done();
	}

	public static final Editor create() {
		return new StandardGameVariableRolePattern().edit();
	}

	private StandardGameVariableRolePattern() {
	}

	private Editor edit() {
		return new Editor();
	}

	public class Editor extends AbstractEditor<StandardGameVariableRolePattern> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			requirePresent(CORE, "token", m_token);
		}

		@Override
		protected StandardGameVariableRolePattern model() {
			return StandardGameVariableRolePattern.this;
		}

		public Editor setToken(String token) {
			requireNotDone();
			m_token = token;
			return this;
		}

	}

	@Override
	public String toString() {
		return "ROLE WHERE :" + m_token + " = ?";
	}

}
