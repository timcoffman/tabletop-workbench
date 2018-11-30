package com.tcoffman.ttwb.state.pool.place;

import static java.util.stream.Collectors.counting;

import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePlace;
import com.tcoffman.ttwb.state.pool.GamePartPool;
import com.tcoffman.ttwb.state.pool.GamePlaceSubset;

public class GamePlaceExclusionList extends GamePlaceList {

	public GamePlaceExclusionList(GamePartPool pool) {
		super(pool);
	}

	@Override
	public GamePlaceSubset invert() {
		final GamePlaceList subset = new GamePlaceInclusionList(m_pool);
		subset.m_places.addAll(m_places);
		return subset;
	}

	@Override
	public void add(GamePlace place) {
		removeFromInternalList(place);
	}

	@Override
	public boolean contains(GamePlace place) {
		return !internalListContains(place);
	}

	@Override
	public boolean isEmpty() {
		return m_places.stream().map(GamePlace::getOwner).distinct().collect(counting()) == m_pool.size();
	}

	@Override
	public Stream<? extends GamePlace> stream() {
		return m_pool.stream().flatMap(GamePart::places).filter(this::internalListDoesNotContain);
	}

	@Override
	public void remove(GamePlace place) {
		addToInternalList(place);
	}

	@Override
	public void retainIf(Predicate<GamePlace> filter) {
		m_places.removeIf(filter);
		m_pool.stream().flatMap(GamePart::places).filter(p -> !filter.test(p)).forEach(m_places::add);
	}

}
