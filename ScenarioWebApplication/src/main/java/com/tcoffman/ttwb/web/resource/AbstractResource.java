package com.tcoffman.ttwb.web.resource;

import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

public abstract class AbstractResource {

	@Context
	UriInfo m_uriInfo;
	@Context
	ResourceContext m_resources;
	@Context
	Request m_request;

	public AbstractResource() {
	}

	protected <T extends AbstractResource> T subresource(T resource) {
		return m_resources.initResource(resource);
	}

}
