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

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.web.GameModelFileRepository;
import com.tcoffman.ttwb.web.UnrecognizedValueException;

public class RolesResource extends AbstractModelSubresource {

	public RolesResource(GameModelFileRepository.Bundle modelBundle) {
		super(modelBundle);
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return ModelsResource.pathTo(uriBuilder).path(ModelResource.class, "getRoles").path(RolesResource.class, "getRole");
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RoleResource> getRoles() {
		final List<RoleResource> roles = new ArrayList<RoleResource>();
		for (final Iterator<? extends GameRole> i = modelBundle().getModel().roles().iterator(); i.hasNext();)
			try {
				final GameRole role = i.next();
				roles.add(createRoleResource(lookupId(role), role));
			} catch (final GameComponentBuilderException ex) {
				// don't add
				ex.printStackTrace();
			}
		return roles;
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/{roleId}")
	public RoleResource getRole(@PathParam("roleId") String roleId) throws UnrecognizedValueException {
		final GameRole role = lookupRole(roleId).get();
		return createRoleResource(roleId, role);
	}

	private RoleResource createRoleResource(final String roleId, final GameRole role) {
		return subresource(new RoleResource(modelBundle(), roleId, role));
	}

}