package com.tcoffman.ttwb.model.persistance.xml;

import java.util.HashMap;
import java.util.Map;

import com.tcoffman.ttwb.model.GameComponentRef;

public class GameModelComponentRefManager<T> {

	private final Map<String, T> m_managedComponents = new HashMap<String, T>();
	private final Map<String, GameComponentManagedRef<T>> m_modelComponentRefs = new HashMap<String, GameComponentManagedRef<T>>();

	public void register(T component, String id) {
		final T registeredComponent = m_managedComponents.get(id);
		if (null != registeredComponent && registeredComponent != component)
			throw new IllegalStateException("cannot register two different components with the same id");
		m_managedComponents.put(id, component);
	}

	public void resolveAll() {
		m_modelComponentRefs.forEach((id, ref) -> ref.set(resolve(id)));
	}

	private T resolve(String id) {
		final T component = m_managedComponents.get(id);
		if (null == component)
			throw new IllegalStateException("failed to resolve component id \"" + id + "\"");
		return component;
	}

	public GameComponentRef<T> createRef(String id) {
		GameComponentManagedRef<T> ref = m_modelComponentRefs.get(id);
		if (null == ref)
			m_modelComponentRefs.put(id, ref = new GameComponentManagedRef<T>());
		return ref;
	}

	private static class GameComponentManagedRef<T> implements GameComponentRef<T> {

		private T m_component;

		public GameComponentManagedRef() {
		}

		public void set(T component) {
			if (null == component)
				throw new IllegalStateException("cannot register using a missing component");
			if (null != m_component && m_component != component)
				throw new IllegalStateException("cannot register two different components with the same id");
			m_component = component;
		}

		@Override
		public T get() {
			if (null == m_component)
				throw new IllegalStateException("managed reference " + this.hashCode() + " was never resolved");
			return m_component;
		}

	}

}