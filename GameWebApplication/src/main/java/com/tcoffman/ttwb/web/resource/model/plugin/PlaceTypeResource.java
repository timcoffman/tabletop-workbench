package com.tcoffman.ttwb.web.resource.model.plugin;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcoffman.ttwb.component.persistence.GameModelRepository;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.web.resource.model.AbstractModelSubresource;

public class PlaceTypeResource extends AbstractModelSubresource {

	private final GamePlaceType m_placeType;

	public PlaceTypeResource(GameModelRepository.Bundle modelBundle, GamePlaceType placeType) {
		super(modelBundle);
		m_placeType = placeType;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@JsonIgnore
	public PlaceTypeResource getPlaceType() {
		return this;
	}

	public URI getResource() {
		return PlaceTypesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), m_placeType.getDeclaringPlugin().toURI(),
				m_placeType.getLocalName());
	}

	public String getLabel() {
		return m_placeType.getLocalName();
	}

}
