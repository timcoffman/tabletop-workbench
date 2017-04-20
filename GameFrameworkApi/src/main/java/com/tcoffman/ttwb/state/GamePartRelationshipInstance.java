package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GamePartRelationshipPrototype;

public interface GamePartRelationshipInstance {

	GamePartRelationshipPrototype getPrototype();

	GamePlaceInstance getOrigin();

	GamePlaceInstance getTerminus();

}
