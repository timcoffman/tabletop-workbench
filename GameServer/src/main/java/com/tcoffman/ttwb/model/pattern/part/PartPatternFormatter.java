package com.tcoffman.ttwb.model.pattern.part;

import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;

public class PartPatternFormatter {

	public static PartPatternFormatter create() {
		return new PartPatternFormatter();
	}

	private int m_conditionCount = 0;

	private final StringBuffer m_buffer = new StringBuffer();

	public PartPatternFormatter() {
		append("PART");
	}

	private PartPatternFormatter append(Object obj) {
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

	private PartPatternFormatter prepend(Object obj) {
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

	public PartPatternFormatter matchesQuantity(GameQuantityPattern quantityPattern) {
		return prepend(quantityPattern);
	}

	public PartPatternFormatter equalProperty(String propertyName, Object propertyValue) {
		if (0 == m_conditionCount++)
			append("WHERE");
		else
			append("AND");
		append(propertyName);
		append("EQUALS");
		append(propertyValue);
		return this;
	}

	public PartPatternFormatter matchPattern(GamePartPattern p) {
		if (0 == m_conditionCount++)
			append("WHERE");
		else
			append("AND");
		append("(");
		append(p.toString());
		append(")");
		return this;
	}

	public PartPatternFormatter matchNegatedPattern(GamePartPattern p) {
		if (0 == m_conditionCount++)
			append("WHERE");
		else
			append("AND");
		append("NOT");
		append("(");
		append(p.toString());
		append(")");
		return this;
	}

}
