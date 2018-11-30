package com.tcoffman.ttwb.dao.sql;

import java.util.Map;
import java.util.Optional;

import org.jdbi.v3.sqlobject.config.KeyColumn;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.tcoffman.ttwb.dao.ScenarioDao;
import com.tcoffman.ttwb.dao.data.Scenario;

public interface SqlScenarioDao extends ScenarioDao {

	@Override
	@SqlUpdate("INSERT INTO scenario(model,description,participant_count_min,participant_count_max,execution_count,termination) VALUES (:model,:description,:participantCountMin,:participantCountMax,:executionCount,:termination)")
	@GetGeneratedKeys({ "id" })
	long create(@BindBean Scenario scenario);

	@Override
	@SqlUpdate("UPDATE scenario SET description = :description, participant_count_min = :participantCountMin, participant_count_max = :participantCountMax, execution_count = :executionCount, termination = :termination WHERE id = :id")
	void update(@Bind("id") long id, @BindBean Scenario scenario);

	@Override
	@SqlQuery("SELECT * FROM scenario WHERE id = :id")
	@RegisterBeanMapper(Scenario.class)
	Optional<Scenario> lookup(@Bind("id") long id);

	@Override
	@SqlQuery("SELECT * FROM scenario ORDER BY id")
	@KeyColumn("id")
	@RegisterBeanMapper(Scenario.class)
	Map<Long, Scenario> all();
}
