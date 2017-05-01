package com.tcoffman.ttwb.model;

import com.tcoffman.ttwb.plugin.PluginName;

public interface GamePartRelationshipType {

	PluginName getDeclaringPlugin();

	String getLocalName();

}
