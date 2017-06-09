package com.tcoffman.ttwb.state.persistence;

import com.tcoffman.ttwb.component.persistence.GameComponentRefManager;
import com.tcoffman.ttwb.state.GamePart;

public interface StateRefManager extends StateRefResolver {

	GameComponentRefManager<GamePart> getPartManager();

	void resolveAll();

}