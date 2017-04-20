package com.tcoffman.ttwb.model;


public interface GamePlace {

	GamePartPrototype getPart();

	GameComponentRef<GamePlaceType> getType();

}
