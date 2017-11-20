package com.tcoffman.ttwb.web;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class GameWebApplication extends ResourceConfig {

	public GameWebApplication() {
		register(new GameWebApplicationBinder());
		packages(true, "com.tcoffman.ttwb.web");
		property(ServerProperties.TRACING, "ALL");
	}
}
