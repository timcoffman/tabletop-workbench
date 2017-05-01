package com.tcoffman.ttwb.state.pattern;

import com.tcoffman.ttwb.model.pattern.GamePartPattern;

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
		if (m_buffer.length() != 0)
			m_buffer.append(" ");
		m_buffer.append(obj.toString());
		return this;
	}

	@Override
	public String toString() {
		return m_buffer.toString();
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

}
