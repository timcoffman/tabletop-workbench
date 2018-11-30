package com.tcoffman.ttwb.dao.data;

public class Execution {

	private String m_state;
	private long m_scenarioId;

	public Execution() {
	}

	public String getState() {
		return m_state;
	}

	public void setState(String state) {
		m_state = state;
	}

	public void setScenarioId(long scenarioId) {
		m_scenarioId = scenarioId;
	}

	public long getScenarioId() {
		return m_scenarioId;
	}

}
