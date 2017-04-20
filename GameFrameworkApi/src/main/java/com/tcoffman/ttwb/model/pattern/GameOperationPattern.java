package com.tcoffman.ttwb.model.pattern;

import java.util.Optional;

import com.tcoffman.ttwb.state.GameOperation;

public interface GameOperationPattern {

	GameOperation.Type getType();

	GameRolePattern getRolePattern();

	Optional<GamePartPattern> getSubjectPattern();

	Optional<GamePlacePattern> getSubjectPlacePattern();

	Optional<GamePartPattern> getTargetPattern();

	Optional<GamePlacePattern> getTargetPlacePattern();

	Optional<GameQuantityPattern> getQuantityPattern();

}
