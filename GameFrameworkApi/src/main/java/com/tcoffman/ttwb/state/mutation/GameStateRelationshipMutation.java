package com.tcoffman.ttwb.state.mutation;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.state.GamePlace;

public abstract class GameStateRelationshipMutation extends AbstractGameStateMutation implements GameStateInvertibleMutation {

	protected final GamePlace m_source;
	protected final GamePlace m_destination;
	protected final GameComponentRef<GamePartRelationshipType> m_relationshipType;

	public GameStateRelationshipMutation(GameRole role, GameOperation.Type type, GameComponentRef<GamePartRelationshipType> relationshipType, GamePlace source,
			GamePlace destination) {
		super(role, type);
		m_relationshipType = relationshipType;
		m_source = source;
		m_destination = destination;
	}

	public GamePlace getSource() {
		return m_source;
	}

	public GamePlace getDestination() {
		return m_destination;
	}

	public GameComponentRef<GamePartRelationshipType> getRelationshipType() {
		return m_relationshipType;
	}

	@Override
	public String toString() {
		return verbString() + " " + m_source + " --" + m_relationshipType.get() + "--> " + m_destination;
	}

	protected abstract String verbString();

}