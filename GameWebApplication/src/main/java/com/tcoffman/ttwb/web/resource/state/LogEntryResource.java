package com.tcoffman.ttwb.web.resource.state;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.state.mutation.GameStateLogEntry;
import com.tcoffman.ttwb.state.mutation.GameStateMutation;
import com.tcoffman.ttwb.web.GameStateRepository;
import com.tcoffman.ttwb.web.resource.model.StagesResource;

public class LogEntryResource extends AbstractStateSubresource {

	private final long m_logEntryIndex;
	private final GameStateLogEntry m_logEntry;

	public LogEntryResource(GameStateRepository.Bundle stateBundle, long logEntryIndex, GameStateLogEntry logEntry) {
		super(stateBundle);
		m_logEntryIndex = logEntryIndex;
		m_logEntry = logEntry;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public LogEntryResource get() {
		return this;
	}

	public URI getResource() {
		return LogEntriesResource.pathTo(baseUriBuilder()).build(stateBundle().getStateId(), m_logEntryIndex);
	}

	public URI getForward() throws GameComponentBuilderException {
		return StagesResource.pathTo(baseUriBuilder()).build(stateBundle().getStateId(), lookupStageId(m_logEntry.getForward()));
	}

	public List<StateMutationResource> getForwardMutations() {
		return m_logEntry.forwardMutations().map(this::createStateMutationResource).collect(Collectors.toList());
	}

	public URI getRollback() throws GameComponentBuilderException {
		return StagesResource.pathTo(baseUriBuilder()).build(stateBundle().getStateId(), lookupStageId(m_logEntry.getRollback()));
	}

	public List<StateMutationResource> getRollbackMutations() {
		return m_logEntry.rollbackMutations().map(this::createStateMutationResource).collect(Collectors.toList());
	}

	public String getLabel() {
		return m_logEntry.toString();
	}

	private StateMutationResource createStateMutationResource(GameStateMutation mutation) {

		return subresource(new StateMutationResource(stateBundle(), m_logEntryIndex, m_logEntry, mutation));
	}
}
