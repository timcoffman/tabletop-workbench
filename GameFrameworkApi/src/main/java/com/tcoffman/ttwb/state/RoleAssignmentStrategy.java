package com.tcoffman.ttwb.state;

import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GameRole;

public interface RoleAssignmentStrategy {

	Stream<? extends GameComponentRef<GameRole>> preferredRoles(GameModel model);

}
