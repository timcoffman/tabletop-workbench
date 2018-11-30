package com.tcoffman.ttwb.dao;

import java.util.Map;
import java.util.Optional;

import com.tcoffman.ttwb.dao.data.Scenario;

public interface ScenarioDao {

	long create(Scenario scenario);

	Optional<Scenario> lookup(long id);

	Map<Long, Scenario> all();

	void update(long id, Scenario scenario);

}
