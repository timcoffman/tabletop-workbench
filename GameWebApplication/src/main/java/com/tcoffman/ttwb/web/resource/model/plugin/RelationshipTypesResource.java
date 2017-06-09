package com.tcoffman.ttwb.web.resource.model.plugin;

import java.net.URI;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.UriBuilder;

import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.web.GameModelRepository;
import com.tcoffman.ttwb.web.resource.model.AbstractModelSubresource;

public class RelationshipTypesResource extends AbstractModelSubresource {

	private final ModelPlugin m_plugin;

	public RelationshipTypesResource(GameModelRepository.Bundle modelBundle, ModelPlugin plugin) {
		super(modelBundle);
		m_plugin = plugin;
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return ModelPluginsResource.pathTo(uriBuilder).path(ModelPluginResource.class, "getRelationshipTypes")
				.path(RelationshipTypesResource.class, "getRelationshipType");
	}

	public URI getResource() {
		return ModelPluginsResource.pathTo(baseUriBuilder()).path(ModelPluginResource.class, "getRelationshipTypes")
				.build(modelBundle().getModelId(), m_plugin.getName());
	}

	public String getLabel() {
		return "Relationship Types";
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/{localName}")
	public RelationshipTypeResource getRelationshipType(@PathParam("localName") String localName) throws PluginException {
		final GamePartRelationshipType relationshipType = m_plugin.getRelationshipType(localName).get();
		return createRelationshipTypeResource(localName, relationshipType);
	}

	private RelationshipTypeResource createRelationshipTypeResource(String localName, final GamePartRelationshipType relationshipType) {
		return subresource(new RelationshipTypeResource(modelBundle(), relationshipType));
	}

}