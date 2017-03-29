package com.tcoffman.ttwb.plugin;

public class PluginException extends Exception {

	private static final long serialVersionUID = 1L;

	private final PluginName m_pluginName;

	private static String messageWithPrefix(PluginName pluginName, String message) {
		if (null == pluginName)
			return message;
		else if (null == message)
			return "plugin \"" + pluginName.toString() + "\"";
		else
			return "plugin \"" + pluginName.toString() + "\": " + message;
	}

	public PluginException(PluginName pluginName) {
		m_pluginName = pluginName;
	}

	public PluginException(PluginName pluginName, String message, Throwable cause) {
		super(messageWithPrefix(pluginName, message), cause);
		m_pluginName = pluginName;
	}

	public PluginException(PluginName pluginName, String message) {
		super(messageWithPrefix(pluginName, message));
		m_pluginName = pluginName;
	}

	public PluginException(PluginName pluginName, Throwable cause) {
		super(messageWithPrefix(pluginName, null), cause);
		m_pluginName = pluginName;
	}

	public PluginName getPluginName() {
		return m_pluginName;
	}

}
