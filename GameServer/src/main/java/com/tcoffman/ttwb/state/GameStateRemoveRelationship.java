package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GameRole;

public class GameStateRemoveRelationship extends GameStateInvertibleMutation {

	private final GamePlace m_subject;
	private final GamePlace m_target;
	private final GameComponentRef<GamePartRelationshipType> m_relationshipType;

	public GameStateRemoveRelationship(GameRole role, GameOperation.Type type, GameComponentRef<GamePartRelationshipType> relationshipType, GamePlace subject,
			GamePlace target) {
		super(role, type);
		m_relationshipType = relationshipType;
		m_subject = subject;
		m_target = target;
	}

	@Override
	public void apply(GameState.Mutator stateMutator) {
		stateMutator.createRelationship(m_relationshipType, m_subject, m_target);
	}

	@Override
	public GameStateMutation inverted() {
		return new GameStateAddRelationship(m_role, m_type, m_relationshipType, m_subject, m_target);
	}
}
