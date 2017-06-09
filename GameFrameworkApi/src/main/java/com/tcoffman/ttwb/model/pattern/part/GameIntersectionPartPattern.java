package com.tcoffman.ttwb.model.pattern.part;

import java.util.stream.Stream;

public interface GameIntersectionPartPattern extends GamePartPattern {

	Stream<? extends GamePartPattern> patterns();

	int countPatterns();

}
