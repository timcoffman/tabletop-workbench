package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GameRole;

public abstract class GameStateMutation {

	protected final GameRole m_role;
	protected final GameOperation.Type m_type;

	public GameStateMutation(GameRole role, GameOperation.Type type) {
		m_role = role;
		m_type = type;
	}

	public GameRole getRole() {
		return m_role;
	}

	public GameOperation.Type getType() {
		return m_type;
	}

	public abstract void apply(GameState.Mutator stateMutator);

}