package com.tcoffman.ttwb.state.mutation;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.state.GamePlace;
import com.tcoffman.ttwb.state.GameState;

public class GameStateRemoveRelationship extends GameStateRelationshipMutation {

	public GameStateRemoveRelationship(GameRole role, GameOperation.Type type, GameComponentRef<GamePartRelationshipType> relationshipType, GamePlace source,
			GamePlace destination) {
		super(role, type, relationshipType, source, destination);
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	@Override
	public void apply(GameState.Mutator stateMutator) {
		stateMutator.destroyRelationship(m_relationshipType, m_source, m_destination);
	}

	@Override
	public AbstractGameStateMutation inverted() {
		return new GameStateAddRelationship(m_role, m_type, m_relationshipType, m_source, m_destination);
	}

	@Override
	protected String verbString() {
		return "remove";
	}

}
