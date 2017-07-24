package com.tcoffman.ttwb.web.resource.model.pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.model.pattern.quantity.GameAnyQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameRangeQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameSingleQuantityPattern;
import com.tcoffman.ttwb.web.GameModelFileRepository;
import com.tcoffman.ttwb.web.resource.model.AbstractModelSubresource;

public class QuantityPatternResource extends AbstractModelSubresource {

	private final GameQuantityPattern m_pattern;

	public QuantityPatternResource(GameModelFileRepository.Bundle modelBundle, GameQuantityPattern pattern) {
		super(modelBundle);
		m_pattern = pattern;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public QuantityPatternResource get() {
		return this;
	}

	public String getLabel() {
		return m_pattern.toString();
	}

	public String getType() {
		return m_pattern.visit(new GameQuantityPattern.Visitor<String, RuntimeException>() {

			@Override
			public String visit(GameAnyQuantityPattern pattern) {
				return "ANY";
			}

			@Override
			public String visit(GameSingleQuantityPattern pattern) {
				return "SINGLE";
			}

			@Override
			public String visit(GameRangeQuantityPattern pattern) {
				return "RANGE";
			}
		});
	}

	public Long getMaximum() {
		return m_pattern.visit(new GameQuantityPattern.Visitor<Long, RuntimeException>() {

			@Override
			public Long visit(GameAnyQuantityPattern pattern) {
				return null;
			}

			@Override
			public Long visit(GameSingleQuantityPattern pattern) {
				return null;
			}

			@Override
			public Long visit(GameRangeQuantityPattern pattern) {
				return pattern.getMaximum().orElse(null);
			}
		});
	}

	public Long getMinimum() {
		return m_pattern.visit(new GameQuantityPattern.Visitor<Long, RuntimeException>() {

			@Override
			public Long visit(GameAnyQuantityPattern pattern) {
				return null;
			}

			@Override
			public Long visit(GameSingleQuantityPattern pattern) {
				return null;
			}

			@Override
			public Long visit(GameRangeQuantityPattern pattern) {
				return pattern.getMinimum().orElse(null);
			}
		});
	}
}
