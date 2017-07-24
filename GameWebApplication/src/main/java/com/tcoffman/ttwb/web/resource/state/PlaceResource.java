package com.tcoffman.ttwb.web.resource.state;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePlace;
import com.tcoffman.ttwb.web.GameStateFileRepository;
import com.tcoffman.ttwb.web.resource.model.plugin.PlaceTypesResource;

public class PlaceResource extends AbstractStateSubresource {

	private final String m_partId;
	private final GamePart m_part;
	private final GamePlace m_place;

	public PlaceResource(GameStateFileRepository.Bundle stateBundle, String partId, GamePart part, GamePlace place) {
		super(stateBundle);
		m_partId = partId;
		m_part = part;
		m_place = place;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public PlaceResource get() {
		return this;
	}

	public URI getResource() {
		final GamePlaceType placeType = m_place.getPrototype().get().getType().get();
		return PlacesResource.pathTo(baseUriBuilder()).build(stateBundle().getModelId(), m_partId, placeType.getDeclaringPlugin().toURI(),
				placeType.getLocalName());
	}

	public URI getTypeResource() {
		final GamePlaceType placeType = m_place.getPrototype().get().getType().get();
		return PlaceTypesResource.pathTo(baseUriBuilder()).build(stateBundle().getModelId(), placeType.getDeclaringPlugin().toURI(), placeType.getLocalName());
	}

	public String getLabel() {
		return m_place.getPrototype().get().getDocumentation().getName(GameComponentDocumentation.Format.SHORT);
	}

}
