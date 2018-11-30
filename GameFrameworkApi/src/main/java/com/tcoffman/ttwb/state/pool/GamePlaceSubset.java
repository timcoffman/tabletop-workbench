package com.tcoffman.ttwb.state.pool;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.state.GamePlace;

public abstract class GamePlaceSubset {

	protected final GamePartPool m_pool;

	public GamePlaceSubset(GamePartPool pool) {
		m_pool = pool;
	}

	public abstract GamePlaceSubset invert();

	public abstract void add(GamePlace place);

	public abstract void remove(GamePlace place);

	public abstract void retainIf(Predicate<GamePlace> filter);

	public abstract boolean contains(GamePlace part);

	public abstract boolean isEmpty();

	public abstract Stream<? extends GamePlace> stream();

	public void forEach(Consumer<GamePlace> action) {
		stream().forEach(action);
	}
}
