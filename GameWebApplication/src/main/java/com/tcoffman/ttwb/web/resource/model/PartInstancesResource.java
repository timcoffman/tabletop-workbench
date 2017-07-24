package com.tcoffman.ttwb.web.resource.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.stream.XMLStreamException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GamePartInstance;
import com.tcoffman.ttwb.web.GameModelFileRepository;

public class PartInstancesResource extends AbstractModelSubresource {

	public PartInstancesResource(GameModelFileRepository.Bundle modelBundle) {
		super(modelBundle);
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return ModelsResource.pathTo(uriBuilder).path(ModelResource.class, "getParts").path(PartInstancesResource.class, "getPart");
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<PartInstanceResource> getParts() {
		final List<PartInstanceResource> parts = new ArrayList<PartInstanceResource>();
		foreachWithIndex(modelBundle().getModel().parts(), (i, r) -> parts.add(createPartInstanceResource(i, r)));
		return parts;
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/{partIndex}")
	public PartInstanceResource getPart(@PathParam("partIndex") long partIndex) throws GameComponentBuilderException, XMLStreamException {
		final GamePartInstance part = nthElement(partIndex, modelBundle().getModel().parts()).orElseThrow(this::missingModelComponent);
		return createPartInstanceResource(partIndex, part);
	}

	private PartInstanceResource createPartInstanceResource(final long index, final GamePartInstance part) {
		return subresource(new PartInstanceResource(modelBundle(), index, part));
	}

}