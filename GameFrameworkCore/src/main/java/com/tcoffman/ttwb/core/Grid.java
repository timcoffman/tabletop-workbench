package com.tcoffman.ttwb.core;

import java.util.HashMap;
import java.util.Map;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.plugin.AbstractGeneralPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;

public class Grid extends AbstractGeneralPlugin {

	private static final java.util.regex.Pattern REGEX_PLACE_TYPE = java.util.regex.Pattern.compile("[0-9]+(-[0-9]+)*");

	@Override
	public GameComponentRef<GamePlaceType> getPlaceType(String localName) throws PluginException {
		if (REGEX_PLACE_TYPE.matcher(localName).matches()) {
			GameComponentRef<GamePlaceType> placeType = m_places.get(localName);
			if (null == placeType)
				m_places.put(localName, placeType = createRef(new GridGamePlaceType(localName)));
			return placeType;
		} else
			return super.getPlaceType(localName);
	}

	private final Map<String, GameComponentRef<GamePlaceType>> m_places = new HashMap<String, GameComponentRef<GamePlaceType>>();

	private final class GridGamePlaceType implements GamePlaceType {
		private final String m_localName;

		public GridGamePlaceType(String localName) {
			m_localName = localName;
		}

		@Override
		public PluginName getDeclaringPlugin() {
			return getName();
		}

		@Override
		public String getLocalName() {
			return m_localName;
		}

		@Override
		public String toString() {
			return getDeclaringPlugin().toString() + "/" + getLocalName();
		}
	}

}
