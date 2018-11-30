package com.tcoffman.ttwb.state.pool;

import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.pool.place.GamePlaceExclusionList;
import com.tcoffman.ttwb.state.pool.place.GamePlaceInclusionList;

public abstract class GamePartPool {

	public GamePartPool() {
		super();
	}

	protected abstract Collection<? extends GamePart> parts();

	public GamePlaceSubset all() {
		final GamePlaceSubset set = new GamePlaceExclusionList(this);
		return set;
	}

	public GamePlaceSubset none() {
		final GamePlaceSubset set = new GamePlaceInclusionList(this);
		return set;
	}

	public boolean contains(GamePart part) {
		return parts().contains(part);
	}

	public int size() {
		return parts().size();
	}

	public boolean isEmpty() {
		return parts().isEmpty();
	}

	public Stream<? extends GamePart> stream() {
		return parts().stream();
	}

}