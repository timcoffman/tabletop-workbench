package com.tcoffman.ttwb.web.resource.state;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.model.pattern.place.StandardAnyPlacePattern;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePlace;
import com.tcoffman.ttwb.web.GameStateFileRepository;
import com.tcoffman.ttwb.web.UnrecognizedValueException;
import com.tcoffman.ttwb.web.resource.model.ModelsResource;
import com.tcoffman.ttwb.web.resource.model.StagesResource;
import com.tcoffman.ttwb.web.resource.state.pattern.OperationPatternSetsResource;
import com.tcoffman.ttwb.web.resource.state.pattern.PatternUtils.PlacePatternForm;

public class StateResource extends AbstractStateSubresource {

	public StateResource(GameStateFileRepository.Bundle stateBundle) {
		super(stateBundle);
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@JsonIgnore
	public StateResource getState() {
		return this;
	}

	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void delete() throws GameComponentBuilderException {
		stateBundle().remove();
	}

	@POST
	@Path("/find")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<PlaceResource> find(PlacePatternForm patternForm) throws GameComponentBuilderException, UnrecognizedValueException {
		final List<PlaceResource> places = new ArrayList<>();
		final GamePlacePattern placePattern = createPlacePattern(patternForm).orElse(StandardAnyPlacePattern.create().done());
		for (final Iterator<? extends GamePlace> i = stateBundle().getState().find(placePattern).iterator(); i.hasNext();) {
			final GamePlace place = i.next();
			final GamePart part = place.getOwner();
			final String partId = requireId(part);
			places.add(subresource(new PlaceResource(stateBundle(), partId, part, place)));
		}
		return places;
	}

	private Optional<GamePlacePattern> createPlacePattern(PlacePatternForm patternForm) throws UnrecognizedValueException, GameComponentBuilderException {
		return patternUtils().createPlacePattern(patternForm);
	}

	public URI getResource() {
		return StatesResource.pathTo(baseUriBuilder()).build(stateBundle().getStateId());
	}

	public URI getModel() {
		return ModelsResource.pathTo(baseUriBuilder()).build(stateBundle().getModelId());
	}

	public URI getStage() throws GameComponentBuilderException {
		return StagesResource.pathTo(baseUriBuilder()).build(stateBundle().getModelId(), lookupStageId(stateBundle().getState().getCurrentStage()));
	}

	public String getLabel() {
		String modelName;
		try {
			modelName = getModelProvider(stateBundle().getModelId()).getModel().getDocumentation().getName(GameComponentDocumentation.Format.LONG);
		} catch (final GameComponentBuilderException ex) {
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
