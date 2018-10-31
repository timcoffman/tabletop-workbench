package com.tcoffman.ttwb.web.resource.model;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.web.GameModelFileRepository;

import io.swagger.annotations.ApiOperation;

public class PartPrototypeResource extends AbstractModelSubresource {

	private final String m_prototypeId;
	private final GamePartPrototype m_prototype;

	public PartPrototypeResource(GameModelFileRepository.Bundle modelBundle, String prototypeId, GamePartPrototype prototype) {
		super(modelBundle);
		m_prototypeId = prototypeId;
		m_prototype = prototype;
	}

	@ApiOperation("Retrieve a Part Prototype")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@JsonIgnore
	public PartPrototypeResource getPartPrototype() {
		return this;
	}

	public URI getResource() {
		return PartPrototypesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), m_prototypeId);
	}

	public String getLabel() {
		return m_prototype.getDocumentation().getName(GameComponentDocumentation.Format.SHORT);
	}

	public URI getExtends() {
		if (!m_prototype.getExtends().isPresent())
			return null;

		try {
			return PartPrototypesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), lookupPartPrototypeId(m_prototype.getExtends().get()));
		} catch (final GameComponentBuilderException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public URI getRole() {
		if (!m_prototype.getRoleBinding().isPresent())
			return null;

		try {
			return RolesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), lookupRoleId(m_prototype.getRoleBinding().get()));
		} catch (final GameComponentBuilderException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/places")
	public PlacePrototypesResource getPlaces() throws GameComponentBuilderException {
		return subresource(new PlacePrototypesResource(modelBundle(), m_prototypeId, m_prototype));
	}

}
