package com.tcoffman.ttwb.model;

import java.util.Optional;

import com.tcoffman.ttwb.component.GameComponentRef;

public interface GamePartInstance {

	GameComponentRef<GamePartPrototype> getPrototype();

	Optional<GameComponentRef<GameRole>> getRoleBinding();

}
