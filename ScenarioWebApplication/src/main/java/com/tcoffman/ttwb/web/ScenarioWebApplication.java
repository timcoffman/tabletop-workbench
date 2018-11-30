package com.tcoffman.ttwb.web;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class ScenarioWebApplication extends ResourceConfig {

	public ScenarioWebApplication() {
		register(new ScenarioWebApplicationBinder());
		packages(true, "com.tcoffman.ttwb.web", "com.tcoffman.ttwb.dao");
		property(ServerProperties.TRACING, "ALL");
	}
}
