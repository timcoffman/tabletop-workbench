package com.tcoffman.ttwb.state.mutation;

import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;

public interface GameMoveOperation extends GameOperation {

	@Override
	default Type getType() {
		return Type.MOVE;
	}

	@Override
	default <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	GamePlacePattern getSubject();

	GamePlacePattern getTarget();

}
