package com.tcoffman.ttwb.web.resource.runner;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.stream.XMLStreamException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.web.GameStateFileRepository;
import com.tcoffman.ttwb.web.resource.ResourceMetaData.Builder;
import com.tcoffman.ttwb.web.resource.state.AbstractStateSubresource;
import com.tcoffman.ttwb.web.resource.state.LogEntriesResource;
import com.tcoffman.ttwb.web.resource.state.ParticipantsResource;
import com.tcoffman.ttwb.web.resource.state.PartsResource;
import com.tcoffman.ttwb.web.resource.state.RelationshipsResource;
import com.tcoffman.ttwb.web.resource.state.StatesResource;
import com.tcoffman.ttwb.web.resource.state.pattern.OperationPatternSetsResource;

import io.swagger.annotations.ApiOperation;

public class RunnerResource extends AbstractStateSubresource {

	public RunnerResource(GameStateFileRepository.Bundle stateBundle) {
		super(stateBundle);
	}

	@ApiOperation("Retrieve a Runner")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@JsonIgnore
	public RunnerResource getRunner() {
		return this;
	}

	public URI getResource() {
		return StatesResource.pathTo(baseUriBuilder()).build(stateBundle().getStateId());
	}

	@Override
	protected Builder metaDataBuilder() {
		return super.metaDataBuilder().identifiedBy(stateBundle().getModelId() + ":" + stateBundle().getStateId()).labelled(getLabel());
	}

	public String getLabel() {
		String modelName;
		try {
			modelName = getModelProvider(stateBundle().getModelId()).getModel().getDocumentation().getName(GameComponentDocumentation.Format.LONG);
		} catch (GameComponentBuilderException | XMLStreamException ex) {
			modelName = "State";
		}
		return modelName + " #" + stateBundle().getStateId();
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/participants")
	public ParticipantsResource getParticipants() throws GameComponentBuilderException {
		return subresource(new ParticipantsResource(stateBundle()));
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/parts")
	public PartsResource getParts() throws GameComponentBuilderException {
		return subresource(new PartsResource(stateBundle()));
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/relationships")
	public RelationshipsResource getRelationships() throws GameComponentBuilderException {
		return subresource(new RelationshipsResource(stateBundle()));
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/allowedOperations")
	public OperationPatternSetsResource getAllowedOperations() throws GameComponentBuilderException {
		return subresource(new OperationPatternSetsResource(stateBundle()));
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/log")
	public LogEntriesResource getLog() throws GameComponentBuilderException {
		return subresource(new LogEntriesResource(stateBundle()));
	}

}
