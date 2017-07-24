package com.tcoffman.ttwb.state;

import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponent;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;

public interface GamePart extends GameComponent {

	GameComponentRef<GamePartPrototype> getPrototype();

	Optional<GameComponentRef<GameRole>> getRoleBinding();

	Stream<? extends GamePlace> places();

	GamePlace findPlace(GameComponentRef<GamePlaceType> type);

	Optional<GameComponentRef<GameRole>> effectiveRoleBinding();
}
