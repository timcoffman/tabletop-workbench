package com.tcoffman.ttwb.web.resource.model.plugin;

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

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.web.GameModelFileRepository;
import com.tcoffman.ttwb.web.resource.model.AbstractModelSubresource;
import com.tcoffman.ttwb.web.resource.model.ModelResource;
import com.tcoffman.ttwb.web.resource.model.ModelsResource;

public class ModelPluginsResource extends AbstractModelSubresource {

	public ModelPluginsResource(GameModelFileRepository.Bundle modelBundle) {
		super(modelBundle);
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return ModelsResource.pathTo(uriBuilder).path(ModelResource.class, "getPlugins").path(ModelPluginsResource.class, "getPlugin");
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ModelPluginResource> getRequiredPlugins() {
		return modelBundle().getModel().getRequiredPlugins().stream().map(this::createModelPluginResource).collect(Collectors.toList());
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/{pluginUri}")
	public ModelPluginResource getPlugin(@PathParam("pluginUri") String pluginUri) throws GameComponentBuilderException, XMLStreamException {
		final PluginName pluginName = PluginName.create(URI.create(pluginUri));
		return createModelPluginResource(pluginName);
	}

	private ModelPluginResource createModelPluginResource(final PluginName pluginName) {
		return subresource(new ModelPluginResource(modelBundle(), pluginName));
	}

}
