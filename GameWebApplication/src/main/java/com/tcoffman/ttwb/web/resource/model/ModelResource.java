package com.tcoffman.ttwb.web.resource.model;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.web.GameModelFileRepository;
import com.tcoffman.ttwb.web.resource.model.plugin.ModelPluginsResource;

public class ModelResource extends AbstractModelSubresource {

	public ModelResource(GameModelFileRepository.Bundle modelBundle) {
		super(modelBundle);
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ModelResource get() {
		return this;
	}

	public URI getResource() {
		return ModelsResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId());
	}

	public String getLabel() {
		return modelBundle().getModel().getDocumentation().getName(GameComponentDocumentation.Format.SHORT);
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/plugins")
	public ModelPluginsResource getPlugins() throws GameComponentBuilderException {
		return subresource(new ModelPluginsResource(modelBundle()));
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/prototypes")
	public PartPrototypesResource getPartPrototypes() throws GameComponentBuilderException {
		return subresource(new PartPrototypesResource(modelBundle()));
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/roles")
	public RolesResource getRoles() throws GameComponentBuilderException {
		return subresource(new RolesResource(modelBundle()));
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/parts")
	public PartInstancesResource getParts() throws GameComponentBuilderException {
		return subresource(new PartInstancesResource(modelBundle()));
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/stages")
	public StagesResource getStages() throws GameComponentBuilderException {
		return subresource(new StagesResource(modelBundle(), modelBundle().getModel()));
	}

}
