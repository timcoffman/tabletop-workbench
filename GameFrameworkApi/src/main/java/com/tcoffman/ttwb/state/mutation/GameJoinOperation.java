package com.tcoffman.ttwb.state.mutation;

import com.tcoffman.ttwb.state.GamePart;

public interface GameJoinOperation extends GameOperation {

	@Override
	default Type getType() {
		return Type.JOIN;
	}

	@Override
	default <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	GamePart getSubject();

	GamePart getTarget();

}
