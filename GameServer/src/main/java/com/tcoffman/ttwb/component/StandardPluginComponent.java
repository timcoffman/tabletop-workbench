package com.tcoffman.ttwb.component;

import com.tcoffman.ttwb.plugin.PluginName;

public abstract class StandardPluginComponent extends StandardComponent implements GamePluginComponent {

	private final PluginName m_declaringPlugin;
	private final String m_localName;

	public StandardPluginComponent(PluginName declaringPlugin, String localName) {
		m_declaringPlugin = declaringPlugin;
		m_localName = localName;
	}

	@Override
	public PluginName getDeclaringPlugin() {
		return m_declaringPlugin;
	}

	@Override
	public String getLocalName() {
		return m_localName;
	}
}
