package com.tcoffman.ttwb.web.resource.model.pattern;

import java.util.List;
import java.util.stream.Collectors;

import com.tcoffman.ttwb.model.pattern.part.GameIntersectionPartPattern;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.web.GameModelRepository;

public class IntersectionPartPatternResource extends PartPatternResource<GameIntersectionPartPattern> {

	public IntersectionPartPatternResource(GameModelRepository.Bundle modelBundle, GameIntersectionPartPattern pattern) {
		super(modelBundle, pattern);
	}

	@Override
	public String getType() {
		return "ALL";
	}

	public String getLabel() {
		return Integer.toString(m_pattern.countPatterns()) + " pattern(s)";
	}

	public List<PartPatternResource<?>> getPatterns() {
		return m_pattern.patterns().map(this::createPartPatternResource).collect(Collectors.toList());
	}

	private PartPatternResource<?> createPartPatternResource(GamePartPattern pattern) {
		return subresource(PartPatternResource.createUninitializedPartPattern(modelBundle(), pattern));
	}

}
