package com.tcoffman.ttwb.dao;

import java.util.Map;
import java.util.Optional;

import com.tcoffman.ttwb.dao.data.Execution;

public interface ExecutionDao {

	long create(Execution execution);

	Optional<Execution> lookup(long id);

	Map<Long, Execution> all();

	void update(long id, Execution execution);

	Map<Long, Execution> allForScenario(long scenarioId);

	Optional<Long> anyWithStatus(String status);

	boolean acquire(long executionId, String status, String workingStatus);

	boolean release(long executionId, String workingStatus, String status);

}
