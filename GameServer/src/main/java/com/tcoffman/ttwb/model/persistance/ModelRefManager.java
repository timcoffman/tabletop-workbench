package com.tcoffman.ttwb.model.persistance;

import com.tcoffman.ttwb.component.persistence.GameComponentRefManager;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;

public interface ModelRefManager extends ModelRefResolver {

	GameComponentRefManager<GameStage> getStageManager();

	GameComponentRefManager<GamePartPrototype> getPartPrototypeManager();

	GameComponentRefManager<GameRole> getRoleManager();

	void resolveAll();

}