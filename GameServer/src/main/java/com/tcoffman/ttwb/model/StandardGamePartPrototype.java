package com.tcoffman.ttwb.model;

import java.util.Collections;
import java.util.Map;

import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.state.GamePartPlace;

public class StandardGamePartPrototype implements GamePartPrototype {

	private final PluginName m_declaringPlugin;
	private Map<String, GamePartPlace> m_places;

	protected StandardGamePartPrototype(PluginName declaringPlugin) {
		m_declaringPlugin = declaringPlugin;
	}

	@Override
	public PluginName getDeclaringPlugin() {
		return m_declaringPlugin;
	}

	@Override
	public Map<String, GamePartPlace> getPlaces() {
		return Collections.unmodifiableMap(m_places);
	}

	public static Editor create(PluginName declaringPlugin) {
		return new StandardGamePartPrototype(declaringPlugin).edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public class Editor extends AbstractEditor<StandardGamePartPrototype> {

		@Override
		protected void validate() throws GameModelBuilderException {
		}

		@Override
		protected StandardGamePartPrototype model() {
			return StandardGamePartPrototype.this;
		}

	}

}
