package com.tcoffman.ttwb.model.pattern.part;

import java.util.Optional;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GameRole;

public interface GameFilterPartPattern extends GamePartPattern {

	Optional<GameComponentRef<GamePartPrototype>> getMatchesPrototype();

	Optional<GameComponentRef<GameRole>> getMatchesRoleBinding();

}
