package com.tcoffman.ttwb.web.resource.model;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.model.GamePartInstance;
import com.tcoffman.ttwb.web.GameModelFileRepository;

public class PartInstanceResource extends AbstractModelSubresource {

	private final long m_partIndex;
	private final GamePartInstance m_partInstance;

	public PartInstanceResource(GameModelFileRepository.Bundle modelBundle, long partIndex, GamePartInstance partInstance) {
		super(modelBundle);
		m_partIndex = partIndex;
		m_partInstance = partInstance;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public PartInstanceResource get() {
		return this;
	}

	public URI getResource() {
		return PartInstancesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), m_partIndex);
	}

	public URI getPrototypeResource() throws GameComponentBuilderException {
		return PartPrototypesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), lookupPartPrototypeId(m_partInstance.getPrototype()));
	}

	public URI getRole() {
		if (!m_partInstance.getRoleBinding().isPresent())
			return null;

		try {
			return RolesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), lookupRoleId(m_partInstance.getRoleBinding().get()));
		} catch (final GameComponentBuilderException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public String getLabel() {
		return m_partInstance.getPrototype().get().getDocumentation().getName(GameComponentDocumentation.Format.SHORT);
	}

}
