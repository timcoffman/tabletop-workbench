package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GamePart;
import com.tcoffman.ttwb.model.GameQuantity;

public interface GameSplitOperation extends GameOperation {

	@Override
	default Type getType() {
		return Type.SPLIT;
	}

	@Override
	default <T> T visit(Visitor<T> visitor) {
		return visitor.visit(this);
	}

	GamePart getSubject();

	GameQuantity getQuantity();
}
