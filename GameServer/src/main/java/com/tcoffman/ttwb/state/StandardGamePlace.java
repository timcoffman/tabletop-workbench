package com.tcoffman.ttwb.state;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.StandardComponent;
import com.tcoffman.ttwb.model.GamePlacePrototype;

public class StandardGamePlace extends StandardComponent implements GamePlace {

	private final Reference<StandardGamePart> m_owner;
	private final GameComponentRef<GamePlacePrototype> m_prototype;

	private final Collection<GamePartRelationship> m_outgoingRelationships = new ArrayList<GamePartRelationship>();
	private final Collection<GamePartRelationship> m_incomingRelationships = new ArrayList<GamePartRelationship>();

	public StandardGamePlace(GameComponentRef<GamePlacePrototype> prototype, Reference<StandardGamePart> owner) {
		m_prototype = prototype;
		m_owner = owner;
	}

	@Override
	public GamePart getOwner() {
		return m_owner.get();
	}

	@Override
	public GameComponentRef<GamePlacePrototype> getPrototype() {
		return m_prototype;
	}

	public void addOutgoingRelationship(GamePartRelationship relationship) {
		m_outgoingRelationships.add(relationship);

	}

	public void addIncomingRelationship(GamePartRelationship relationship) {
		m_incomingRelationships.add(relationship);

	}

	@Override
	public Stream<? extends GamePartRelationship> outgoingRelationships() {
		return m_outgoingRelationships.stream();
	}

	@Override
	public Stream<? extends GamePartRelationship> incomingRelationships() {
		return m_incomingRelationships.stream();
	}

	@Override
	public String toString() {
		return m_prototype.get().toString();
	}

}
