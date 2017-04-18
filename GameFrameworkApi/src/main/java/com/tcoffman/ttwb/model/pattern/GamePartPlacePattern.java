package com.tcoffman.ttwb.model.pattern;

import java.util.function.Predicate;

import com.tcoffman.ttwb.state.GamePartPlace;

public interface GamePartPlacePattern {

	Predicate<GamePartPlace> allows();

}
