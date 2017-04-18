package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GameRole;

public class StandardGameParticipant implements GameParticipant {

	private final GameRole m_role;

	public StandardGameParticipant(GameRole role) {
		m_role = role;
	}

	@Override
	public GameRole getRole() {
		return m_role;
	}

}
