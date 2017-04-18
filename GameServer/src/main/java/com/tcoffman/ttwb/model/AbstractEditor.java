package com.tcoffman.ttwb.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public abstract class AbstractEditor<T> {

	private final Collection<Consumer<T>> m_consumers = new ArrayList<Consumer<T>>();
	protected boolean m_done = false;

	public <C extends AbstractEditor<T>> C completed(Consumer<T> consumer) {
		m_consumers.add(consumer);
		@SuppressWarnings("unchecked")
		final C self = (C) this;
		return self;
	}

	protected void requireNotDone() {
		if (m_done)
			throw new IllegalStateException("GameModel cannot be edited further");
	}

	protected abstract void validate() throws GameModelBuilderException;

	protected abstract T model();

	public T done() throws GameModelBuilderException {
		requireNotDone();
		validate();
		m_done = true;
		final T model = model();
		m_consumers.parallelStream().forEach((c) -> c.accept(model));
		return model;
	}

}
