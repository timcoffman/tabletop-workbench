package com.tcoffman.ttwb.state;


public interface GameJoinOperation extends GameOperation {

	@Override
	default Type getType() {
		return Type.JOIN;
	}

	@Override
	default <T> T visit(Visitor<T> visitor) {
		return visitor.visit(this);
	}

	GamePart getSubject();

	GamePart getTarget();

}
