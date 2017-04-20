package com.tcoffman.ttwb.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.model.GamePartInstance;
import com.tcoffman.ttwb.model.GamePlace;
import com.tcoffman.ttwb.model.GamePartRelationshipPrototype;

public class StandardGamePlaceInstance implements GamePlaceInstance {

	private final Collection<GamePartRelationshipPrototype> m_outgoingRelationships = new ArrayList<GamePartRelationshipPrototype>();
	private final Collection<GamePartRelationshipPrototype> m_incomingRelationships = new ArrayList<GamePartRelationshipPrototype>();

	@Override
	public GamePartInstance getPart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GamePlace getPlacePrototype() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addOutgoingRelationship(GamePartRelationshipPrototype relationship) {
		m_outgoingRelationships.add(relationship);

	}

	public void addIncomingRelationship(GamePartRelationshipPrototype relationship) {
		m_incomingRelationships.add(relationship);

	}

	@Override
	public Stream<? extends GamePartRelationshipPrototype> outgoingRelationships() {
		return m_outgoingRelationships.parallelStream();
	}

	@Override
	public Stream<? extends GamePartRelationshipPrototype> incomingRelationships() {
		return m_incomingRelationships.parallelStream();
	}

}
