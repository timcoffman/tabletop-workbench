package com.tcoffman.ttwb.web.resource.model.plugin;

import static java.util.stream.Collectors.toList;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.web.GameModelFileRepository;
import com.tcoffman.ttwb.web.resource.model.AbstractModelSubresource;

import io.swagger.annotations.ApiOperation;

public class RelationshipTypesResource extends AbstractModelSubresource {

	private final ModelPlugin m_plugin;

	public RelationshipTypesResource(GameModelFileRepository.Bundle modelBundle, ModelPlugin plugin) {
		super(modelBundle);
		m_plugin = plugin;
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return ModelPluginsResource.pathTo(uriBuilder).path(ModelPluginResource.class, "getRelationshipTypes").path(RelationshipTypesResource.class,
				"getRelationshipType");
	}

	public URI getResource() {
		return ModelPluginsResource.pathTo(baseUriBuilder()).path(ModelPluginResource.class, "getRelationshipTypes").build(modelBundle().getModelId(),
				m_plugin.getName());
	}

	@ApiOperation("List of all Relationship Types")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Collection<RelationshipTypeResource> getRelationshipTypes() {
		return m_plugin.getRelationshipTypeLocalNames().stream().map(ln -> {
			try {
				return getRelationshipType(ln);
			} catch (final PluginException ex) {
				return null;
			}
		}).filter(p -> null != p).collect(toList());
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