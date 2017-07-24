package com.tcoffman.ttwb.web.resource.model.plugin;

import java.net.URI;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.UriBuilder;

import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.web.GameModelFileRepository;
import com.tcoffman.ttwb.web.resource.model.AbstractModelSubresource;

public class PlaceTypesResource extends AbstractModelSubresource {

	private final ModelPlugin m_plugin;

	public PlaceTypesResource(GameModelFileRepository.Bundle modelBundle, ModelPlugin plugin) {
		super(modelBundle);
		m_plugin = plugin;
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return ModelPluginsResource.pathTo(uriBuilder).path(ModelPluginResource.class, "getPlaceTypes").path(PlaceTypesResource.class, "getPlaceType");
	}

	public URI getResource() {
		return ModelPluginsResource.pathTo(baseUriBuilder()).path(ModelPluginResource.class, "getPlaceTypes")
				.build(modelBundle().getModelId(), m_plugin.getName());
	}

	public String getLabel() {
		return "Place Types";
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/{localName}")
	public PlaceTypeResource getPlaceType(@PathParam("localName") String localName) throws PluginException {
		final GamePlaceType placeType = m_plugin.getPlaceType(localName).get();
		return createPlaceTypeResource(localName, placeType);
	}

	private PlaceTypeResource createPlaceTypeResource(String localName, final GamePlaceType placeType) {
		return subresource(new PlaceTypeResource(modelBundle(), placeType));
	}

}