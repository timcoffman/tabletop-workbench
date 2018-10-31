package com.tcoffman.ttwb.web.resource.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.stream.XMLStreamException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GameRule;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.web.GameModelFileRepository;

import io.swagger.annotations.ApiOperation;

public class RulesResource extends AbstractModelSubresource {

	private final String m_stageId;
	private final GameStage m_stage;

	public RulesResource(GameModelFileRepository.Bundle modelBundle, String stageId, GameStage stage) {
		super(modelBundle);
		m_stageId = stageId;
		m_stage = stage;
	}

	@ApiOperation("List of Game Rules")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RuleResource> getRules() {
		final List<RuleResource> rules = new ArrayList<>();
		foreachWithIndex(m_stage.rules(), (i, r) -> rules.add(createRuleResource(i, r)));
		return rules;
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/{ruleIndex}")
	public RuleResource getRule(@PathParam("ruleIndex") int ruleIndex) throws GameComponentBuilderException, XMLStreamException {
		final GameRule rule = nthElement(ruleIndex, m_stage.rules()).orElseThrow(this::missingModelComponent);
		return createRuleResource(ruleIndex, rule);
	}

	private RuleResource createRuleResource(long ruleIndex, final GameRule rule) {
		return subresource(new RuleResource(modelBundle(), m_stageId, m_stage, ruleIndex, rule));
	}

}