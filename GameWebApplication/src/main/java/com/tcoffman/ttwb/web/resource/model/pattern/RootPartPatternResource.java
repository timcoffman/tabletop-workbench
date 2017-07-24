package com.tcoffman.ttwb.web.resource.model.pattern;

import com.tcoffman.ttwb.model.pattern.part.GameVariablePartPattern;
import com.tcoffman.ttwb.web.GameModelFileRepository;

public class RootPartPatternResource extends PartPatternResource<GameVariablePartPattern> {

	public RootPartPatternResource(GameModelFileRepository.Bundle modelBundle, GameVariablePartPattern pattern) {
		super(modelBundle, pattern);
	}

	@Override
	public String getType() {
		return "ROOT";
	}

	public String getLabel() {
		return "ROOT";
	}

}
