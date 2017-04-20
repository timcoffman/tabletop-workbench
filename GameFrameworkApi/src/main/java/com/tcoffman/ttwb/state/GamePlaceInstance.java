package com.tcoffman.ttwb.state;

import java.util.stream.Stream;

import com.tcoffman.ttwb.model.GamePartInstance;
import com.tcoffman.ttwb.model.GamePlace;
import com.tcoffman.ttwb.model.GamePartRelationshipPrototype;

public interface GamePlaceInstance {

	GamePartInstance getPart();

	GamePlace getPlacePrototype();

	Stream<? extends GamePartRelationshipPrototype> outgoingRelationships();

	Stream<? extends GamePartRelationshipPrototype> incomingRelationships();
}
