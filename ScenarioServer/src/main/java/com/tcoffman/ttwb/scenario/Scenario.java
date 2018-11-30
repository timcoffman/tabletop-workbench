package com.tcoffman.ttwb.scenario;

import java.util.Collection;

import com.tcoffman.ttwb.state.GameParticipant;

public interface Scenario {

	String getModel();

	Collection<GameParticipant> getParticipants();
}
