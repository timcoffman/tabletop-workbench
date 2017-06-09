package com.tcoffman.ttwb.web.resource.model;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.web.GameModelRepository;

public class PartPrototypeResource extends AbstractModelSubresource {

	private final String m_prototypeId;
	private final GamePartPrototype m_prototype;

	public PartPrototypeResource(GameModelRepository.Bundle modelBundle, String prototypeId, GamePartPrototype prototype) {
		super(modelBundle);
		m_prototypeId = prototypeId;
		m_prototype = prototype;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public PartPrototypeResource get() {
		return this;
	}

	public URI getResource() {
		return PartPrototypesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), m_prototypeId);
	}

	public String getLabel() {
		return m_prototype.getDocumentation().getName(GameComponentDocumentation.Format.SHORT);
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/places")
	public PlacePrototypesResource getPlaces() throws GameComponentBuilderException {
		return subresource(new PlacePrototypesResource(modelBundle(), m_prototypeId, m_prototype));
	}

}
