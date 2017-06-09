package com.tcoffman.ttwb.model.pattern.quantity;


public class QuantityPatternFormatter {

	public static QuantityPatternFormatter create() {
		return new QuantityPatternFormatter();
	}

	private int m_conditionCount = 0;

	private final StringBuffer m_buffer = new StringBuffer();

	public QuantityPatternFormatter() {
	}

	private QuantityPatternFormatter append(Object obj) {
		if (m_buffer.length() != 0)
			m_buffer.append(" ");
		m_buffer.append(obj.toString());
		return this;
	}

	@Override
	public String toString() {
		return m_buffer.toString();
	}

	public QuantityPatternFormatter matchesRange(Long min, Long max) {
		if (null == min && null == max)
			return this;

		if (0 != m_conditionCount++)
			append("AND");

		if (null == max) {
			append("AT LEAST");
			append(min.toString());
		} else if (null == min) {
			append("NO MORE THAN");
			append(max.toString());
		} else if (min == max)
			append(min.toString());
		else {
			append(min.toString());
			append("...");
			append(max.toString());
		}
		return this;
	}

}
