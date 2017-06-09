package com.tcoffman.ttwb.model.pattern.place;

import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;

public interface GameAnyPlacePattern extends GamePlacePattern {

	GameQuantityPattern getQuantityPattern();

	GamePartPattern getPartPattern();

}
