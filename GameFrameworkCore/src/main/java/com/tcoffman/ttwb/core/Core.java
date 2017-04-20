package com.tcoffman.ttwb.core;

import java.util.function.Consumer;

import com.tcoffman.ttwb.model.GameComponentRef;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GameModelBuilderException;
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
	public void validate(GameModel model, Consumer<GameModelBuilderException> reporter) {
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

	private GamePlaceType m_physicalTop = null;
	private GamePlaceType m_physicalBottom = null;

	private final class CoreGamePlaceType implements GamePlaceType {
		@Override
		public PluginName getDeclaringPlugin() {
			return getName();
		}
	}

	private GameComponentRef<GamePlaceType> getPlaceTypePhysicalTop() {
		if (null == m_physicalTop)
			m_physicalTop = new CoreGamePlaceType();
		return () -> m_physicalTop;
	}

	private GameComponentRef<GamePlaceType> getPlaceTypePhysicalBottom() {
		if (null == m_physicalBottom)
			m_physicalBottom = new CoreGamePlaceType();
		return () -> m_physicalBottom;
	}

}
