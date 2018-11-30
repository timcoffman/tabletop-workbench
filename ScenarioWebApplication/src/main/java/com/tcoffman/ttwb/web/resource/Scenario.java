package com.tcoffman.ttwb.web.resource;

public class Scenario {

	private long m_id;
	private String m_model;
	private String m_description;
	private String m_participantCountMin;
	private String m_participantCountMax;
	private String m_executionCount;
	private String m_termination;

	public Scenario() {
	}

	public Scenario(long id, String model) {
		m_id = id;
		m_model = model;
	}

	public long getId() {
		return m_id;
	}

	public void setId(long id) {
		m_id = id;
	}

	public void setModel(String model) {
		m_model = model;
	}

	public String getModel() {
		return m_model;
	}

	public String getDescription() {
		return m_description;
	}

	public void setDescription(String description) {
		m_description = description;
	}

	public void setParticipantCountMin(String participantCountMin) {
		m_participantCountMin = participantCountMin;
	}

	public void setParticipantCountMax(String participantCountMax) {
		m_participantCountMax = participantCountMax;
	}

	public String getParticipantCountMin() {
		return m_participantCountMin;
	}

	public String getParticipantCountMax() {
		return m_participantCountMax;
	}

	public String getExecutionCount() {
		return m_executionCount;
	}

	public void setExecutionCount(String executionCount) {
		m_executionCount = executionCount;
	}

	public String getTermination() {
		return m_termination;
	}

	public void setTermination(String termination) {
		m_termination = termination;
	}

}
