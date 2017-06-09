package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GameRole;

public interface GameParticipant {

	GameState getOwner();

	GameRole getRole();

	Object getAuthorization();
}
