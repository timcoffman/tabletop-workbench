package com.tcoffman.ttwb.model;

import com.tcoffman.ttwb.state.GamePartPlace;

public interface GamePartRelationshipPrototype {

	GamePartPlace getSource();

	GamePartPlace getDestination();

	GamePartRelationshipType getType();

}
