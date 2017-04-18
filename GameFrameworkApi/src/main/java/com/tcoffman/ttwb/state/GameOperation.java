package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.model.GameRole;

public interface GameOperation {

	public enum Type {
		SIGNAL, MOVE, // subject-place, target-place
		ORIENT, // subject-place, target-place
		JOIN, // subject, target
		SPLIT, // subject, target-place, quantity
	}

	GameRole getRole();

	Type getType();

	<T> T visit(Visitor<T> visitor);

	public interface Visitor<T> {
		T visit(GameOperation op);

		default T visit(GameSignalOperation op) {
			return visit((GameOperation) op);
		}

		default T visit(GameMoveOperation op) {
			return visit((GameOperation) op);
		}

		default T visit(GameOrientOperation op) {
			return visit((GameOperation) op);
		}

		default T visit(GameJoinOperation op) {
			return visit((GameOperation) op);
		}

		default T visit(GameSplitOperation op) {
			return visit((GameOperation) op);
		}
	}

}
