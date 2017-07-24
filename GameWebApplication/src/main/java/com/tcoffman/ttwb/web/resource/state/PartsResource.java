package com.tcoffman.ttwb.web.resource.state;

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
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.web.GameStateFileRepository;
import com.tcoffman.ttwb.web.UnrecognizedValueException;

public class PartsResource extends AbstractStateSubresource {

	public PartsResource(GameStateFileRepository.Bundle stateBundle) {
		super(stateBundle);
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return StatesResource.pathTo(uriBuilder).path(StateResource.class, "getParts").path(PartsResource.class, "getPart");
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<PartResource> getParts() {
		final List<PartResource> parts = new ArrayList<>();
		for (final Iterator<? extends GamePart> i = stateBundle().getState().parts().iterator(); i.hasNext();)
			try {
				final GamePart part = i.next();
				parts.add(createPartResource(requireId(part), part));
			} catch (final GameComponentBuilderException ex) {
				// don't add
				ex.printStackTrace();
			}
		return parts;
	}

	@Path("/{partId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public PartResource getPart(@PathParam("partId") String partId) throws UnrecognizedValueException {
		return createPartResource(partId, lookupPart(partId).get());
	}

	private PartResource createPartResource(String partId, GamePart part) {
		return subresource(new PartResource(stateBundle(), partId, part));
	}

}