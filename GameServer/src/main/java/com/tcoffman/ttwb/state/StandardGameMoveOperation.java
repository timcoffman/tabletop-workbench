package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GameRole;

public class StandardGameMoveOperation extends StandardGameOperation implements GameMoveOperation {

	private final StandardGamePlaceInstance m_subject;
	private final StandardGamePlaceInstance m_target;

	public StandardGameMoveOperation(GameRole role, StandardGamePlaceInstance subject, StandardGamePlaceInstance target) {
		super(role);
		m_subject = subject;
		m_target = target;
	}

	@Override
	public StandardGamePlaceInstance getSubject() {
		return m_subject;
	}

	@Override
	public StandardGamePlaceInstance getTarget() {
		return m_target;
	}

}
