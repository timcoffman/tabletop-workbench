package com.tcoffman.ttwb.model;

import com.tcoffman.ttwb.plugin.PluginName;

public interface GameModelProperty {

	PluginName getDeclaringPlugin();

	String getLocalName();

	String getValue();
}
