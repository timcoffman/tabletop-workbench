package com.tcoffman.ttwb.model.pattern;

import java.util.function.Predicate;

import com.tcoffman.ttwb.state.GamePlaceInstance;

public interface GamePlacePattern {

	Predicate<GamePlaceInstance> allows();

}
