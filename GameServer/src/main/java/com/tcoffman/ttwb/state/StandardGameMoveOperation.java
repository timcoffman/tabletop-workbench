package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GameRole;

public class StandardGameMoveOperation extends StandardGameOperation implements GameMoveOperation {

	private final GamePartPlace m_subject;
	private final GamePartPlace m_target;

	public StandardGameMoveOperation(GameRole role, GamePartPlace subject, GamePartPlace target) {
		super(role);
		m_subject = subject;
		m_target = target;
	}

	@Override
	public GamePartPlace getSubject() {
		return m_subject;
	}

	@Override
	public GamePartPlace getTarget() {
		return m_target;
	}

}
