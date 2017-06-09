package com.tcoffman.ttwb.web.resource.state.pattern;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.part.StandardAnyPartPattern;
import com.tcoffman.ttwb.model.pattern.part.StandardFilterPartPattern;
import com.tcoffman.ttwb.model.pattern.part.StandardIntersectionPartPattern;
import com.tcoffman.ttwb.model.pattern.part.StandardInversionPartPattern;
import com.tcoffman.ttwb.model.pattern.part.StandardVariablePartPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.model.pattern.place.StandardAnyPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.StandardFilterPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.StandardIntersectionPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.StandardInversionPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.StandardRelationshipPlacePattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.StandardAnyQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.StandardRangeQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.StandardSingleQuantityPattern;
import com.tcoffman.ttwb.web.UnrecognizedValueException;
import com.tcoffman.ttwb.web.resource.state.StateContextAwareResource;

public class PatternUtils {

	private final StateContextAwareResource m_stateContextAwareResource;

	public PatternUtils(StateContextAwareResource stateContextAwareResource) {
		m_stateContextAwareResource = stateContextAwareResource;
	}

	public static class PlacePatternForm {
		private String m_type;
		private PatternUtils.QuantityPatternForm m_quantity;
		private PatternUtils.PartPatternForm m_part;
		private List<PatternUtils.PlacePatternForm> m_patterns;
		private PatternUtils.PlacePatternForm m_pattern;
		private String m_placeType;
		private String m_role;

		private Boolean m_relExists;
		private Boolean m_relForward;
		private String m_relType;
		private PatternUtils.PlacePatternForm m_relatedPlace;

		public String getType() {
			return m_type;
		}

		public void setType(String typeId) {
			m_type = typeId;
		}

		public PatternUtils.PartPatternForm getPart() {
			return m_part;
		}

		public void setPart(PatternUtils.PartPatternForm part) {
			m_part = part;
		}

		public List<PatternUtils.PlacePatternForm> getPatterns() {
			return m_patterns;
		}

		public void setPatterns(List<PatternUtils.PlacePatternForm> patterns) {
			m_patterns = patterns;
		}

		public void setPattern(PatternUtils.PlacePatternForm pattern) {
			m_pattern = pattern;
		}

		public PatternUtils.PlacePatternForm getPattern() {
			return m_pattern;
		}

		public String getPlaceType() {
			return m_placeType;
		}

		public void setPlaceType(String placeTypeId) {
			m_placeType = placeTypeId;
		}

		public String getRole() {
			return m_role;
		}

		public void setRole(String roleId) {
			m_role = roleId;
		}

		public PatternUtils.QuantityPatternForm getQuantity() {
			return m_quantity;
		}

		public void setQuantity(PatternUtils.QuantityPatternForm quantityPattern) {
			m_quantity = quantityPattern;
		}

		public Boolean getForward() {
			return m_relForward;
		}

		public void setForward(Boolean relForward) {
			this.m_relForward = relForward;
		}

		public Boolean getExists() {
			return m_relExists;
		}

		public void setExists(Boolean relExists) {
			this.m_relExists = relExists;
		}

		public String getRelationshipType() {
			return m_relType;
		}

		public void setRelationshipType(String relType) {
			this.m_relType = relType;
		}

		public PatternUtils.PlacePatternForm getRelated() {
			return m_relatedPlace;
		}

		public void setRelated(PatternUtils.PlacePatternForm relatedPlace) {
			this.m_relatedPlace = relatedPlace;
		}

	}

	public static class QuantityPatternForm {
		private String m_type;
		private Integer m_max;
		private Integer m_min;

		public String getType() {
			return m_type;
		}

		public void setType(String typeId) {
			m_type = typeId;
		}

		public Integer getMax() {
			return m_max;
		}

		public void setMax(Integer max) {
			m_max = max;
		}

		public Integer getMin() {
			return m_min;
		}

		public void setMin(Integer min) {
			m_min = min;
		}

	}

	public static class PartPatternForm {
		private String m_type;
		private PatternUtils.QuantityPatternForm m_quantity;
		private List<PatternUtils.PartPatternForm> m_patterns;
		private PatternUtils.PartPatternForm m_pattern;
		private String m_prototype;
		private String m_role;

		public String getType() {
			return m_type;
		}

		public void setType(String typeId) {
			m_type = typeId;
		}

		public List<PatternUtils.PartPatternForm> getPatterns() {
			return m_patterns;
		}

		public void setPatterns(List<PatternUtils.PartPatternForm> patterns) {
			m_patterns = patterns;
		}

		public PatternUtils.PartPatternForm getPattern() {
			return m_pattern;
		}

		public void setPattern(PatternUtils.PartPatternForm pattern) {
			m_pattern = pattern;
		}

		public String getPrototype() {
			return m_prototype;
		}

		public void setPrototype(String prototypeId) {
			m_prototype = prototypeId;
		}

		public String getRole() {
			return m_role;
		}

		public void setRole(String roleId) {
			m_role = roleId;
		}

		public PatternUtils.QuantityPatternForm getQuantity() {
			return m_quantity;
		}

		public void setQuantity(PatternUtils.QuantityPatternForm quantityPattern) {
			m_quantity = quantityPattern;
		}

	}

	public Optional<GamePlacePattern> createPlacePattern(PlacePatternForm patternForm) throws UnrecognizedValueException, GameComponentBuilderException {
		if (null == patternForm)
			return Optional.empty();

		final Optional<GamePartPattern> partPattern = createPartPattern(patternForm.getPart());
		final Optional<GameQuantityPattern> quantityPattern = createQuantityPattern(patternForm.getQuantity());

		GamePlacePattern pattern;
		switch (patternForm.getType().toUpperCase()) {
		case "ANY":
			final StandardAnyPlacePattern.Editor anyEditor = StandardAnyPlacePattern.create();
			quantityPattern.ifPresent(anyEditor::setQuantityPattern);
			partPattern.ifPresent(anyEditor::setPartPattern);
			pattern = anyEditor.done();
			break;
		case "ALL":
			final StandardIntersectionPlacePattern.Editor allEditor = StandardIntersectionPlacePattern.create();
			if (null != patternForm.getPatterns())
				for (final PlacePatternForm form : patternForm.getPatterns())
					createPlacePattern(form).ifPresent(allEditor::addPattern);
			pattern = allEditor.done();
			break;
		case "NOT":
			final StandardInversionPlacePattern.Editor notEditor = StandardInversionPlacePattern.create();
			createPlacePattern(patternForm.getPattern()).ifPresent(notEditor::setPattern);
			pattern = notEditor.done();
			break;
		case "FILTER":
			final StandardFilterPlacePattern.Editor filterEditor = StandardFilterPlacePattern.create();
			quantityPattern.ifPresent(filterEditor::setQuantityPattern);
			partPattern.ifPresent(filterEditor::setPartPattern);
			createPlaceType(patternForm.getPlaceType()).ifPresent(filterEditor::setMatchType);
			createRole(patternForm.getRole()).ifPresent(filterEditor::setMatchBinding);
			pattern = filterEditor.done();
			break;
		case "REL":
			final StandardRelationshipPlacePattern.Editor relEditor = StandardRelationshipPlacePattern.create();
			quantityPattern.ifPresent(relEditor::setQuantityPattern);
			partPattern.ifPresent(relEditor::setPartPattern);
			if (null != patternForm.getExists())
				relEditor.setMatchExistence(patternForm.getExists());
			if (null != patternForm.getForward())
				relEditor.setMatchForward(patternForm.getForward());
			if (null != patternForm.getRelationshipType())
				relEditor.setMatchType(m_stateContextAwareResource.lookupRelationshipType(patternForm.getRelationshipType()));
			createPlacePattern(patternForm.getRelated()).ifPresent(relEditor::setRelatedPlacePattern);
			pattern = relEditor.done();
			break;
		default:
			throw new UnrecognizedValueException(patternForm.getType() + " not recognized", Stream.of("ANY", "ALL", "NOT", "FILTER", "REL"));
		}
		return Optional.of(pattern);
	}

	public Optional<GameComponentRef<GamePlaceType>> createPlaceType(String placeTypeId) throws UnrecognizedValueException {
		if (null == placeTypeId)
			return Optional.empty();
		final GameComponentRef<GamePlaceType> placeType = m_stateContextAwareResource.lookupPlaceType(placeTypeId);
		return Optional.of(placeType);
	}

	public Optional<GameComponentRef<GameRole>> createRole(String roleId) throws UnrecognizedValueException {
		if (null == roleId)
			return Optional.empty();
		final GameComponentRef<GameRole> role = m_stateContextAwareResource.lookupRole(roleId);
		return Optional.of(role);
	}

	public Optional<GamePartPattern> createPartPattern(PartPatternForm patternForm) throws UnrecognizedValueException, GameComponentBuilderException {
		if (null == patternForm)
			return Optional.empty();

		final Optional<GameQuantityPattern> quantityPattern = createQuantityPattern(patternForm.getQuantity());

		GamePartPattern pattern;
		switch (patternForm.getType()) {
		case "ANY":
			final StandardAnyPartPattern.Editor anyEditor = StandardAnyPartPattern.create();
			quantityPattern.ifPresent(anyEditor::setQuantityPattern);
			pattern = anyEditor.done();
			break;
		case "ALL":
			final StandardIntersectionPartPattern.Editor allEditor = StandardIntersectionPartPattern.create();
			if (null != patternForm.getPatterns())
				for (final PartPatternForm form : patternForm.getPatterns())
					allEditor.addPattern(createPartPattern(form).get());
			pattern = allEditor.done();
			break;
		case "NOT":
			final StandardInversionPartPattern.Editor notEditor = StandardInversionPartPattern.create();
			createPartPattern(patternForm.getPattern()).ifPresent(notEditor::setPattern);
			pattern = notEditor.done();
			break;
		case "FILTER":
			final StandardFilterPartPattern.Editor filterEditor = StandardFilterPartPattern.create();
			quantityPattern.ifPresent(filterEditor::setQuantityPattern);
			createPartPrototype(patternForm.getPrototype()).ifPresent(filterEditor::setMatchPrototype);
			createRole(patternForm.getRole()).ifPresent(filterEditor::setMatchBinding);
			pattern = filterEditor.done();
			break;
		case "ROOT":
			pattern = StandardVariablePartPattern.create().done();
			break;
		default:
			throw new UnrecognizedValueException(patternForm.getType() + " not recognized", Stream.of("ANY", "ALL", "FILTER", "ROOT"));
		}
		return Optional.of(pattern);
	}

	public Optional<GameComponentRef<GamePartPrototype>> createPartPrototype(String prototypeId) throws UnrecognizedValueException {
		if (null == prototypeId)
			return Optional.empty();
		final GameComponentRef<GamePartPrototype> role = m_stateContextAwareResource.lookupPartPrototype(prototypeId);
		return Optional.of(role);
	}

	public Optional<GameQuantityPattern> createQuantityPattern(QuantityPatternForm patternForm) throws GameComponentBuilderException,
			UnrecognizedValueException {
		if (null == patternForm)
			return Optional.empty();
		GameQuantityPattern pattern;
		switch (patternForm.getType()) {
		case "ANY":
			pattern = StandardAnyQuantityPattern.create().done();
			break;
		case "SINGLE":
			pattern = StandardSingleQuantityPattern.create().done();
			break;
		case "RANGE":
			final StandardRangeQuantityPattern.Editor editor = StandardRangeQuantityPattern.create();
			if (null != patternForm.getMax())
				editor.setMaximum(patternForm.getMax());
			if (null != patternForm.getMin())
				editor.setMinimum(patternForm.getMin());
			pattern = editor.done();
			break;
		default:
			throw new UnrecognizedValueException(patternForm.getType() + " not recognized", Stream.of("ANY", "SINGLE", "RANGE"));
		}
		return Optional.of(pattern);
	}

}