package com.tcoffman.ttwb.web.resource.state;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.state.GamePartRelationship;
import com.tcoffman.ttwb.web.GameStateRepository;

public class RelationshipsResource extends AbstractStateSubresource {

	public RelationshipsResource(GameStateRepository.Bundle stateBundle) {
		super(stateBundle);
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RelationshipResource> getRelationships() {
		return stateBundle().getState().relationships().map(this::createRelationshipResource).collect(Collectors.toList());
	}

	private RelationshipResource createRelationshipResource(GamePartRelationship relationship) {
		return subresource(new RelationshipResource(stateBundle(), relationship));
	}

}