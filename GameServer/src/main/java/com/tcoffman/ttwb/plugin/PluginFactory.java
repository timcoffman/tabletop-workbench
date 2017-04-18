package com.tcoffman.ttwb.plugin;

public interface PluginFactory {

	Plugin create(PluginName name) throws PluginException;

}
