package com.tcoffman.ttwb.web.resource.state;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.state.GamePartRelationship;
import com.tcoffman.ttwb.state.GamePlace;
import com.tcoffman.ttwb.web.GameStateRepository;
import com.tcoffman.ttwb.web.resource.model.plugin.RelationshipTypesResource;

public class RelationshipResource extends AbstractStateSubresource {

	private final GamePartRelationship m_relationship;

	public RelationshipResource(GameStateRepository.Bundle stateBundle, GamePartRelationship relationship) {
		super(stateBundle);
		m_relationship = relationship;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RelationshipResource get() {
		return this;
	}

	public String getLabel() throws GameComponentBuilderException {
		return requireId(m_relationship.getSource().get().getOwner()) + "|"
				+ m_relationship.getSource().get().getPrototype().get().getType().get().getLocalName() + " -[" + m_relationship.getType().get().getLocalName()
				+ "]-> " + requireId(m_relationship.getDestination().get().getOwner()) + "|"
				+ m_relationship.getDestination().get().getPrototype().get().getType().get().getLocalName();
	}

	public URI getType() {
		final GamePartRelationshipType relType = m_relationship.getType().get();
		return RelationshipTypesResource.pathTo(baseUriBuilder()).build(stateBundle().getModelId(), relType.getDeclaringPlugin().toURI(),
				relType.getLocalName());
	}

	public String getSource() throws GameComponentBuilderException {
		final GamePlace place = m_relationship.getSource().get();
		final GamePlaceType placeType = place.getPrototype().get().getType().get();
		return requireId(place.getOwner()) + "|" + placeType.getDeclaringPlugin().toURI() + "/" + placeType.getLocalName();
	}

	public String getDestination() throws GameComponentBuilderException {
		final GamePlace place = m_relationship.getDestination().get();
		final GamePlaceType placeType = place.getPrototype().get().getType().get();
		return requireId(place.getOwner()) + "|" + placeType.getDeclaringPlugin().toURI() + "/" + placeType.getLocalName();
	}
}
