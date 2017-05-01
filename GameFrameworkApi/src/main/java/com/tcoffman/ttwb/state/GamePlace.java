package com.tcoffman.ttwb.state;

import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePlacePrototype;

public interface GamePlace {

	GamePart getOwner();

	GameComponentRef<GamePlacePrototype> getPrototype();

	Stream<? extends GamePartRelationship> outgoingRelationships();

	Stream<? extends GamePartRelationship> incomingRelationships();
}
