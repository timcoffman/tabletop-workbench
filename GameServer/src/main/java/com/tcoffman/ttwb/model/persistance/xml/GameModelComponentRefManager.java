package com.tcoffman.ttwb.model.persistance.xml;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tcoffman.ttwb.model.GameComponentRef;

public class GameModelComponentRefManager<T> {

	private final Map<String, T> m_managedComponents = new HashMap<String, T>();
	private final Set<GameComponentManagedRef<T>> m_modelComponentRefs = new HashSet<GameComponentManagedRef<T>>();

	public void register(T component, String id) {
		final T registeredComponent = m_managedComponents.get(id);
		if (null != registeredComponent && registeredComponent != component)
			throw new IllegalStateException("cannot register two different components with the same id");
		m_managedComponents.put(id, component);
	}

	public void resolveAll() {
		m_modelComponentRefs.forEach(GameComponentManagedRef::get);
	}

	public GameComponentRef<T> createRef(String id) {
		final GameComponentManagedRef<T> ref = new GameComponentManagedRef<T>(m_managedComponents, id);
		m_modelComponentRefs.add(ref);
		return ref;
	}

	private static class GameComponentManagedRef<T> implements GameComponentRef<T> {

		private Map<String, T> m_managedComponents;
		private final String m_id;
		private T m_component;

		public GameComponentManagedRef(Map<String, T> managedComponents, String id) {
			m_managedComponents = managedComponents;
			m_id = id;
		}

		@Override
		public T get() {
			if (null == m_component) {
				m_component = m_managedComponents.get(m_id);
				m_managedComponents = null;
			}
			return m_component;
		}

	}

}