package com.tcoffman.ttwb.web.resource.model.pattern;

import java.net.URI;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.pattern.part.GameFilterPartPattern;
import com.tcoffman.ttwb.web.GameModelRepository;
import com.tcoffman.ttwb.web.resource.model.PartPrototypesResource;
import com.tcoffman.ttwb.web.resource.model.RolesResource;

public class FilterPartPatternResource extends PartPatternResource<GameFilterPartPattern> {

	public FilterPartPatternResource(GameModelRepository.Bundle modelBundle, GameFilterPartPattern pattern) {
		super(modelBundle, pattern);
	}

	@Override
	public String getType() {
		return "FILTER";
	}

	public String getLabel() {
		return m_pattern.toString();
	}

	public URI getMatchesPrototype() throws GameComponentBuilderException {
		if (!m_pattern.getMatchesPrototype().isPresent())
			return null;
		return PartPrototypesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), lookupPartPrototypeId(m_pattern.getMatchesPrototype().get()));
	}

	public String getMatchesRole() throws GameComponentBuilderException {
		if (!m_pattern.getMatchesRoleBinding().isPresent())
			return null;
		return RolesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), lookupRoleId(m_pattern.getMatchesRoleBinding().get())).toString();
	}

}
