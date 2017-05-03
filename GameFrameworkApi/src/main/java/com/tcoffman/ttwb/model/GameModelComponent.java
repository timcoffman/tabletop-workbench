package com.tcoffman.ttwb.model;

import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GamePluginComponent;

public interface GameModelComponent extends GamePluginComponent {

	Stream<? extends GameModelProperty> properties();

	Stream<? extends GameModelComponent> components();

}
