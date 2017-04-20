package com.tcoffman.ttwb.plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class DefaultPluginFactory implements PluginFactory {

	private final Map<PluginName, Class<? extends Plugin>> m_plugins = new HashMap<PluginName, Class<? extends Plugin>>();

	public void install(PluginName name, Class<? extends Plugin> pluginClass) {
		m_plugins.put(name, pluginClass);
	}

	@Override
	public Plugin create(PluginName name) throws PluginException {
		final Class<? extends Plugin> pluginClass = m_plugins.get(name);
		if (null == pluginClass)
			throw new PluginException(name, "no plugin registered");
		try {
			final Constructor<? extends Plugin> ctor = pluginClass.getConstructor();
			final Plugin plugin = ctor.newInstance();
			plugin.setName(name);
			return plugin;
		} catch (final NoSuchMethodException ex) {
			throw new PluginException(name, "missing no-arg constructor", ex);
		} catch (final InvocationTargetException ex) {
			throw new PluginException(name, "error during creation", ex.getTargetException());
		} catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
			throw new PluginException(name, "cannot create", ex);
		}
	}

}
