package com.tcoffman.ttwb.model;

import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.GameDocumentableComponent;
import com.tcoffman.ttwb.plugin.PluginName;

public interface GamePartPrototype extends GameDocumentableComponent {

	PluginName getDeclaringPlugin();

	Stream<? extends GamePlacePrototype> effectivePlaces();

	Stream<? extends GamePlacePrototype> places();

	Optional<GameComponentRef<GamePartPrototype>> getExtends();

	Optional<GameComponentRef<GameRole>> effectiveRoleBinding();

	Optional<GameComponentRef<GameRole>> getRoleBinding();

}
