package com.tcoffman.ttwb.web.resource.model.pattern;

import com.tcoffman.ttwb.model.pattern.place.GameInversionPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.web.GameModelFileRepository;

public class InversionPlacePatternResource extends PlacePatternResource<GameInversionPlacePattern> {

	public InversionPlacePatternResource(GameModelFileRepository.Bundle modelBundle, GameInversionPlacePattern pattern) {
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
