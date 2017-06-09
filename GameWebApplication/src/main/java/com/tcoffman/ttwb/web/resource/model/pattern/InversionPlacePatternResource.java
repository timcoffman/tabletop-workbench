package com.tcoffman.ttwb.web.resource.model.pattern;

import com.tcoffman.ttwb.model.pattern.place.GameInversionPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.web.GameModelRepository;

public class InversionPlacePatternResource extends PlacePatternResource<GameInversionPlacePattern> {

	public InversionPlacePatternResource(GameModelRepository.Bundle modelBundle, GameInversionPlacePattern pattern) {
		super(modelBundle, pattern);
	}

	@Override
	public String getType() {
		return "NOT";
	}

	@Override
	public String getLabel() {
		return "invert pattern";
	}

	public PlacePatternResource<?> getPattern() {
		return createPlacePatternResource(m_pattern.getPattern());
	}

	private PlacePatternResource<?> createPlacePatternResource(GamePlacePattern pattern) {
		return subresource(PlacePatternResource.createUninitializedPlacePattern(modelBundle(), pattern));
	}

}
