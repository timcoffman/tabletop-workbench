package com.tcoffman.ttwb.plugin;

import com.tcoffman.ttwb.component.GameComponentRef;

public class AbstractPlugin implements Plugin {

	private PluginName m_name;

	@Override
	public PluginName getName() {
		return m_name;
	}

	@Override
	public void setName(PluginName name) {
		m_name = name;
	}

	private static class PluginComponentRef<T> implements GameComponentRef<T> {

		private final T m_component;

		public PluginComponentRef(T component) {
			m_component = component;
		}

		@Override
		public T get() {
			return m_component;
		}

	}

	protected <T> GameComponentRef<T> createRef(T component) {
		return new PluginComponentRef<T>(component);
	}

}
