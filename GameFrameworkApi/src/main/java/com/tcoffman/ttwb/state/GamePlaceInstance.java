package com.tcoffman.ttwb.state;

import java.util.stream.Stream;

import com.tcoffman.ttwb.model.GamePartPlaceType;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePartRelationshipPrototype;

public interface GamePartPlace {

	GamePartPrototype getPart();

	GamePartPlaceType getType();

	Stream<? extends GamePartRelationshipPrototype> outgoingRelationships();

	Stream<? extends GamePartRelationshipPrototype> incomingRelationships();
}
