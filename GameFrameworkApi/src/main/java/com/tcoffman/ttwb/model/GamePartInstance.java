package com.tcoffman.ttwb.model;

import com.tcoffman.ttwb.component.GameComponentRef;

public interface GamePartInstance {

	GameComponentRef<GamePartPrototype> getPrototype();

}
