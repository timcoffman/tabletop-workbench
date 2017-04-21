package com.tcoffman.ttwb.model;

import java.util.stream.Stream;

import com.tcoffman.ttwb.plugin.PluginName;

public interface GameModelComponent {

	PluginName getDeclaringPlugin();

	String getLocalName();

	Stream<? extends GameModelProperty> properties();

	Stream<? extends GameModelComponent> components();

}
