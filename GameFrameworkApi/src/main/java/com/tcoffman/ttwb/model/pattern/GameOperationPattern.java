package com.tcoffman.ttwb.model.pattern;

import java.util.Optional;

import com.tcoffman.ttwb.state.GameOperation;

public interface GameOperationPattern {

	GameOperation.Type getType();

	GameRolePattern getRolePattern();

	Optional<GamePartPattern> getSubjectPattern();

	Optional<GamePartPlacePattern> getSubjectPlacePattern();

	Optional<GamePartPattern> getTargetPattern();

	Optional<GamePartPlacePattern> getTargetPlacePattern();

	Optional<GamePartPlacePattern> getQuantityPattern();

}
