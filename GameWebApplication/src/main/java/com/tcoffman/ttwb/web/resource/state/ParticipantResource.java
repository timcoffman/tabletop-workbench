package com.tcoffman.ttwb.web.resource.state;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.state.GameParticipant;
import com.tcoffman.ttwb.web.GameStateFileRepository;
import com.tcoffman.ttwb.web.resource.model.RolesResource;

public class ParticipantResource extends AbstractStateSubresource {

	private final String m_roleId;
	private final GameParticipant m_participant;

	public ParticipantResource(GameStateFileRepository.Bundle stateBundle, String roleId, GameParticipant participant) {
		super(stateBundle);
		m_roleId = roleId;
		m_participant = participant;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@JsonIgnore
	public ParticipantResource getParticipant() {
		return this;
	}

	public URI getRoleResource() {
		return RolesResource.pathTo(baseUriBuilder()).build(stateBundle().getModelId(), m_roleId);
	}

	public String getLabel() {
		return m_participant.getRole().getDocumentation().getName(GameComponentDocumentation.Format.SHORT);
	}

}
