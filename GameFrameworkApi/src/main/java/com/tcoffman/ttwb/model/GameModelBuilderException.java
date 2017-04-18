package com.tcoffman.ttwb.model;

import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;

public class GameModelBuilderException extends PluginException {

	private static final long serialVersionUID = 1L;

	public GameModelBuilderException(PluginName pluginName) {
		super(pluginName);
	}

	public GameModelBuilderException(PluginName pluginName, String message, Throwable cause) {
		super(pluginName, message, cause);
	}

	public GameModelBuilderException(PluginName pluginName, String message) {
		super(pluginName, message);
	}

	public GameModelBuilderException(PluginName pluginName, Throwable cause) {
		super(pluginName, cause);
	}

}
