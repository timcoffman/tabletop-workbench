package com.tcoffman.ttwb.web.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.scenario.engine.EngineScheduler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@Api("Scenario Engine")
@SwaggerDefinition(info = @Info(description = "Scenario Engine", version = "1.0.0", title = "Game Server API"))
@Path("/engine")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class EngineResource extends AbstractRootResource {

	@Inject
	EngineScheduler engineService;

	public EngineResource() {
		super();
	}

	public interface EngineInfo {
		boolean getStatus();
	}

	@ApiOperation("Scenario Engine Status")
	@GET
	public EngineInfo getInfo() {
		return new EngineInfoImpl();
	}

	private class EngineInfoImpl implements EngineInfo {

		@Override
		public boolean getStatus() {
			return engineService.isRunning();
		}

	}

	@ApiOperation("Scenario Engine Status")
	@POST
	@Path("status")
	public void status(boolean isRunning) {
		if (isRunning)
			engineService.start();
		else
			engineService.stop();
	}

}
