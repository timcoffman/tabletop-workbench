package com.tcoffman.ttwb.dao;

import java.util.Optional;

import com.tcoffman.ttwb.dao.data.Role;
import com.tcoffman.ttwb.dao.data.State;

public interface StateDao {

	Optional<State> lookup(String stateUrl);

	State create(String model);

	void bindParticipant(State state, Role role, String authorizationForRole);
}
