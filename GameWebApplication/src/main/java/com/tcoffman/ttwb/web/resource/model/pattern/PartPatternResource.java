package com.tcoffman.ttwb.web.resource.model.pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.model.pattern.part.GameAnyPartPattern;
import com.tcoffman.ttwb.model.pattern.part.GameFilterPartPattern;
import com.tcoffman.ttwb.model.pattern.part.GameIntersectionPartPattern;
import com.tcoffman.ttwb.model.pattern.part.GameInversionPartPattern;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.part.GameVariablePartPattern;
import com.tcoffman.ttwb.web.GameModelFileRepository;
import com.tcoffman.ttwb.web.resource.model.AbstractModelSubresource;

public abstract class PartPatternResource<T extends GamePartPattern> extends AbstractModelSubresource {

	protected final T m_pattern;

	public PartPatternResource(GameModelFileRepository.Bundle modelBundle, T pattern) {
		super(modelBundle);
		m_pattern = pattern;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public PartPatternResource<T> get() {
		return this;
	}

	public abstract String getType();

	public static PartPatternResource<?> createUninitializedPartPattern(GameModelFileRepository.Bundle modelBundle, GamePartPattern pattern) {
		return pattern.visit(new GamePartPattern.Visitor<PartPatternResource<?>, RuntimeException>() {

			@Override
			public PartPatternResource<?> visit(GameAnyPartPattern pattern) throws RuntimeException {
				return new AnyPartPatternResource(modelBundle, pattern);
			}

			@Override
			public PartPatternResource<?> visit(GameVariablePartPattern pattern) throws RuntimeException {
				return new RootPartPatternResource(modelBundle, pattern);
			}

			@Override
			public PartPatternResource<?> visit(GameFilterPartPattern pattern) throws RuntimeException {
				return new FilterPartPatternResource(modelBundle, pattern);
			}

			@Override
			public PartPatternResource<?> visit(GameIntersectionPartPattern pattern) throws RuntimeException {
				return new IntersectionPartPatternResource(modelBundle, pattern);
			}

			@Override
			public PartPatternResource<?> visit(GameInversionPartPattern pattern) throws RuntimeException {
				return new InversionPartPatternResource(modelBundle, pattern);
			}
		});
	}

}
