package com.tcoffman.ttwb.model.pattern.role;

import java.util.function.Predicate;

import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.state.GameState;

public interface GameRolePattern {

	Predicate<GameRole> allows(GameState state);

	<R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E;

	interface Visitor<R, E extends Throwable> {
		default R visit(GameRolePattern pattern) throws E {
			throw new UnsupportedOperationException();
		}

		R visit(GameAnyRolePattern pattern) throws E;

		R visit(GameRoleAllowDenyPattern pattern) throws E;

		R visit(GameRolePartBindingPattern pattern) throws E;

		R visit(GameVariableRolePattern pattern) throws E;
	}

}
