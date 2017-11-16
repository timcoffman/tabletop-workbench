package com.tcoffman.ttwb.model.pattern.operation;

import java.util.Optional;

import com.tcoffman.ttwb.component.GameDocumentableComponent;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.model.pattern.role.GameRolePattern;
import com.tcoffman.ttwb.state.mutation.GameOperation;

public interface GameOperationPattern extends GameDocumentableComponent {

	GameOperation.Type getType();

	GameRolePattern getRolePattern();

	Optional<GamePartPattern> getSubjectPattern();

	Optional<GamePlacePattern> getSubjectPlacePattern();

	Optional<GamePartPattern> getTargetPattern();

	Optional<GamePlacePattern> getTargetPlacePattern();

	// Optional<GameQuantityPattern> getQuantityPattern();

}
