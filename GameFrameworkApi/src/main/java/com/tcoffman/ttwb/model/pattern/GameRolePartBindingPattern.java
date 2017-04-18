package com.tcoffman.ttwb.model.pattern;

import java.util.function.Predicate;

import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.state.GameState;

public interface GameRolePartBindingPattern extends GameRolePattern {

	@Override
	default Predicate<GameRole> allows(GameState state) {
		return (r) -> state.parts().filter(getPartPattern().allows()).anyMatch((p) -> true);
	}

	GamePartPattern getPartPattern();

}
