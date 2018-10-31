package com.tcoffman.ttwb.web.resource.model.pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;
import com.tcoffman.ttwb.web.GameModelFileRepository;
import com.tcoffman.ttwb.web.resource.model.AbstractModelSubresource;

public class OperationPatternResource extends AbstractModelSubresource {

	private final GameOperationPattern m_opPattern;

	public OperationPatternResource(GameModelFileRepository.Bundle modelBundle, GameOperationPattern opPattern) {
		super(modelBundle);
		m_opPattern = opPattern;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@JsonIgnore
	public OperationPatternResource getPatternResource() {
		return this;
	}

	public String getDescription() {
		return m_opPattern.getDocumentation().getDescription();
	}

	public String getType() {
		return m_opPattern.getType().name();
	}

	@Path("/subject")
	public PlacePatternResource<?> getSubject() {
		return m_opPattern.getSubjectPlacePattern().map(this::createPlacePatternResource).orElse(null);
	}

	@Path("/target")
	public PlacePatternResource<?> getTarget() {
		return m_opPattern.getTargetPlacePattern().map(this::createPlacePatternResource).orElse(null);
	}

	// @Path("/quantity")
	// public QuantityPatternResource getQuantity() {
	// return
	// m_opPattern.getQuantityPattern().map(this::createQuantityPatternResource).orElse(null);
	// }

	private PlacePatternResource<?> createPlacePatternResource(GamePlacePattern pattern) {
		return subresource(PlacePatternResource.createUninitializedPlacePattern(modelBundle(), pattern));
	}

	private QuantityPatternResource createQuantityPatternResource(GameQuantityPattern pattern) {
		return subresource(new QuantityPatternResource(modelBundle(), pattern));
	}

}
