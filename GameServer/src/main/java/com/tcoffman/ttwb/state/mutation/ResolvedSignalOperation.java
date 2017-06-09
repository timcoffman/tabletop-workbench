package com.tcoffman.ttwb.state.mutation;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GameRole;

public final class ResolvedSignalOperation extends ResolvedOperation {

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	private ResolvedSignalOperation() {

	}

	public static Editor create() {
		return new ResolvedSignalOperation().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public class Editor extends ResolvedOperation.Editor<ResolvedSignalOperation> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			setTypeInternal(GameOperation.Type.SIGNAL);
			super.validate();
		}

		@Override
		protected ResolvedSignalOperation model() {
			return ResolvedSignalOperation.this;
		}

		public Editor setRole(GameRole role) {
			setRoleInternal(role);
			return this;
		}

	}

	@Override
	public String toString() {
		return getType().toString();
	}
}