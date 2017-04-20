package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GameComponentRef;
import com.tcoffman.ttwb.model.GamePart;
import com.tcoffman.ttwb.model.GamePartPrototype;

public class StandardGamePart implements GamePart {

	private final GameComponentRef<GamePartPrototype> m_prototype;

	public StandardGamePart(GameComponentRef<GamePartPrototype> prototype) {
		m_prototype = prototype;
	}

	@Override
	public GameComponentRef<GamePartPrototype> getPrototype() {
		return m_prototype;
	}

}
