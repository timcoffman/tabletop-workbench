package com.tcoffman.ttwb.core;

import java.util.function.Consumer;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.StatePlugin;
import com.tcoffman.ttwb.state.GameState;
import com.tcoffman.ttwb.state.GameStateException;

public class Core implements ModelPlugin, StatePlugin {

	public static final String PLACE_PHYSICAL_TOP = "top";
	public static final String PLACE_PHYSICAL_BOTTOM = "bottom";
	public static final String RELATIONSHIP_PHYSICAL = "location";

	private PluginName m_name;

	@Override
	public PluginName getName() {
		return m_name;
	}

	@Override
	public void setName(PluginName name) {
		m_name = name;
	}

	@Override
	public void validate(GameState state, Consumer<GameStateException> reporter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate(GameModel model, Consumer<GameComponentBuilderException> reporter) {
		// TODO Auto-generated method stub

	}

	@Override
	public GameComponentRef<GamePlaceType> getPlaceType(String localName) throws PluginException {
		if (PLACE_PHYSICAL_TOP.equals(localName))
			return getPlaceTypePhysicalTop();
		else if (PLACE_PHYSICAL_BOTTOM.equals(localName))
			return getPlaceTypePhysicalBottom();
		else
			throw new PluginException(getName(), "no definition for place type \"" + localName + "\"");
	}

	@Override
	public GameComponentRef<GamePartRelationshipType> getRelationshipType(String localName) throws PluginException {
		if (RELATIONSHIP_PHYSICAL.equals(localName))
			return getRelationshipTypePhysical();
		else
			throw new PluginException(getName(), "no definition for relationship type \"" + localName + "\"");
	}

	private GamePartRelationshipType m_location = null;

	private final class CoreGameRelationshipType implements GamePartRelationshipType {
		private final String m_localName;

		public CoreGameRelationshipType(String localName) {
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

	private GameComponentRef<GamePartRelationshipType> getRelationshipTypePhysical() {
		if (null == m_location)
			m_location = new CoreGameRelationshipType(RELATIONSHIP_PHYSICAL);
		return () -> m_location;
	}

	private GamePlaceType m_physicalTop = null;
	private GamePlaceType m_physicalBottom = null;

	private final class CoreGamePlaceType implements GamePlaceType {
		private final String m_localName;

		public CoreGamePlaceType(String localName) {
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

	private GameComponentRef<GamePlaceType> getPlaceTypePhysicalTop() {
		if (null == m_physicalTop)
			m_physicalTop = new CoreGamePlaceType(PLACE_PHYSICAL_TOP);
		return () -> m_physicalTop;
	}

	private GameComponentRef<GamePlaceType> getPlaceTypePhysicalBottom() {
		if (null == m_physicalBottom)
			m_physicalBottom = new CoreGamePlaceType(PLACE_PHYSICAL_BOTTOM);
		return () -> m_physicalBottom;
	}

}
