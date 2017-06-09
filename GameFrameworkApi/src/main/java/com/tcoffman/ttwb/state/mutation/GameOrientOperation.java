package com.tcoffman.ttwb.state.mutation;

import com.tcoffman.ttwb.state.GamePlace;

public interface GameOrientOperation extends GameOperation {

	@Override
	default Type getType() {
		return Type.ORIENT;
	}

	@Override
	default <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	GamePlace getSubject();

	GamePlace getTarget();

}
