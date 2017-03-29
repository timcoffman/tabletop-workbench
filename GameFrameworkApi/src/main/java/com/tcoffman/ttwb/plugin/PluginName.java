package com.tcoffman.ttwb.plugin;

public class PluginName {

	private final String m_qualifiedName;
	private final String m_version;

	public PluginName(String qualifiedName, String version) {
		m_qualifiedName = qualifiedName;
		m_version = version;
	}

	public String getQualifiedName() {
		return m_qualifiedName;
	}

	public String getVersion() {
		return m_version;
	}

	@Override
	public int hashCode() {
		return repr().hashCode();
	}

	private String repr() {
		return m_qualifiedName + ":" + m_version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof PluginName) {
			final PluginName pluginName = (PluginName) obj;
			return m_qualifiedName.equals(pluginName.getQualifiedName())
					&& m_version.equals(pluginName.getVersion());
		} else
			return repr().equals(obj.toString());
	}

}
