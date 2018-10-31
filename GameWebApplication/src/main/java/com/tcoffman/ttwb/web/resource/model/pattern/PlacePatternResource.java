package com.tcoffman.ttwb.web.resource.model.pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.place.GameAnyPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GameFilterPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GameIntersectionPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GameInversionPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GameRelationshipPlacePattern;
import com.tcoffman.ttwb.web.GameModelFileRepository;
import com.tcoffman.ttwb.web.resource.model.AbstractModelSubresource;

public abstract class PlacePatternResource<T extends GamePlacePattern> extends AbstractModelSubresource {

	protected final T m_pattern;

	public PlacePatternResource(GameModelFileRepository.Bundle modelBundle, T pattern) {
		super(modelBundle);
		m_pattern = pattern;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@JsonIgnore
	public PlacePatternResource<T> getPlacePattern() {
		return this;
	}

	public abstract String getType();

	public abstract String getLabel();

	public PartPatternResource<?> getPart() {
		return m_pattern.visit(new GamePlacePattern.Visitor<PartPatternResource<?>, RuntimeException>() {

			@Override
			public PartPatternResource<?> visit(GameAnyPlacePattern pattern) throws RuntimeException {
				return createPartPatternResource(pattern.getPartPattern());
			}

			@Override
			public PartPatternResource<?> visit(GameFilterPlacePattern pattern) throws RuntimeException {
				return createPartPatternResource(pattern.getPartPattern());
			}

			@Override
			public PartPatternResource<?> visit(GameIntersectionPlacePattern pattern) throws RuntimeException {
				return null;
			}

			@Override
			public PartPatternResource<?> visit(GameInversionPlacePattern pattern) throws RuntimeException {
				return null;
			}

			@Override
			public PartPatternResource<?> visit(GameRelationshipPlacePattern pattern) throws RuntimeException {
				return createPartPatternResource(pattern.getPartPattern());
			}
		});
	}

	private PartPatternResource<?> createPartPatternResource(GamePartPattern pattern) {
		return subresource(PartPatternResource.createUninitializedPartPattern(modelBundle(), pattern));
	}

	public static PlacePatternResource<?> createUninitializedPlacePattern(GameModelFileRepository.Bundle modelBundle, GamePlacePattern pattern) {
		return pattern.visit(new GamePlacePattern.Visitor<PlacePatternResource<?>, RuntimeException>() {

			@Override
			public PlacePatternResource<?> visit(GameAnyPlacePattern pattern) throws RuntimeException {
				return new AnyPlacePatternResource(modelBundle, pattern);
			}

			@Override
			public PlacePatternResource<?> visit(GameFilterPlacePattern pattern) throws RuntimeException {
				return new FilterPlacePatternResource(modelBundle, pattern);
			}

			@Override
			public PlacePatternResource<?> visit(GameIntersectionPlacePattern pattern) throws RuntimeException {
				return new IntersectionPlacePatternResource(modelBundle, pattern);
			}

			@Override
			public PlacePatternResource<?> visit(GameInversionPlacePattern pattern) throws RuntimeException {
				return new InversionPlacePatternResource(modelBundle, pattern);
			}

			@Override
			public PlacePatternResource<?> visit(GameRelationshipPlacePattern pattern) throws RuntimeException {
				return new RelationshipPlacePatternResource(modelBundle, pattern);
			}
		});
	}

}
