package com.tcoffman.ttwb.web.resource.state;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.state.GamePlace;
import com.tcoffman.ttwb.state.mutation.AbstractGameStateMutation;
import com.tcoffman.ttwb.state.mutation.GameOperation;
import com.tcoffman.ttwb.state.mutation.GameStateAddRelationship;
import com.tcoffman.ttwb.state.mutation.GameStateLogEntry;
import com.tcoffman.ttwb.state.mutation.GameStateMutation;
import com.tcoffman.ttwb.state.mutation.GameStateRemoveRelationship;
import com.tcoffman.ttwb.web.GameStateFileRepository;
import com.tcoffman.ttwb.web.resource.model.RolesResource;
import com.tcoffman.ttwb.web.resource.model.plugin.RelationshipTypesResource;

public class StateMutationResource extends AbstractStateSubresource {

	private final long m_logEntryIndex;
	private final GameStateLogEntry m_logEntry;
	private final GameStateMutation m_mutation;

	public StateMutationResource(GameStateFileRepository.Bundle stateBundle, long logEntryIndex, GameStateLogEntry logEntry, GameStateMutation mutation) {
		super(stateBundle);
		m_logEntryIndex = logEntryIndex;
		m_logEntry = logEntry;
		m_mutation = mutation;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@JsonIgnore
	public StateMutationResource getStateMutation() {
		return this;
	}

	public URI getRole() throws GameComponentBuilderException {
		return RolesResource.pathTo(baseUriBuilder()).build(stateBundle().getStateId(), lookupId(m_mutation.getRole()));
	}

	public GameOperation.Type getOperationType() {
		return m_mutation.getType();
	}

	public String getMutation() {
		return m_mutation.visit(new AbstractGameStateMutation.Visitor<String, RuntimeException>() {

			@Override
			public String visit(GameStateAddRelationship addRelationship) {
				return "REL-ADD";
			}

			@Override
			public String visit(GameStateRemoveRelationship removeRelationship) {
				return "REL-REMOVE";
			}

		});
	}

	public URI getSource() throws GameComponentBuilderException {
		return m_mutation.visit(new AbstractGameStateMutation.Visitor<URI, GameComponentBuilderException>() {

			@Override
			public URI visit(GameStateAddRelationship addRelationship) throws GameComponentBuilderException {
				final GamePlace source = addRelationship.getSource();
				final GamePlaceType placeType = source.getPrototype().get().getType().get();
				return PlacesResource.pathTo(baseUriBuilder()).build(stateBundle().getStateId(), requireId(source.getOwner()),
						placeType.getDeclaringPlugin().toURI(), placeType.getLocalName());
			}

			@Override
			public URI visit(GameStateRemoveRelationship removeRelationship) throws GameComponentBuilderException {
				final GamePlace subject = removeRelationship.getSource();
				final GamePlaceType placeType = subject.getPrototype().get().getType().get();
				return PlacesResource.pathTo(baseUriBuilder()).build(stateBundle().getStateId(), requireId(subject.getOwner()),
						placeType.getDeclaringPlugin().toURI(), placeType.getLocalName());
			}

		});
	}

	public URI getDestination() throws GameComponentBuilderException {
		return m_mutation.visit(new AbstractGameStateMutation.Visitor<URI, GameComponentBuilderException>() {

			@Override
			public URI visit(GameStateAddRelationship addRelationship) throws GameComponentBuilderException {
				final GamePlace destination = addRelationship.getDestination();
				final GamePlaceType placeType = destination.getPrototype().get().getType().get();
				return PlacesResource.pathTo(baseUriBuilder()).build(stateBundle().getStateId(), requireId(destination.getOwner()),
						placeType.getDeclaringPlugin(), placeType.getLocalName());
			}

			@Override
			public URI visit(GameStateRemoveRelationship removeRelationship) throws GameComponentBuilderException {
				final GamePlace subject = removeRelationship.getDestination();
				final GamePlaceType placeType = subject.getPrototype().get().getType().get();
				return PlacesResource.pathTo(baseUriBuilder()).build(stateBundle().getStateId(), requireId(subject.getOwner()),
						placeType.getDeclaringPlugin().toURI(), placeType.getLocalName());
			}

		});
	}

	public URI getRelationshipType() throws GameComponentBuilderException {
		return m_mutation.visit(new AbstractGameStateMutation.Visitor<URI, GameComponentBuilderException>() {

			@Override
			public URI visit(GameStateAddRelationship addRelationship) throws GameComponentBuilderException {
				return RelationshipTypesResource.pathTo(baseUriBuilder()).build(stateBundle().getStateId(),
						addRelationship.getRelationshipType().get().getDeclaringPlugin().toURI(), addRelationship.getRelationshipType().get().getLocalName());
			}

			@Override
			public URI visit(GameStateRemoveRelationship removeRelationship) throws GameComponentBuilderException {
				return RelationshipTypesResource.pathTo(baseUriBuilder()).build(stateBundle().getStateId(),
						removeRelationship.getRelationshipType().get().getDeclaringPlugin().toURI(),
						removeRelationship.getRelationshipType().get().getLocalName());
			}

		});
	}

	public String getLabel() {
		return m_logEntry.toString();
	}

}
