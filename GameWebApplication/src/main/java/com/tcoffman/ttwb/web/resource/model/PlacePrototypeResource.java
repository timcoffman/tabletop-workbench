package com.tcoffman.ttwb.web.resource.model;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.model.GameModelProperty;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePlacePrototype;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.web.GameModelRepository;
import com.tcoffman.ttwb.web.resource.model.plugin.PlaceTypesResource;

public class PlacePrototypeResource extends AbstractModelSubresource {

	private final String m_partPrototypeId;
	private final GamePartPrototype m_partPrototype;
	private final GamePlacePrototype m_prototype;

	public PlacePrototypeResource(GameModelRepository.Bundle modelBundle, String partPrototypeId, GamePartPrototype partPrototype, GamePlacePrototype prototype) {
		super(modelBundle);
		m_partPrototypeId = partPrototypeId;
		m_partPrototype = partPrototype;
		m_prototype = prototype;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public PlacePrototypeResource get() {
		return this;
	}

	public String getPlaceType() {
		final GamePlaceType placeType = m_prototype.getType().get();
		return PlaceTypesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), placeType.getDeclaringPlugin().toURI(), placeType.getLocalName())
				.toString();
	}

	public URI getResource() {
		final GamePlaceType placeType = m_prototype.getType().get();
		return PlacePrototypesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), m_partPrototypeId, placeType.getDeclaringPlugin().toURI(),
				placeType.getLocalName());
	}

	private String keyForProperty(GameModelProperty property) {
		return property.getDeclaringPlugin().toString() + "/" + property.getLocalName();
	}

	public Map<String, String> getProperties() {
		return m_prototype.properties().collect(Collectors.toMap(this::keyForProperty, GameModelProperty::getValue));
	}

	public String getLabel() {
		return m_prototype.getType().get().toString();
	}

}
