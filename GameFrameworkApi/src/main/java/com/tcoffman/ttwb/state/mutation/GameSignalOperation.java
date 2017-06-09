package com.tcoffman.ttwb.state.mutation;

public interface GameSignalOperation extends GameOperation {

	@Override
	default Type getType() {
		return Type.SIGNAL;
	}

	@Override
	default <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

}
