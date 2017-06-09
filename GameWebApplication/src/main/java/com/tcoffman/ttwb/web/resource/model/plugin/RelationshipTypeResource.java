package com.tcoffman.ttwb.web.resource.model.plugin;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.web.GameModelRepository.Bundle;
import com.tcoffman.ttwb.web.resource.model.AbstractModelSubresource;

public class RelationshipTypeResource extends AbstractModelSubresource {

	private final GamePartRelationshipType m_relationshipType;

	public RelationshipTypeResource(Bundle modelBundle, GamePartRelationshipType relationshipType) {
		super(modelBundle);
		m_relationshipType = relationshipType;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RelationshipTypeResource get() {
		return this;
	}

	public URI getResource() {
		return RelationshipTypesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), m_relationshipType.getDeclaringPlugin().toURI(),
				m_relationshipType.getLocalName());
	}

	public String getLabel() {
		return m_relationshipType.getLocalName();
	}

}
