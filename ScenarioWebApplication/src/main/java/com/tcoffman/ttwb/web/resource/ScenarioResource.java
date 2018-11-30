package com.tcoffman.ttwb.web.resource;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.dao.ExecutionDao;
import com.tcoffman.ttwb.dao.ModelDao;
import com.tcoffman.ttwb.dao.ScenarioDao;
import com.tcoffman.ttwb.dao.StateDao;
import com.tcoffman.ttwb.scenario.engine.ScenarioRunner;
import com.tcoffman.ttwb.util.Beans;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@Api("Scenarios")
@SwaggerDefinition(info = @Info(description = "Scenario Management", version = "1.0.0", title = "Game Scenario Server API"))
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("scenario")
public class ScenarioResource {

	@Inject
	StateDao stateDao;

	@Inject
	ModelDao modelDao;

	@Inject
	ScenarioDao scenarioDao;

	@Inject
	ExecutionDao executionDao;

	private Scenario scenarioFrom(Map.Entry<Long, com.tcoffman.ttwb.dao.data.Scenario> scenarioEntry) {
		return scenarioWithIdFrom(scenarioEntry.getKey()).apply(scenarioEntry.getValue());
	}

	private Function<com.tcoffman.ttwb.dao.data.Scenario, Scenario> scenarioWithIdFrom(long id) {
		return scenario -> {
			final Scenario s = new Scenario(id, scenario.getModel());
			s.setDescription(scenario.getDescription());
			s.setParticipantCountMin(scenario.getParticipantCountMin());
			s.setParticipantCountMax(scenario.getParticipantCountMax());
			s.setExecutionCount(scenario.getExecutionCount());
			s.setTermination(scenario.getTermination());
			return s;
		};
	}

	private com.tcoffman.ttwb.dao.data.Scenario toScenario(Scenario scenario) {
		final com.tcoffman.ttwb.dao.data.Scenario s = new com.tcoffman.ttwb.dao.data.Scenario();
		s.setModel(scenario.getModel());
		s.setDescription(scenario.getDescription());
		s.setParticipantCountMin(scenario.getParticipantCountMin());
		s.setParticipantCountMax(scenario.getParticipantCountMax());
		s.setExecutionCount(scenario.getExecutionCount());
		s.setTermination(scenario.getTermination());
		return s;
	}

	@ApiOperation("get all scenarios")
	@GET
	public List<Scenario> getScenarios() {
		return scenarioDao.all().entrySet().stream().map(this::scenarioFrom).collect(toList());
	}

	@ApiOperation("get one scenario by id")
	@GET
	@Path("/{scenarioId}")
	public Scenario getScenario(@PathParam("scenarioId") long scenarioId) {
		return scenarioDao.lookup(scenarioId).map(scenarioWithIdFrom(scenarioId)).orElseThrow(NotFoundException::new);
	}

	@ApiOperation("create a new scenario")
	@PUT
	public Scenario putScenario(Scenario scenario) {
		// @formatter:off
		Beans.bean( scenario )
			.property("participantCountMin").defaultTo( "1" )
			.property("participantCountMax").defaultTo( "*" )
			.property("executionCount").defaultTo( "1" )
			;
		// @formatter:on
		final long scenarioId = scenarioDao.create(toScenario(scenario));
		return getScenario(scenarioId);
	}

	@ApiOperation("update a scenario")
	@POST
	@Path("/{scenarioId}")
	public Scenario postScenario(@PathParam("scenarioId") long scenarioId, Scenario scenario) {
		if (scenarioId != scenario.getId())
			throw new BadRequestException("scenario id #" + scenario.getId() + " does not match expected id #" + scenarioId);
		scenarioDao.update(scenarioId, toScenario(scenario));
		return getScenario(scenarioId);
	}

	@ApiOperation("start running a scenario")
	@POST
	@Path("/{scenarioId}/run")
	public void runScenario(@PathParam("scenarioId") long scenarioId) {
		final com.tcoffman.ttwb.dao.data.Scenario scenario = scenarioDao.lookup(scenarioId).orElseThrow(NotFoundException::new);
		final com.tcoffman.ttwb.dao.data.Model model = modelDao.lookup(scenario.getModel()).orElseThrow(NotFoundException::new);
		final ScenarioRunner runner = new ScenarioRunner(scenarioId, scenario, model);
		runner.setStateDao(stateDao);
		runner.setModelDao(modelDao);
		runner.setScenarioDao(scenarioDao);
		runner.setExecutionDao(executionDao);
		runner.run();
	}

}
