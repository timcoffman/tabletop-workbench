package com.tcoffman.ttwb.model.pattern;

import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.state.GameState;

public interface GameRoleAllowDenyPattern extends GameRolePattern {

	@Override
	default Predicate<GameRole> allows(GameState state) {
		return (r) -> allowedRoles().anyMatch(r::equals) && !deniedRoles().anyMatch(r::equals);
	}

	Stream<? extends GameRole> allowedRoles();

	Stream<? extends GameRole> deniedRoles();
}
