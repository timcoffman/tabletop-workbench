package com.tcoffman.ttwb.component.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponent;
import com.tcoffman.ttwb.component.GameComponentRef;

public class StandardComponentRefManager<T extends GameComponent> implements GameComponentRefManager<T> {

	private final String m_idPrefix;
	private int m_nextId = 1;
	private final Map<String, T> m_managedComponents = new HashMap<>();
	private final Map<T, String> m_managedComponentIds = new HashMap<>();
	private final Map<String, GameComponentManagedRef<T>> m_modelComponentRefs = new HashMap<>();

	public StandardComponentRefManager(String idPrefix) {
		m_idPrefix = idPrefix;
	}

	@Override
	public Stream<Map.Entry<String, T>> references() {
		return m_managedComponents.entrySet().stream();
	}

	@Override
	public void registerAll(GameComponentRefResolver<T> resolver) {
		resolver.references().forEach((ref) -> register(ref.getValue(), ref.getKey()));
	}

	@Override
	public void register(T component, String id) {
		final T registeredComponent = m_managedComponents.get(id);
		if (null != registeredComponent && registeredComponent != component)
			throw new IllegalStateException("cannot register two different components with the same id");
		m_managedComponents.put(id, component);
		m_managedComponentIds.put(component, id);
	}

	@Override
	public void resolveAll() {
		m_modelComponentRefs.forEach((id, ref) -> ref.set(resolve(id)));
		m_modelComponentRefs.clear();
	}

	private T resolve(String id) {
		final T component = m_managedComponents.get(id);
		if (null == component)
			throw new IllegalStateException("failed to resolve component id \"" + id + "\"");
		return component;
	}

	@Override
	public GameComponentRef<T> createRef(String id) {
		GameComponentManagedRef<T> ref = m_modelComponentRefs.get(id);
		if (null == ref)
			m_modelComponentRefs.put(id, ref = new GameComponentManagedRef<>());
		return ref;
	}

	@Override
	public Optional<GameComponentRef<T>> lookup(String id) {
		final T component = m_managedComponents.get(id);
		return Optional.ofNullable(component).map(GameComponentManagedRef<T>::new);
	}

	@Override
	public Optional<String> lookupId(T component) {
		final String id = m_managedComponentIds.get(component);
		return Optional.ofNullable(id);
	}

	private static class GameComponentManagedRef<T extends GameComponent> extends GameComponentRef<T> {

		private T m_component;

		public GameComponentManagedRef() {
		}

		public GameComponentManagedRef(T component) {
			m_component = component;
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

		@Override
		public String toString() {
			return null == m_component ? "(unresolved)" : m_component.toString();
		}

	}

	@Override
	public String nextId() {
		String nextId;
		do
			nextId = m_idPrefix + "-" + m_nextId++;
		while (m_managedComponents.containsKey(nextId));
		return nextId;
	}

}