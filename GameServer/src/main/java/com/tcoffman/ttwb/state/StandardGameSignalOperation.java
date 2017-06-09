package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.state.mutation.GameSignalOperation;

public class StandardGameSignalOperation extends StandardGameOperation implements GameSignalOperation {

	public StandardGameSignalOperation(GameRole role) {
		super(role);
	}

}
