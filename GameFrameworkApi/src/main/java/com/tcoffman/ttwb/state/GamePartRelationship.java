package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GamePartRelationshipPrototype;

public interface GamePartRelationship {

	GamePartRelationshipPrototype getPrototype();

	GamePartPlace getOrigin();

	GamePartPlace getTerminus();

}
