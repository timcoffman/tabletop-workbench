package com.tcoffman.ttwb.model;

import java.util.stream.Stream;

public interface GamePlace {

	GamePartPrototype getOwningPrototype();

	GameComponentRef<GamePlaceType> getType();

	Stream<? extends GameModelProperty> properties();

	Stream<? extends GameModelComponent> components();

}
