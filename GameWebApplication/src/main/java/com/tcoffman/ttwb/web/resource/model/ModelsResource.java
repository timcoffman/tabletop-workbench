package com.tcoffman.ttwb.web.resource.model;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.stream.XMLStreamException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.web.GameModelFileRepository;
import com.tcoffman.ttwb.web.resource.AbstractRootResource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@Api("Models")
@SwaggerDefinition(info = @Info(description = "Access to view game models", version = "1.0.0", title = "Game Models API"))
@Path("/models")
public class ModelsResource extends AbstractRootResource {

	public ModelsResource() {
		super();
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return uriBuilder.path(ModelsResource.class).path(ModelsResource.class, "getModel");
	}

	@ApiOperation("List of all models")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<URI> getModels() {
		return m_modelRepository.modelIds().map(pathTo(baseUriBuilder())::build).collect(Collectors.toList());
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/{modelId}")
	public ModelResource getModel(@PathParam("modelId") String modelId) throws GameComponentBuilderException, XMLStreamException {
		return createModelResource(getModelBundle(modelId));
	}

	private GameModelFileRepository.Bundle getModelBundle(String modelId) throws GameComponentBuilderException {
		return m_modelRepository.getBundle(modelId);
	}

	private ModelResource createModelResource(final GameModelFileRepository.Bundle modelBundle) {
		return subresource(new ModelResource(modelBundle));
	}

}
