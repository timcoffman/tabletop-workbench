package com.tcoffman.ttwb.scenario.engine;

import com.tcoffman.ttwb.dao.ExecutionDao;
import com.tcoffman.ttwb.dao.ModelDao;
import com.tcoffman.ttwb.dao.ScenarioDao;
import com.tcoffman.ttwb.dao.StateDao;
import com.tcoffman.ttwb.dao.data.Execution;
import com.tcoffman.ttwb.dao.data.Model;
import com.tcoffman.ttwb.dao.data.Role;
import com.tcoffman.ttwb.dao.data.Scenario;
import com.tcoffman.ttwb.dao.data.State;

public class ScenarioRunner {

	private final long m_scenarioId;
	private final Scenario m_scenario;
	private final Model m_model;

	private StateDao m_stateDao;
	private ModelDao m_modelDao;
	private ScenarioDao m_scenarioDao;
	private ExecutionDao m_executionDao;

	public ScenarioRunner(long scenarioId, Scenario scenario, Model model) {
		m_scenarioId = scenarioId;
		m_scenario = scenario;
		m_model = model;
	}

	public void setStateDao(StateDao stateDao) {
		m_stateDao = stateDao;
	}

	public void setModelDao(ModelDao modelDao) {
		m_modelDao = modelDao;
	}

	public void setScenarioDao(ScenarioDao scenarioDao) {
		m_scenarioDao = scenarioDao;
	}

	public void setExecutionDao(ExecutionDao executionDao) {
		m_executionDao = executionDao;
	}

	public void run() {
		int count;
		try {
			count = Integer.parseInt(m_scenario.getExecutionCount());
		} catch (final NumberFormatException ex) {
			count = 1;
		}

		for (int i = 0; i < count; ++i) {
			final State state = m_stateDao.create(m_scenario.getModel());
			// for (final Role role :
			// m_stateDao.lookupRoles(state.getStateUrl()))
			// m_stateDao.bindParticipant(state, role,
			// authorizationForRole(role));
			final Execution execution = new Execution();
			execution.setScenarioId(m_scenarioId);
			execution.setState(state.getStateUrl());
			final long executionId = m_executionDao.create(execution);
			System.out.println("created execution (" + executionId + ")");
		}
	}

	private String authorizationForRole(Role role) {
		return "SCENARIO(" + role.getName() + ")";
	}

}
