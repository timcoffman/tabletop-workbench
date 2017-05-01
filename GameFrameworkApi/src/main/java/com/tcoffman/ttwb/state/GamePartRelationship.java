package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartRelationshipType;

public interface GamePartRelationship {

	GameComponentRef<GamePartRelationshipType> getType();

	GameComponentRef<GamePlace> getSource();

	GameComponentRef<GamePlace> getDestination();
}
