package com.tcoffman.ttwb.model.pattern.quantity;

import java.util.Optional;

public interface GameRangeQuantityPattern extends GameQuantityPattern {

	Optional<Long> getMinimum();

	Optional<Long> getMaximum();

}
