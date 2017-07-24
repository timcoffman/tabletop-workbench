package com.tcoffman.ttwb.web;

import org.junit.Before;
import org.junit.Test;

import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.state.GameRunner;

public class GameRunnerTest {

	private GameModelFileRepository m_modelRepository;
	private GameStateFileRepository m_stateRepository;

	@Before
	public void setupRepositories() {
		m_modelRepository = new GameModelFileRepository();
		m_stateRepository = new GameStateFileRepository();
	}

	@Test
	public void test() throws PluginException {
		final GameModelFileRepository.Bundle modelBundle = m_modelRepository.getBundle("complete");
		final GameStateFileRepository.Bundle stateBundle = m_stateRepository.create(modelBundle.getModel(), modelBundle.getModelId(), modelBundle.getPluginSet(),
				modelBundle.getModelRefResolver());

		final GameRunner runner = new GameRunner(stateBundle.getState());
		System.out.println(runner.autoAdvance());
	}

}
