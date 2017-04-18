package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GamePart;
import com.tcoffman.ttwb.model.GamePartPrototype;

public class StandardGamePart implements GamePart {

	private GamePartPrototype m_prototype;

	@Override
	public GamePartPrototype getPrototype() {
		return m_prototype;
	}

}
