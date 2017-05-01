package com.tcoffman.ttwb.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import com.tcoffman.ttwb.plugin.PluginName;

public abstract class AbstractEditor<T> {

	public interface Initializer<E extends AbstractEditor<?>> {
		void configure(E editor) throws GameComponentBuilderException;
	}

	private final Collection<Consumer<T>> m_consumers = new ArrayList<Consumer<T>>();
	protected boolean m_done = false;

	public <C extends AbstractEditor<T>> C completed(Consumer<T> consumer) {
		m_consumers.add(consumer);
		@SuppressWarnings("unchecked")
		final C self = (C) this;
		return self;
	}

	final protected void requireNotDone() {
		if (m_done)
			throw new IllegalStateException("GameModel cannot be edited further");
	}

	protected abstract void validate() throws GameComponentBuilderException;

	final protected <U> void requirePresent(PluginName pluginName, String name, U obj) throws GameComponentBuilderException {
		if (null == obj)
			throw new GameComponentBuilderException(pluginName, "missing " + name + " for " + model());
	}

	final protected <U> void requireNotEmpty(PluginName pluginName, String name, Collection<U> objs) throws GameComponentBuilderException {
		if (objs.isEmpty())
			throw new GameComponentBuilderException(pluginName, "missing " + name + " for " + model());
	}

	protected abstract T model();

	protected <U extends AbstractEditor<T>, E extends AbstractEditor<?>> U configure(E editor, Initializer<E> initializer) throws GameComponentBuilderException {
		requireNotDone();
		initializer.configure(editor);
		editor.done();

		@SuppressWarnings("unchecked")
		final U self = (U) this;
		return self;
	}

	final public T done() throws GameComponentBuilderException {
		requireNotDone();
		validate();
		m_done = true;
		final T model = model();
		m_consumers.stream().forEach((c) -> c.accept(model));
		return model;
	}

}
