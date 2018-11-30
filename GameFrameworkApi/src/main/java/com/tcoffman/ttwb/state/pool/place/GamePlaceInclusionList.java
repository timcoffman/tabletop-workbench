package com.tcoffman.ttwb.state.pool.place;

import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.state.GamePlace;
import com.tcoffman.ttwb.state.pool.GamePartPool;
import com.tcoffman.ttwb.state.pool.GamePlaceSubset;

public class GamePlaceInclusionList extends GamePlaceList {

	public GamePlaceInclusionList(GamePartPool pool) {
		super(pool);
	}

	@Override
	public GamePlaceSubset invert() {
		final GamePlaceList subset = new GamePlaceExclusionList(m_pool);
		subset.m_places.addAll(m_places);
		return subset;
	}

	@Override
	public void add(GamePlace place) {
		addToInternalList(place);
	}

	@Override
	public boolean contains(GamePlace place) {
		return internalListContains(place);
	}

	@Override
	public boolean isEmpty() {
		return 0 == internalListSize();
	}

	@Override
	public Stream<GamePlace> stream() {
		return internalListStream();
	}

	@Override
	public void remove(GamePlace place) {
		removeFromInternalList(place);
	}

	@Override
	public void retainIf(Predicate<GamePlace> filter) {
		m_places.removeIf(p -> !filter.test(p));
	}

}
