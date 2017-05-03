package com.tcoffman.ttwb.plugin;

import java.util.Objects;

import com.tcoffman.ttwb.component.GameComponent;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.GamePluginComponent;

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

	private static class PluginComponentRef<T extends GameComponent> extends GameComponentRef<T> {

		private final T m_component;

		public PluginComponentRef(T component) {
			Objects.requireNonNull(component);
			m_component = component;
		}

		@Override
		public T get() {
			return m_component;
		}

	}

	protected <T extends GameComponent> GameComponentRef<T> createRef(T component) {
		return new PluginComponentRef<T>(component);
	}

	protected abstract class PluginComponent implements GamePluginComponent {
		private final String m_localName;

		public PluginComponent(String localName) {
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
		public <T extends GameComponent> GameComponentRef<T> self() {
			return new PluginComponentRef<T>((T) this);
		}

		@Override
		public String toString() {
			return getDeclaringPlugin().toString() + "/" + getLocalName();
		}
	}

}
