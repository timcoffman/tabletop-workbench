package com.tcoffman.ttwb.component;

import com.tcoffman.ttwb.plugin.PluginName;

public interface GamePluginComponent extends GameComponent {

	PluginName getDeclaringPlugin();

	String getLocalName();

}
