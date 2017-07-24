package com.tcoffman.ttwb.web.resource.state;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.stream.XMLStreamException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.state.GameParticipant;
import com.tcoffman.ttwb.web.GameStateFileRepository;
import com.tcoffman.ttwb.web.UnrecognizedValueException;

public class ParticipantsResource extends AbstractStateSubresource {

	public ParticipantsResource(GameStateFileRepository.Bundle stateBundle) {
		super(stateBundle);
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ParticipantResource> getParticipants() {
		final List<ParticipantResource> participants = new ArrayList<>();
		for (final Iterator<? extends GameParticipant> i = stateBundle().getState().participants().iterator(); i.hasNext();)
			try {
				final GameParticipant participant = i.next();
				participants.add(createParticipantResource(lookupId(participant.getRole()), participant));
			} catch (final GameComponentBuilderException ex) {
				// don't add
				ex.printStackTrace();
			}
		return participants;
	}

	public static class ParticipantCreationForm {
		private String m_role;
		private String m_user;

		public String getUser() {
			return m_user;
		}

		public void setUser(String user) {
			m_user = user;
		}

		public String getRole() {
			return m_role;
		}

		public void setRole(String roleId) {
			m_role = roleId;
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ParticipantResource createParticipant(ParticipantCreationForm participantCreationForm)
			throws GameComponentBuilderException, XMLStreamException, UnrecognizedValueException {

		final String auth = participantCreationForm.getUser();

		final GameRole role = lookupRole(participantCreationForm.getRole()).get();

		final GameParticipant participant = stateBundle().getState().createParticipant(role, auth);
		stateBundle().store(this::getModelProvider);
		return createParticipantResource(participantCreationForm.getRole(), participant);
	}

	private ParticipantResource createParticipantResource(String roleId, GameParticipant participant) {
		return subresource(new ParticipantResource(stateBundle(), roleId, participant));
	}

}