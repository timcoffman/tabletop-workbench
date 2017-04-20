package com.tcoffman.ttwb.plugin;

import java.net.URI;
import java.net.URISyntaxException;

public class PluginName {

	private static final String URI_PATH_DELIMITER = "/";
	private static final String URI_PATH_VERSION_DELIMITER = "v";
	private static final String URI_SCHEME = "urn";
	private final String m_qualifiedName;
	private final String m_version;

	public PluginName(String qualifiedName, String version) {
		m_qualifiedName = qualifiedName;
		m_version = version;
	}

	public static PluginName create(URI pluginUri) {
		if (!URI_SCHEME.equals(pluginUri.getScheme()))
			throw new IllegalArgumentException("plugin uri is not a urn");
		final String[] sspParts = pluginUri.getSchemeSpecificPart().split(URI_PATH_DELIMITER);
		if (null == sspParts)
			throw new IllegalArgumentException("plugin uri is missing the scheme specific part");
		if (sspParts.length < 3)
			throw new IllegalArgumentException("plugin uri does not have enough parts");
		if (!URI_PATH_VERSION_DELIMITER.equals(sspParts[1]))
			throw new IllegalArgumentException("plugin uri does not have a version delimiter");
		return new PluginName(sspParts[0], sspParts[2]);
	}

	public URI toURI() {
		try {
			return new URI(URI_SCHEME, String.format("%1$s%2$s%3$s%4$s%5$s", m_qualifiedName, URI_PATH_DELIMITER, URI_PATH_VERSION_DELIMITER,
					URI_PATH_DELIMITER, m_version), null);
		} catch (final URISyntaxException ex) {
			throw new IllegalArgumentException(ex);
		}
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
			return m_qualifiedName.equals(pluginName.getQualifiedName()) && m_version.equals(pluginName.getVersion());
		} else
			return repr().equals(obj.toString());
	}

	@Override
	public String toString() {
		return repr();
	}

}
