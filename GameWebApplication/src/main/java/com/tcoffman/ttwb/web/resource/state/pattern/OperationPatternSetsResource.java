package com.tcoffman.ttwb.web.resource.state.pattern;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.model.pattern.operation.GameOperationPatternSet;
import com.tcoffman.ttwb.web.GameStateRepository;
import com.tcoffman.ttwb.web.resource.state.AbstractStateSubresource;
import com.tcoffman.ttwb.web.resource.state.StateResource;
import com.tcoffman.ttwb.web.resource.state.StatesResource;

public class OperationPatternSetsResource extends AbstractStateSubresource {

	public OperationPatternSetsResource(GameStateRepository.Bundle stateBundle) {
		super(stateBundle);
	}

	public URI getResource() {
		return StatesResource.pathTo(baseUriBuilder()).path(StateResource.class, "getAllowedOperations").build(stateBundle().getStateId());
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<OperationPatternSetResource> getOperationPatternSets() {
		return stateBundle().getState().allowedOperations().map(this::createOperationPatternSetResource).collect(Collectors.toList());
	}

	private OperationPatternSetResource createOperationPatternSetResource(GameOperationPatternSet opPatternSet) {
		return subresource(new OperationPatternSetResource(stateBundle(), opPatternSet));
	}

}