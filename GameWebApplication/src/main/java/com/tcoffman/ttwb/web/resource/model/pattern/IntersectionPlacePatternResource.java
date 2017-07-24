package com.tcoffman.ttwb.web.resource.model.pattern;

import java.util.List;
import java.util.stream.Collectors;

import com.tcoffman.ttwb.model.pattern.place.GameIntersectionPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.web.GameModelFileRepository;

public class IntersectionPlacePatternResource extends PlacePatternResource<GameIntersectionPlacePattern> {

	public IntersectionPlacePatternResource(GameModelFileRepository.Bundle modelBundle, GameIntersectionPlacePattern pattern) {
		super(modelBundle, pattern);
	}

	@Override
	public String getType() {
		return "ALL";
	}

	public String getLabel() {
		return Integer.toString(m_pattern.countPatterns()) + " pattern(s)";
	}

	public List<PlacePatternResource<?>> getPatterns() {
		return m_pattern.patterns().map(this::createPlacePatternResource).collect(Collectors.toList());
	}

	private PlacePatternResource<?> createPlacePatternResource(GamePlacePattern pattern) {
		return subresource(PlacePatternResource.createUninitializedPlacePattern(modelBundle(), pattern));
	}

}
