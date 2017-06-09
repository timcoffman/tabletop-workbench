package com.tcoffman.ttwb.state.mutation;

import com.tcoffman.ttwb.model.GameQuantity;
import com.tcoffman.ttwb.state.GamePart;

public interface GameSplitOperation extends GameOperation {

	@Override
	default Type getType() {
		return Type.SPLIT;
	}

	@Override
	default <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	GamePart getSubject();

	GameQuantity getQuantity();
}
