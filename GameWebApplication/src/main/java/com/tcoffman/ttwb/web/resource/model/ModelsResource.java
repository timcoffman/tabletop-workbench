package com.tcoffman.ttwb.web.resource.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.stream.XMLStreamException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.web.GameModelRepository;
import com.tcoffman.ttwb.web.resource.AbstractRootResource;

@Path("/models")
public class ModelsResource extends AbstractRootResource {

	public ModelsResource() {
		super();
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return uriBuilder.path(ModelsResource.class).path(ModelsResource.class, "getModel");
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ModelResource> getModels() {
		final List<ModelResource> models = new ArrayList<ModelResource>();
		for (final Iterator<? extends String> i = m_modelRepository.modelIds().iterator(); i.hasNext();)
			try {
				models.add(createModelResource(getModelBundle(i.next())));
			} catch (final GameComponentBuilderException ex) {
				// don't add
				ex.printStackTrace();
			}
		return models;
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/{modelId}")
	public ModelResource getModel(@PathParam("modelId") String modelId) throws GameComponentBuilderException, XMLStreamException {
		return createModelResource(getModelBundle(modelId));
	}

	private GameModelRepository.Bundle getModelBundle(String modelId) throws GameComponentBuilderException {
		return m_modelRepository.getBundle(modelId);
	}

	private ModelResource createModelResource(final GameModelRepository.Bundle modelBundle) {
		return subresource(new ModelResource(modelBundle));
	}

}
