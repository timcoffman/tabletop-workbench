package com.tcoffman.ttwb.scenario.engine;

import com.tcoffman.ttwb.dao.StateDao;
import com.tcoffman.ttwb.service.api.data.OperationPatternSetResource;
import com.tcoffman.ttwb.service.api.data.StateResource;

public class StateEngine {

	private final String m_stateUrl;
	private final StateDao m_stateDao;
	private final StateResource m_remoteState;

	public StateEngine(StateDao stateDao, String stateUrl, StateResource remoteState) {
		super();
		m_stateDao = stateDao;
		m_stateUrl = stateUrl;
		m_remoteState = remoteState;
	}

	public boolean advance() {
		for (final OperationPatternSetResource operationPatternSet : m_remoteState.getAllowedOperations().getOperationPatternSets()) {
			System.out.print("operationPatternSet: ");
			System.out.println(operationPatternSet);
		}
		return false;
	}

}
