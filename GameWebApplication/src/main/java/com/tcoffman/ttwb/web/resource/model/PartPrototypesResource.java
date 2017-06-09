package com.tcoffman.ttwb.web.resource.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.stream.XMLStreamException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.web.GameModelRepository;
import com.tcoffman.ttwb.web.UnrecognizedValueException;

public class PartPrototypesResource extends AbstractModelSubresource {

	public PartPrototypesResource(GameModelRepository.Bundle modelBundle) {
		super(modelBundle);
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return ModelsResource.pathTo(uriBuilder).path(ModelResource.class, "getPartPrototypes").path(PartPrototypesResource.class, "getPartPrototype");
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<PartPrototypeResource> getPartPrototypes() {
		final List<PartPrototypeResource> prototypes = new ArrayList<PartPrototypeResource>();
		for (final Iterator<? extends GamePartPrototype> i = modelBundle().getModel().prototypes().iterator(); i.hasNext();)
			try {
				final GamePartPrototype prototype = i.next();
				prototypes.add(createPartPrototypeResource(lookupId(prototype), prototype));
			} catch (final GameComponentBuilderException ex) {
				// don't add
				ex.printStackTrace();
			}
		return prototypes;
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/{prototypeId}")
	public PartPrototypeResource getPartPrototype(@PathParam("prototypeId") String prototypeId) throws UnrecognizedValueException {
		return createPartPrototypeResource(prototypeId, lookupPartPrototype(prototypeId).get());
	}

	private PartPrototypeResource createPartPrototypeResource(final String prototypeId, final GamePartPrototype prototype) {
		return subresource(new PartPrototypeResource(modelBundle(), prototypeId, prototype));
	}

}