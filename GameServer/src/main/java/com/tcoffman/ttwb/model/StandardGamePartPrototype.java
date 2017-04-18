package com.tcoffman.ttwb.model;

import java.util.Collections;
import java.util.Map;

import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.state.GamePartPlace;

public class StandardGamePartPrototype implements GamePartPrototype {

	private PluginName m_declaringPlugin;
	private Map<String, GamePartPlace> m_places;

	@Override
	public PluginName getDeclaringPlugin() {
		return m_declaringPlugin;
	}

	@Override
	public Map<String, GamePartPlace> getPlaces() {
		return Collections.unmodifiableMap(m_places);
	}

}
