package com.tcoffman.ttwb.web.resource.model.pattern;

import com.tcoffman.ttwb.model.pattern.part.GameAnyPartPattern;
import com.tcoffman.ttwb.web.GameModelRepository;

public class AnyPartPatternResource extends PartPatternResource<GameAnyPartPattern> {

	public AnyPartPatternResource(GameModelRepository.Bundle modelBundle, GameAnyPartPattern pattern) {
		super(modelBundle, pattern);
	}

	public String getType() {
		return "ANY";
	}

	public String getLabel() {
		return "*";
	}

}
