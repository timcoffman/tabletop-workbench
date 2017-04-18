package com.tcoffman.ttwb.model;

import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.plugin.PluginName;

public interface GameModel extends GameStageContainer {

	String getName();

	Collection<PluginName> getRequiredPlugins();

	Stream<? extends GamePartPrototype> parts();

	Stream<? extends GameRole> roles();

	GameStageRef getInitialStage();

}
