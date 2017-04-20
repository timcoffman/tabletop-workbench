package com.tcoffman.ttwb.model;

import com.tcoffman.ttwb.state.GamePlaceInstance;

public interface GamePartRelationshipPrototype {

	GamePlaceInstance getSource();

	GamePlaceInstance getDestination();

	GamePartRelationshipType getType();

}
