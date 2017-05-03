package com.tcoffman.ttwb.model;

import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.GameDocumentableComponent;

public interface GamePlacePrototype extends GameDocumentableComponent {

	GamePartPrototype getOwner();

	GameComponentRef<GamePlaceType> getType();

	Optional<GameComponentRef<GameRole>> getRoleBinding();

	Stream<? extends GameModelProperty> properties();

	Stream<? extends GameModelComponent> components();

}
