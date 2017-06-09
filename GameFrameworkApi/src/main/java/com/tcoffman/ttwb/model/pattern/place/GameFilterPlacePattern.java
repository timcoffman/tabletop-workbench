package com.tcoffman.ttwb.model.pattern.place;

import java.util.Optional;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;

public interface GameFilterPlacePattern extends GamePlacePattern {

	GameQuantityPattern getQuantityPattern();

	GamePartPattern getPartPattern();

	Optional<GameComponentRef<GameRole>> getMatchesRoleBinding();

	Optional<GameComponentRef<GamePlaceType>> getMatchesType();

}
