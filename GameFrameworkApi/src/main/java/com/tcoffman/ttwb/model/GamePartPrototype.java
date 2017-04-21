package com.tcoffman.ttwb.model;

import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.plugin.PluginName;

public interface GamePartPrototype {

	PluginName getDeclaringPlugin();

	Stream<? extends GamePlace> places();

	Optional<GameComponentRef<GamePartPrototype>> getExtends();

	Optional<GameComponentRef<GameRole>> getRoleBinding();

}
