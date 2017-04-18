package com.tcoffman.ttwb.model.pattern;

import java.util.function.Predicate;

import com.tcoffman.ttwb.model.GamePart;

public interface GamePartPattern {

	Predicate<GamePart> allows();

}
