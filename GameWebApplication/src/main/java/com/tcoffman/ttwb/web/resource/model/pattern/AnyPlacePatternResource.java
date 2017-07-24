package com.tcoffman.ttwb.web.resource.model.pattern;

import com.tcoffman.ttwb.model.pattern.place.GameAnyPlacePattern;
import com.tcoffman.ttwb.web.GameModelFileRepository;

public class AnyPlacePatternResource extends PlacePatternResource<GameAnyPlacePattern> {

	public AnyPlacePatternResource(GameModelFileRepository.Bundle modelBundle, GameAnyPlacePattern pattern) {
		super(modelBundle, pattern);
	}

	public String getType() {
		return "ANY";
	}

	public String getLabel() {
		return "*";
	}

}
