package com.tcoffman.ttwb.web.resource.model;

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

import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePlacePrototype;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.web.GameModelFileRepository;

public class PlacePrototypesResource extends AbstractModelSubresource {

	private final String m_partPrototypeId;
	private final GamePartPrototype m_partPrototype;

	public PlacePrototypesResource(GameModelFileRepository.Bundle modelBundle, String partPrototypeId, GamePartPrototype partPrototype) {
		super(modelBundle);
		m_partPrototypeId = partPrototypeId;
		m_partPrototype = partPrototype;
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return ModelsResource.pathTo(uriBuilder).path(ModelResource.class, "getPartPrototypes").path(PlacePrototypesResource.class, "getPlacePrototype");
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<PlacePrototypeResource> getPlacePrototypes() {
		return m_partPrototype.places().map(this::createPlacePrototypeResource).collect(Collectors.toList());
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/{pluginUri}/{placeTypeLocalName}")
	public PlacePrototypeResource getPlacePrototype(@PathParam("pluginUri") String pluginUri, @PathParam("placeTypeLocalName") String placeTypeLocalName)
			throws XMLStreamException, PluginException {
		final PluginName pluginName = PluginName.create(URI.create(pluginUri));
		final ModelPlugin modelPlugin = (ModelPlugin) modelBundle().getPluginSet().requirePlugin(pluginName);
		final GamePlaceType placeType = modelPlugin.getPlaceType(placeTypeLocalName).get();

		final GamePlacePrototype prototype = m_partPrototype.places().filter((p) -> p.getType().get() == placeType).findAny()
				.orElseThrow(this::missingModelComponent);
		return createPlacePrototypeResource(prototype);
	}

	private PlacePrototypeResource createPlacePrototypeResource(GamePlacePrototype prototype) {
		return subresource(new PlacePrototypeResource(modelBundle(), m_partPrototypeId, m_partPrototype, prototype));
	}

}