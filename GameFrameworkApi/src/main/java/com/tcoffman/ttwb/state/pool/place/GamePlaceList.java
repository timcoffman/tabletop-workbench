package com.tcoffman.ttwb.state.pool.place;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.tcoffman.ttwb.state.GamePlace;
import com.tcoffman.ttwb.state.pool.GamePartPool;
import com.tcoffman.ttwb.state.pool.GamePlaceSubset;

public abstract class GamePlaceList extends GamePlaceSubset {

	protected final Set<GamePlace> m_places = new HashSet<>();

	public GamePlaceList(GamePartPool pool) {
		super(pool);
	}

	protected void addToInternalList(GamePlace place) {
		m_places.add(place);
	}

	protected void removeFromInternalList(GamePlace place) {
		m_places.remove(place);
	}

	protected boolean internalListContains(GamePlace place) {
		return m_places.contains(place);
	}

	protected boolean internalListDoesNotContain(GamePlace place) {
		return !m_places.contains(place);
	}

	protected int internalListSize() {
		return m_places.size();
	}

	protected Stream<GamePlace> internalListStream() {
		return m_places.stream();
	}

}
