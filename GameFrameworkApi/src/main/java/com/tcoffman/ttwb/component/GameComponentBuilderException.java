package com.tcoffman.ttwb.component;

import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;

public class GameComponentBuilderException extends PluginException {

	private static final long serialVersionUID = 1L;

	public GameComponentBuilderException(PluginName pluginName) {
		super(pluginName);
	}

	public GameComponentBuilderException(PluginName pluginName, String message, Throwable cause) {
		super(pluginName, message, cause);
	}

	public GameComponentBuilderException(PluginName pluginName, String message) {
		super(pluginName, message);
	}

	public GameComponentBuilderException(PluginName pluginName, Throwable cause) {
		super(pluginName, cause);
	}

}
