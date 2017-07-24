package com.tcoffman.ttwb.web.resource.model.pattern;

import com.tcoffman.ttwb.model.pattern.part.GameInversionPartPattern;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.web.GameModelFileRepository;

public class InversionPartPatternResource extends PartPatternResource<GameInversionPartPattern> {

	public InversionPartPatternResource(GameModelFileRepository.Bundle modelBundle, GameInversionPartPattern pattern) {
		super(modelBundle, pattern);
	}

	@Override
	public String getType() {
		return "NOT";
	}

	public String getLabel() {
		return "invert pattern";
	}

	public PartPatternResource<?> getPattern() {
		return createPartPatternResource(m_pattern);
	}

	private PartPatternResource<?> createPartPatternResource(GamePartPattern pattern) {
		return subresource(PartPatternResource.createUninitializedPartPattern(modelBundle(), pattern));
	}

}
