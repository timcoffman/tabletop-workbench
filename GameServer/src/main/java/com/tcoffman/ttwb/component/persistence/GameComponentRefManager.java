package com.tcoffman.ttwb.component.persistence;

import com.tcoffman.ttwb.component.GameComponent;
import com.tcoffman.ttwb.component.GameComponentRef;

public interface GameComponentRefManager<T extends GameComponent> extends GameComponentRefResolver<T> {

	public abstract String nextId();

	public abstract GameComponentRef<T> createRef(String id);

	public abstract void resolveAll();

	public abstract void register(T component, String id);

	public abstract void registerAll(GameComponentRefResolver<T> resolver);

}