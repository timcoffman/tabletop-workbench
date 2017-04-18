package com.tcoffman.ttwb.model.pattern;

import java.util.function.Predicate;

import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.state.GameState;

public interface GameAnyRolePattern extends GameRolePattern {

	@Override
	default Predicate<GameRole> allows(GameState state) {
		return (r) -> true;
	}
}
