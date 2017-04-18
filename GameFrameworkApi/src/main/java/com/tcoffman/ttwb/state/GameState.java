package com.tcoffman.ttwb.state;

import java.util.stream.Stream;

import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.pattern.GameOperationPatternSet;

public interface GameState extends StateView {

	GameModel getModel();

	Stream<? extends GameRole> roles();

	StateView roleView(GameRole role);

	GameRole addRole();

	Stream<? extends GameOperationPatternSet> allowedOperations();

	GameParticipant assignRole(GameRole role);

	void mutate(GameStateLogEntry log);

}
