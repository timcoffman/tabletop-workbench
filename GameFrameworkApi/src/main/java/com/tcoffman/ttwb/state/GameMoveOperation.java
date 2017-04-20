package com.tcoffman.ttwb.state;


public interface GameMoveOperation extends GameOperation {

	@Override
	default Type getType() {
		return Type.SIGNAL;
	}

	@Override
	default <T> T visit(Visitor<T> visitor) {
		return visitor.visit(this);
	}

	GamePlaceInstance getSubject();

	GamePlaceInstance getTarget();

}
