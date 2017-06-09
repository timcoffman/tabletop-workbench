package com.tcoffman.ttwb.web.resource.state;

import java.net.URI;

import javax.ws.rs.Path;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.web.GameStateRepository;
import com.tcoffman.ttwb.web.resource.model.PartPrototypesResource;

public class PartResource extends AbstractStateSubresource {

	private final String m_partId;
	private final GamePart m_part;

	public PartResource(GameStateRepository.Bundle stateBundle, String partId, GamePart part) {
		super(stateBundle);
		m_partId = partId;
		m_part = part;
	}

	public URI getPrototypeResource() {
		try {
			return PartPrototypesResource.pathTo(baseUriBuilder()).build(stateBundle().getModelId(), lookupPartPrototypeId(m_part.getPrototype()));
		} catch (final GameComponentBuilderException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public URI getResource() {
		return PartsResource.pathTo(baseUriBuilder()).build(stateBundle().getStateId(), m_partId);
	}

	public String getLabel() {
		return m_part.getPrototype().get().getDocumentation().getName(GameComponentDocumentation.Format.SHORT);
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/places")
	public PlacesResource getPlaces() throws GameComponentBuilderException {
		return subresource(new PlacesResource(stateBundle(), m_partId, m_part));
	}

}
