package com.tcoffman.ttwb.state.mutation;

import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.state.GameState;

public abstract class AbstractGameStateMutation implements GameStateMutation {

	protected final GameRole m_role;
	protected final GameOperation.Type m_type;

	public AbstractGameStateMutation(GameRole role, GameOperation.Type type) {
		m_role = role;
		m_type = type;
	}

	@Override
	public GameRole getRole() {
		return m_role;
	}

	@Override
	public GameOperation.Type getType() {
		return m_type;
	}

	@Override
	public abstract void apply(GameState.Mutator stateMutator);

	@Override
	public abstract <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E;
}