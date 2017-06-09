package com.tcoffman.ttwb.web.resource.state;

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

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePlace;
import com.tcoffman.ttwb.web.GameStateRepository;

public class PlacesResource extends AbstractStateSubresource {

	private final String m_partId;
	private final GamePart m_part;

	public PlacesResource(GameStateRepository.Bundle stateBundle, String partId, GamePart part) {
		super(stateBundle);
		m_partId = partId;
		m_part = part;
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return PartsResource.pathTo(uriBuilder).path(PartResource.class, "getPlaces").path(PlacesResource.class, "getPlace");
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<PlaceResource> getPlaces() {
		return m_part.places().map(this::createPlaceResource).collect(Collectors.toList());
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/{pluginUri}/{placeTypeLocalName}")
	public PlaceResource getPlace(@PathParam("pluginUri") String pluginUri, @PathParam("placeTypeLocalName") String placeTypeLocalName)
			throws XMLStreamException, PluginException {
		final PluginName pluginName = PluginName.create(URI.create(pluginUri));
		final ModelPlugin modelPlugin = (ModelPlugin) modelBundle().getPluginSet().requirePlugin(pluginName);
		final GameComponentRef<GamePlaceType> placeType = modelPlugin.getPlaceType(placeTypeLocalName);

		final GamePlace place = m_part.findPlace(placeType);
		return createPlaceResource(place);
	}

	private PlaceResource createPlaceResource(GamePlace place) {
		return subresource(new PlaceResource(stateBundle(), m_partId, m_part, place));
	}

}