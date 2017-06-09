package com.tcoffman.ttwb.state.persistence;

import com.tcoffman.ttwb.component.persistence.GameComponentRefResolver;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.StatePlugin;
import com.tcoffman.ttwb.state.GamePart;

public interface StateRefResolver {

	GameComponentRefResolver<GamePart> getPartResolver();

	StatePlugin requireStatePlugin(PluginName pluginName) throws PluginException;

}