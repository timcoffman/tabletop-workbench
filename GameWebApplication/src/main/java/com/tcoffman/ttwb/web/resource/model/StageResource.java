package com.tcoffman.ttwb.web.resource.model;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.web.GameModelFileRepository;

public class StageResource extends AbstractModelSubresource {

	private final String m_stageId;
	private final GameStage m_stage;

	public StageResource(GameModelFileRepository.Bundle modelBundle, String stageId, GameStage stage) {
		super(modelBundle);
		m_stageId = stageId;
		m_stage = stage;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public StageResource get() {
		return this;
	}

	public URI getResource() {
		return StagesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), m_stageId);
	}

	public String getLabel() {
		return m_stage.getDocumentation().getName(GameComponentDocumentation.Format.SHORT);
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/stages")
	public StagesResource getStages() throws GameComponentBuilderException {
		return subresource(new StagesResource(modelBundle(), m_stage));
	}

	@Path("/rules")
	public RulesResource getRules() throws GameComponentBuilderException {
		return subresource(new RulesResource(modelBundle(), m_stageId, m_stage));
	}

}
