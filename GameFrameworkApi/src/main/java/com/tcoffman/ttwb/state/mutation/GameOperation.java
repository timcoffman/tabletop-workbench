package com.tcoffman.ttwb.state.mutation;

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

	<R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E;

	public interface Visitor<R, E extends Throwable> {
		R visit(GameOperation op);

		default R visit(GameSignalOperation op) throws E {
			return visit((GameOperation) op);
		}

		default R visit(GameMoveOperation op) throws E {
			return visit((GameOperation) op);
		}

		default R visit(GameOrientOperation op) throws E {
			return visit((GameOperation) op);
		}

		default R visit(GameJoinOperation op) throws E {
			return visit((GameOperation) op);
		}

		default R visit(GameSplitOperation op) throws E {
			return visit((GameOperation) op);
		}
	}

}
