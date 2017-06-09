package com.tcoffman.ttwb.core;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.plugin.AbstractGeneralPlugin;
import com.tcoffman.ttwb.plugin.PluginException;

public class Core extends AbstractGeneralPlugin {

	public static final String PLACE_PHYSICAL_TOP = "top";
	public static final String PLACE_PHYSICAL_BOTTOM = "bottom";
	public static final String RELATIONSHIP_PHYSICAL = "location";

	public static final String TOKEN_ROOT = "root";
	public static final String TOKEN_SUBJECT = "subject";
	public static final String TOKEN_TARGET = "target";
	public static final String TOKEN_SYSTEM = "system";

	@Override
	public GameComponentRef<GamePlaceType> getPlaceType(String localName) throws PluginException {
		if (PLACE_PHYSICAL_TOP.equals(localName))
			return getPlaceTypePhysicalTop();
		else if (PLACE_PHYSICAL_BOTTOM.equals(localName))
			return getPlaceTypePhysicalBottom();
		else
			return super.getPlaceType(localName);
	}

	@Override
	public GameComponentRef<GamePartRelationshipType> getRelationshipType(String localName) throws PluginException {
		if (RELATIONSHIP_PHYSICAL.equals(localName))
			return getRelationshipTypePhysical();
		else
			return super.getRelationshipType(localName);
	}

	private GamePartRelationshipType m_location = null;

	private final class CoreGameRelationshipType extends PluginComponent implements GamePartRelationshipType {
		public CoreGameRelationshipType(String localName) {
			super(localName);
		}
	}

	private class CoreGamePlaceType extends PluginComponent implements GamePlaceType {
		public CoreGamePlaceType(String localName) {
			super(localName);
		}
	}

	private GameComponentRef<GamePartRelationshipType> getRelationshipTypePhysical() {
		if (null == m_location)
			m_location = new CoreGameRelationshipType(RELATIONSHIP_PHYSICAL);
		return createRef(m_location);
	}

	private GamePlaceType m_physicalTop = null;
	private GamePlaceType m_physicalBottom = null;

	private GameComponentRef<GamePlaceType> getPlaceTypePhysicalTop() {
		if (null == m_physicalTop)
			m_physicalTop = new CoreGamePlaceType(PLACE_PHYSICAL_TOP);
		return createRef(m_physicalTop);
	}

	private GameComponentRef<GamePlaceType> getPlaceTypePhysicalBottom() {
		if (null == m_physicalBottom)
			m_physicalBottom = new CoreGamePlaceType(PLACE_PHYSICAL_BOTTOM);
		return createRef(m_physicalBottom);
	}
}
