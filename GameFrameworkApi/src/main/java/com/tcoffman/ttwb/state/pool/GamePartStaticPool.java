package com.tcoffman.ttwb.state.pool;

import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;

import com.tcoffman.ttwb.state.GamePart;

public class GamePartStaticPool extends GamePartPool {

	private final Collection<? extends GamePart> m_parts;

	public GamePartStaticPool(Collection<? extends GamePart> parts) {
		m_parts = unmodifiableCollection(parts);
	}

	@Override
	protected Collection<? extends GamePart> parts() {
		return m_parts;
	}

}
