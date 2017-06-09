package com.tcoffman.ttwb.web.resource.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.GameStageContainer;
import com.tcoffman.ttwb.web.GameModelRepository;
import com.tcoffman.ttwb.web.UnrecognizedValueException;

public class StagesResource extends AbstractModelSubresource {

	private final GameStageContainer m_container;

	public StagesResource(GameModelRepository.Bundle modelBundle, GameStageContainer container) {
		super(modelBundle);
		m_container = container;
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return ModelsResource.pathTo(uriBuilder).path(ModelResource.class, "getStages").path(StagesResource.class, "getStage");
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<StageResource> getStages() {
		final List<StageResource> stages = new ArrayList<StageResource>();
		for (final Iterator<? extends GameStage> i = m_container.stages().iterator(); i.hasNext();)
			try {
				final GameStage stage = i.next();
				stages.add(createStageResource(lookupId(stage), stage));
			} catch (final GameComponentBuilderException ex) {
				// don't add
				ex.printStackTrace();
			}
		return stages;
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/{stageId}")
	public StageResource getStage(@PathParam("stageId") String stageId) throws UnrecognizedValueException {
		final GameStage stage = lookupStage(stageId).get();
		return createStageResource(stageId, stage);
	}

	private StageResource createStageResource(final String stageId, final GameStage stage) {
		return subresource(new StageResource(modelBundle(), stageId, stage));
	}

}