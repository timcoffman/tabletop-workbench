package com.tcoffman.ttwb.state;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import com.tcoffman.ttwb.model.GameRole;

public class StandardGameParticipant implements GameParticipant {

	private final GameRole m_role;
	private final Reference<GameState> m_owner;

	public StandardGameParticipant(GameState owner, GameRole role) {
		m_owner = new WeakReference<GameState>(owner);
		m_role = role;
	}

	@Override
	public GameRole getRole() {
		return m_role;
	}

	@Override
	public GameState getOwner() {
		return m_owner.get();
	}

}
