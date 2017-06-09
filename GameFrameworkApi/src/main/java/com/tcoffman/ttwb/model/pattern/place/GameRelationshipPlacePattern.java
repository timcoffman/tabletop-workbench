package com.tcoffman.ttwb.model.pattern.place;

import java.util.Optional;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;

public interface GameRelationshipPlacePattern extends GamePlacePattern {

	GameQuantityPattern getQuantityPattern();

	GamePartPattern getPartPattern();

	boolean getTraverseForward();

	boolean getMatchExistence();

	Optional<GameComponentRef<GamePartRelationshipType>> getMatchType();

	Optional<GamePlacePattern> getRelatedPlacePattern();
}
