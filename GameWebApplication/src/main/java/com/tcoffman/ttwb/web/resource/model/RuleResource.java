package com.tcoffman.ttwb.web.resource.model;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.model.GameRule;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPattern;
import com.tcoffman.ttwb.web.GameModelRepository;
import com.tcoffman.ttwb.web.resource.model.pattern.OperationPatternResource;

public class RuleResource extends AbstractModelSubresource {

	private final String m_stageId;
	private final GameStage m_stage;
	private final long m_ruleIndex;
	private final GameRule m_rule;

	public RuleResource(GameModelRepository.Bundle modelBundle, String stageId, GameStage stage, long ruleIndex, GameRule rule) {
		super(modelBundle);
		m_stageId = stageId;
		m_stage = stage;
		m_ruleIndex = ruleIndex;
		m_rule = rule;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RuleResource get() {
		return this;
	}

	public URI getResource() {
		return StagesResource.pathTo(baseUriBuilder()).path(StageResource.class, "getRules").path(RulesResource.class, "getRule")
				.build(modelBundle().getModelId(), m_stageId, m_ruleIndex);
	}

	public String getLabel() {
		return m_rule.toString();
	}

	public String getResultLabel() throws GameComponentBuilderException {
		return m_rule.getResult().get().getDocumentation().getName(GameComponentDocumentation.Format.SHORT);
	}

	public URI getResultResource() throws GameComponentBuilderException {
		return StagesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), lookupStageId(m_rule.getResult()));
	}

	@Path("/operationPatterns")
	public List<OperationPatternResource> getOperationPatterns() {
		return m_rule.operationPatterns().map(this::createOperationPatternResource).collect(Collectors.toList());
	}

	private OperationPatternResource createOperationPatternResource(GameOperationPattern opPattern) {
		return subresource(new OperationPatternResource(modelBundle(), opPattern));
	}
}
