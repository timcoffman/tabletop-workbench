package com.tcoffman.ttwb.web.resource.model.pattern;

import com.tcoffman.ttwb.model.pattern.place.GameRelationshipPlacePattern;
import com.tcoffman.ttwb.web.GameModelRepository;

public class RelationshipPlacePatternResource extends PlacePatternResource<GameRelationshipPlacePattern> {

	public RelationshipPlacePatternResource(GameModelRepository.Bundle modelBundle, GameRelationshipPlacePattern pattern) {
		super(modelBundle, pattern);
	}

	@Override
	public String getType() {
		return "REL";
	}

	@Override
	public String getLabel() {
		return this.toString();
	}

}
