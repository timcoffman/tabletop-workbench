package com.tcoffman.ttwb.web.resource.model.plugin;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.web.GameModelFileRepository;
import com.tcoffman.ttwb.web.resource.model.AbstractModelSubresource;

public class ModelPluginResource extends AbstractModelSubresource {

	private final PluginName m_pluginName;

	public ModelPluginResource(GameModelFileRepository.Bundle modelBundle, final PluginName pluginName) {
		super(modelBundle);
		m_pluginName = pluginName;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@JsonIgnore
	public ModelPluginResource getModelPlugin() {
		return this;
	}

	public URI getResource() {
		return ModelPluginsResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), m_pluginName.toURI());
	}

	public String getLabel() {
		return m_pluginName.getQualifiedName();
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/placeTypes")
	public PlaceTypesResource getPlaceTypes() throws PluginException {
		final ModelPlugin modelPlugin = (ModelPlugin) modelBundle().getPluginSet().requirePlugin(m_pluginName);
		return createPlaceTypesResource(modelPlugin);
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/relationshipTypes")
	public RelationshipTypesResource getRelationshipTypes() throws PluginException {
		final ModelPlugin modelPlugin = (ModelPlugin) modelBundle().getPluginSet().requirePlugin(m_pluginName);
		return createRelationshipTypesResource(modelPlugin);
	}

	private PlaceTypesResource createPlaceTypesResource(ModelPlugin modelPlugin) {
		return subresource(new PlaceTypesResource(modelBundle(), modelPlugin));
	}

	private RelationshipTypesResource createRelationshipTypesResource(ModelPlugin modelPlugin) {
		return subresource(new RelationshipTypesResource(modelBundle(), modelPlugin));
	}

}
