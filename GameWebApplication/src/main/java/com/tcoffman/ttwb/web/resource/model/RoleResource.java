package com.tcoffman.ttwb.web.resource.model;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.web.GameModelFileRepository;

public class RoleResource extends AbstractModelSubresource {

	private final String m_roleId;
	private final GameRole m_role;

	public RoleResource(GameModelFileRepository.Bundle modelBundle, String roleId, GameRole role) {
		super(modelBundle);
		m_roleId = roleId;
		m_role = role;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RoleResource get() {
		return this;
	}

	public URI getResource() {
		return ModelsResource.pathTo(baseUriBuilder()).path(ModelResource.class, "getRoles").path(RolesResource.class, "getRole")
				.build(modelBundle().getModelId(), m_roleId);
	}

	public String getLabel() {
		return m_role.getDocumentation().getName(GameComponentDocumentation.Format.SHORT);
	}

}
