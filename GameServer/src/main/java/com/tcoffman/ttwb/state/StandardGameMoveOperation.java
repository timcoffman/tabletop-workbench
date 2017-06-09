package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.state.mutation.GameMoveOperation;

public class StandardGameMoveOperation extends StandardGameOperation implements GameMoveOperation {

	private final GamePlacePattern m_subject;
	private final GamePlacePattern m_target;

	public StandardGameMoveOperation(GameRole role, GamePlacePattern subject, GamePlacePattern target) {
		super(role);
		m_subject = subject;
		m_target = target;
	}

	@Override
	public GamePlacePattern getSubject() {
		return m_subject;
	}

	@Override
	public GamePlacePattern getTarget() {
		return m_target;
	}

}
