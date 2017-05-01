package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GameRole;

public class StandardGameMoveOperation extends StandardGameOperation implements GameMoveOperation {

	private final GamePlace m_subject;
	private final GamePlace m_target;

	public StandardGameMoveOperation(GameRole role, GamePlace subject, GamePlace target) {
		super(role);
		m_subject = subject;
		m_target = target;
	}

	@Override
	public GamePlace getSubject() {
		return m_subject;
	}

	@Override
	public GamePlace getTarget() {
		return m_target;
	}

}
