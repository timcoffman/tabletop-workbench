package com.tcoffman.ttwb.web.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@Api("Application Info")
@SwaggerDefinition(info = @Info(description = "Information about the API", version = "1.0.0", title = "Game Server API"))
@Path("/app")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class AppResource extends AbstractRootResource {

	public AppResource() {
		super();
	}

	public interface AppInfo {
		String getVersion();
	}

	@ApiOperation("Scenario Web Application Info")
	@GET
	public AppInfo getInfo() {
		return new AppInfoImpl();
	}

	private static class AppInfoImpl implements AppInfo {

		@Override
		public String getVersion() {
			return "v1.0";
		}

	}
}
