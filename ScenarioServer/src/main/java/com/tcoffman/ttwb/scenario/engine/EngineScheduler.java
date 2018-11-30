package com.tcoffman.ttwb.scenario.engine;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.tcoffman.ttwb.dao.ExecutionDao;
import com.tcoffman.ttwb.dao.StateDao;
import com.tcoffman.ttwb.dao.data.Execution;
import com.tcoffman.ttwb.dao.data.State;

public class EngineScheduler {

	private final ScheduledExecutorService m_scheduledExecutorService;
	private final ExecutionDao m_executionDao;
	private final StateDao m_stateDao;
	private ScheduledFuture<?> m_scheduledTask;

	public EngineScheduler(ExecutionDao executionDao, StateDao stateDao) {
		m_executionDao = executionDao;
		m_stateDao = stateDao;
		m_scheduledExecutorService = Executors.newScheduledThreadPool(3);
	}

	public void start() {
		if (isRunning())
			throw new IllegalStateException("already running");
		m_scheduledTask = m_scheduledExecutorService.scheduleAtFixedRate(new ExecutionRunner(m_executionDao, m_stateDao), 3, 1, TimeUnit.SECONDS);
	}

	public boolean isRunning() {
		return null != m_scheduledTask && !m_scheduledTask.isDone();
	}

	public void stop() {
		if (!isRunning())
			throw new IllegalStateException("not running");
		m_scheduledTask.cancel(false);
	}

	public void waitStop() {
		if (isRunning())
			m_scheduledTask.cancel(false);
	}

	private static class ExecutionRunner implements Runnable {
		private final ExecutionDao m_executionDao;
		private final StateDao m_stateDao;

		public ExecutionRunner(ExecutionDao executionDao, StateDao stateDao) {
			m_executionDao = executionDao;
			m_stateDao = stateDao;
		}

		@Override
		public void run() {
			try {
				m_executionDao.anyWithStatus("OK").ifPresent(this::runExecution);
				final int count = m_executionDao.all().size();
				System.out.println("running (" + count + ")");
			} catch (final Throwable ex) {
				System.out.println("running (" + ex.getMessage() + ")");
			}
		}

		private void runExecution(long executionId) {
			try {
				if (m_executionDao.acquire(executionId, "OK", "WORKING")) {
					final boolean finished = m_executionDao.lookup(executionId).map(this::runExecution).orElseThrow(IllegalArgumentException::new);
					m_executionDao.release(executionId, "WORKING", finished ? "DONE" : "OK");
				}
			} finally {
				m_executionDao.release(executionId, "WORKING", "FAIL");
			}
		}

		private boolean runExecution(Execution execution) {
			return m_stateDao.lookup(execution.getState()).map(this::advanceExecution).orElseThrow(IllegalStateException::new);
		}

		private boolean advanceExecution(State state) {
			System.out.println("advancing state (" + state.getStateUrl() + ")");
			final StateEngine stateEngine = new StateEngine(m_stateDao, state.getStateUrl(), state.getRemoteResource());
			return stateEngine.advance();
		}

	}

}
