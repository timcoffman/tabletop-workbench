package com.tcoffman.ttwb.dao.data;

import com.tcoffman.ttwb.service.api.data.StateResource;

public class State {

	private String m_stateUrl;
	private String m_model;
	private StateResource m_remoteState;

	public State() {

	}

	public String getStateUrl() {
		return m_stateUrl;
	}

	public void setStateUrl(String stateUrl) {
		m_stateUrl = stateUrl;
	}

	public String getModel() {
		return m_model;
	}

	public void setModel(String model) {
		m_model = model;
	}

	public StateResource getRemoteResource() {
		return m_remoteState;
	}

	public void setRemoteResource(StateResource remoteState) {
		m_remoteState = remoteState;
	}
}
