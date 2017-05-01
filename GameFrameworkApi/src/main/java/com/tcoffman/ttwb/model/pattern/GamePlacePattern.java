package com.tcoffman.ttwb.model.pattern;

import java.util.function.Predicate;

import com.tcoffman.ttwb.state.GamePlace;

public interface GamePlacePattern {

	GamePartPattern getPartPattern();

	Predicate<GamePlace> matches();

}
