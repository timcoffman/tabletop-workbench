package com.tcoffman.ttwb.web.resource.model.pattern;

import java.net.URI;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.pattern.place.GameFilterPlacePattern;
import com.tcoffman.ttwb.web.GameModelRepository;
import com.tcoffman.ttwb.web.resource.model.RolesResource;
import com.tcoffman.ttwb.web.resource.model.plugin.PlaceTypesResource;

public class FilterPlacePatternResource extends PlacePatternResource<GameFilterPlacePattern> {

	public FilterPlacePatternResource(GameModelRepository.Bundle modelBundle, GameFilterPlacePattern pattern) {
		super(modelBundle, pattern);
	}

	@Override
	public String getType() {
		return "FILTER";
	}

	@Override
	public String getLabel() {
		return m_pattern.toString();
	}

	public URI getMatchesType() {
		if (!m_pattern.getMatchesType().isPresent())
			return null;
		final GamePlaceType placeType = m_pattern.getMatchesType().get().get();
		return PlaceTypesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), placeType.getDeclaringPlugin().toURI(), placeType.getLocalName());
	}

	public URI getMatchesRole() throws GameComponentBuilderException {
		if (!m_pattern.getMatchesRoleBinding().isPresent())
			return null;
		return RolesResource.pathTo(baseUriBuilder()).build(modelBundle().getModelId(), lookupRoleId(m_pattern.getMatchesRoleBinding().get()));
	}
}
