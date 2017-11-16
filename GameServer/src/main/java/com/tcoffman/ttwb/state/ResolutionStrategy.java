package com.tcoffman.ttwb.state;

import java.util.Optional;

import com.tcoffman.ttwb.model.pattern.operation.GameOperationPatternSet;
import com.tcoffman.ttwb.state.mutation.ResolvedOperationSet;

public interface ResolutionStrategy {

	Optional<ResolvedOperationSet> autoResolve(GameOperationPatternSet operationPatternSet);

}