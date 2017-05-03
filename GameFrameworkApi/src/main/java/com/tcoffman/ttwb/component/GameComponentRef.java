package com.tcoffman.ttwb.component;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class GameComponentRef<T extends GameComponent> {

	public abstract T get();

	@Override
	public String toString() {
		return get().toString();
	}

	public static <T extends GameComponent> GameComponentRef<T> wrap(T component) {
		return new DirectComponentRef<T>(component);
	}

	public static <T extends GameComponent> GameComponentRef<T> deferred(Supplier<T> supplier) {
		return new DeferredComponentRef<T>(supplier);
	}

	private static class DeferredComponentRef<T extends GameComponent> extends GameComponentRef<T> {
		private final Supplier<T> m_supplier;

		public DeferredComponentRef(Supplier<T> supplier) {
			m_supplier = supplier;
		}

		@Override
		public T get() {
			return m_supplier.get();
		}

	}

	private static class DirectComponentRef<T extends GameComponent> extends GameComponentRef<T> {
		private final T m_component;

		public DirectComponentRef(T component) {
			Objects.requireNonNull(component);
			m_component = component;
		}

		@Override
		public T get() {
			return m_component;
		}

	}

}
