package com.tcoffman.ttwb.state;


public interface GameOrientOperation extends GameOperation {

	@Override
	default Type getType() {
		return Type.ORIENT;
	}

	@Override
	default <T> T visit(Visitor<T> visitor) {
		return visitor.visit(this);
	}

	GamePlace getSubject();

	GamePlace getTarget();

}
