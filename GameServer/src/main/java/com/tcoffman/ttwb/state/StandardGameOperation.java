package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GameRole;

public abstract class StandardGameOperation implements GameOperation {

	private final GameRole m_role;

	public StandardGameOperation(GameRole role) {
		m_role = role;
	}

	@Override
	public GameRole getRole() {
		return m_role;
	}

}
