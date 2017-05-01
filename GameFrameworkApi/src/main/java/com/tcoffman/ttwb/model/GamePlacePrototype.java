package com.tcoffman.ttwb.model;

import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;

public interface GamePlacePrototype {

	GamePartPrototype getOwner();

	GameComponentRef<GamePlaceType> getType();

	Optional<GameComponentRef<GameRole>> getRoleBinding();

	Stream<? extends GameModelProperty> properties();

	Stream<? extends GameModelComponent> components();

}
