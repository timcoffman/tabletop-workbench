package com.tcoffman.ttwb.model.pattern.place;

import java.util.stream.Stream;

public interface GameIntersectionPlacePattern extends GamePlacePattern {

	Stream<? extends GamePlacePattern> patterns();

	int countPatterns();

}
