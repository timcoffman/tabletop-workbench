package com.tcoffman.ttwb.web;

import org.glassfish.hk2.api.PostConstruct;
import org.glassfish.hk2.api.PreDestroy;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import com.tcoffman.ttwb.dao.ExecutionDao;
import com.tcoffman.ttwb.dao.ModelDao;
import com.tcoffman.ttwb.dao.ScenarioDao;
import com.tcoffman.ttwb.dao.StateDao;
import com.tcoffman.ttwb.dao.remote.RemoteModelDao;
import com.tcoffman.ttwb.dao.remote.RemoteStateDao;
import com.tcoffman.ttwb.dao.sql.SqlExecutionDao;
import com.tcoffman.ttwb.dao.sql.SqlScenarioDao;
import com.tcoffman.ttwb.scenario.engine.EngineScheduler;

public class ScenarioWebApplicationBinder extends AbstractBinder {

	public ScenarioWebApplicationBinder() {
		super();
	}

	@Override
	protected void configure() {
		// bind(GameStateFileRepository.class).to(GameStateFileRepository.class).in(Singleton.class);
		// bind(GameModelFileRepository.class).to(GameModelFileRepository.class).in(Singleton.class);
		// final GameStateFileRepository stateRepo = new
		// GameStateFileRepository();
		// final GameModelFileRepository modelRepo = new
		// GameModelFileRepository();
		// bind(stateRepo).to(GameStateFileRepository.class);
		// bind(modelRepo).to(GameModelFileRepository.class);

		final Jdbi jdbi = Jdbi.create(System.getenv("JDBC_DATABASE_URL"));
		jdbi.installPlugin(new SqlObjectPlugin());
		bind(jdbi).to(Jdbi.class);

		final ScenarioDao scenarioDao = jdbi.onDemand(SqlScenarioDao.class);
		bind(scenarioDao).to(ScenarioDao.class);

		final ExecutionDao executionDao = jdbi.onDemand(SqlExecutionDao.class);
		bind(executionDao).to(ExecutionDao.class);

		final ModelDao modelDao = new RemoteModelDao();
		bind(modelDao).to(ModelDao.class);

		final StateDao stateDao = new RemoteStateDao();
		bind(stateDao).to(StateDao.class);

		final EngineScheduler engineService = new EngineScheduler(executionDao, stateDao);
		bind(engineService).to(EngineScheduler.class);

		final EngineLifecycleManager engineLifecycleManager = new EngineLifecycleManager(engineService);
		bind(engineLifecycleManager).to(EngineLifecycleManager.class);
	}

	private static class EngineLifecycleManager implements PostConstruct, PreDestroy {

		public EngineScheduler m_engineService;

		public EngineLifecycleManager(EngineScheduler engineService) {
			m_engineService = engineService;
		}

		@Override
		public void preDestroy() {
			m_engineService.waitStop();
		}

		@Override
		public void postConstruct() {
			// TODO maybe consider starting automatically
		}

	}

}
