package com.tcoffman.ttwb.dao.sql;

import java.util.Map;
import java.util.Optional;

import org.jdbi.v3.sqlobject.config.KeyColumn;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.MaxRows;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.tcoffman.ttwb.dao.ExecutionDao;
import com.tcoffman.ttwb.dao.data.Execution;

public interface SqlExecutionDao extends ExecutionDao {

	@Override
	@SqlUpdate("INSERT INTO execution(scenario_id,state) VALUES (:scenarioId,:state)")
	@GetGeneratedKeys({ "id" })
	long create(@BindBean Execution execution);

	@Override
	@SqlUpdate("UPDATE execution SET state = :state")
	void update(@Bind("id") long id, @BindBean Execution execution);

	@Override
	@SqlQuery("SELECT * FROM execution WHERE id = :id")
	@RegisterBeanMapper(Execution.class)
	Optional<Execution> lookup(@Bind("id") long id);

	@Override
	@SqlQuery("SELECT * FROM execution ORDER BY id")
	@KeyColumn("id")
	@RegisterBeanMapper(Execution.class)
	Map<Long, Execution> all();

	@Override
	@SqlQuery("SELECT * FROM execution WHERE id = :executionId ORDER BY id")
	@KeyColumn("id")
	@RegisterBeanMapper(Execution.class)
	Map<Long, Execution> allForScenario(@Bind("executionId") long scenarioId);

	@Override
	@SqlQuery("SELECT id FROM execution WHERE status = :status")
	@MaxRows(1)
	@RegisterBeanMapper(Execution.class)
	Optional<Long> anyWithStatus(@Bind("status") String status);

	@Override
	@SqlUpdate("UPDATE execution SET status = :workingStatus WHERE id = :executionId AND status = :status")
	boolean acquire(@Bind("executionId") long executionId, @Bind("status") String status, @Bind("workingStatus") String workingStatus);

	@Override
	@SqlUpdate("UPDATE execution SET status = :status WHERE id = :executionId AND status = :workingStatus")
	boolean release(long executionId, String workingStatus, String status);
}
