package com.tcoffman.ttwb.model.pattern.place;

import java.util.Optional;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;

public class PlacePatternFormatter {

	public static PlacePatternFormatter create() {
		return new PlacePatternFormatter();
	}

	private int m_conditionCount = 0;

	private final StringBuffer m_buffer = new StringBuffer();

	public PlacePatternFormatter() {
		append("PLACE");
	}

	private PlacePatternFormatter append(Object obj) {
		if (null != obj) {
			final String str = obj.toString();
			if (!str.isEmpty()) {
				if (m_buffer.length() != 0)
					m_buffer.append(" ");
				m_buffer.append(str);
			}
		}
		return this;
	}

	private PlacePatternFormatter prepend(Object obj) {
		if (null != obj) {
			final String str = obj.toString();
			if (!str.isEmpty()) {
				if (m_buffer.length() != 0)
					m_buffer.insert(0, " ");
				m_buffer.insert(0, str);
			}
		}
		return this;
	}

	@Override
	public String toString() {
		return m_buffer.toString();
	}

	public PlacePatternFormatter matchesQuantity(GameQuantityPattern quantityPattern) {
		return prepend(quantityPattern);
	}

	public PlacePatternFormatter matchesPart(GamePartPattern partPattern) {
		return append("ON:(").append(partPattern.toString()).append(")");
	}

	public PlacePatternFormatter equalProperty(String propertyName, Object propertyValue) {
		if (0 == m_conditionCount++)
			append("WHERE");
		else
			append("AND");
		append(propertyName);
		append("EQUALS");
		append(propertyValue);
		return this;
	}

	public PlacePatternFormatter matchesRelationship(boolean matchesExistence, boolean matchesForward,
			Optional<GameComponentRef<GamePartRelationshipType>> matchRelationshipType, Optional<GamePlacePattern> relatedPlacePattern) {
		if (0 == m_conditionCount++)
			append("WHERE");
		else
			append("AND");
		append(matchesExistence ? "IS" : "IS NOT");
		append(matchesForward ? "SOURCE" : "DESTINATION");
		append("OF");
		matchRelationshipType.ifPresent((p) -> append(p.get()));
		append("RELATIONSHIP");
		relatedPlacePattern.ifPresent((p) -> {
			append("WHERE");
			append(matchesForward ? "DESTINATION" : "SOURCE");
			append(p);
		});
		return this;
	}

	public PlacePatternFormatter matchPattern(GamePlacePattern p) {
		if (0 == m_conditionCount++)
			append("WHERE");
		else
			append("AND");
		append("(");
		append(p);
		append(")");
		return this;
	}

	public PlacePatternFormatter matchNegatedPattern(GamePlacePattern p) {
		if (0 == m_conditionCount++)
			append("WHERE");
		else
			append("AND");
		append("NOT (");
		append(p);
		append(")");
		return this;
	}

	public PlacePatternFormatter matchNegatedPattern(GamePartPattern p) {
		if (0 == m_conditionCount++)
			append("WHERE");
		else
			append("AND");
		append("NOT");
		append("(");
		append(p);
		append(")");
		return this;
	}

}
