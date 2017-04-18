package com.tcoffman.ttwb.model;

import java.util.Map;

import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.state.GamePartPlace;

public interface GamePartPrototype {

	PluginName getDeclaringPlugin();

	Map<String, GamePartPlace> getPlaces();

}
