package com.tcoffman.ttwb.model.pattern;

import java.util.function.Predicate;

public interface GameQuantityPattern {

	Predicate<Integer> allows();

}
