package com.tcoffman.ttwb.state.mutation;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GameRole;

public abstract class ResolvedOperation {

	private GameOperation.Type m_type;
	private GameRole m_role;

	public GameOperation.Type getType() {
		return m_type;
	}

	public GameRole getRole() {
		return m_role;
	}

	public interface Visitor<R, E extends Throwable> {
		default R visit(ResolvedOperation resolvedOperation) throws E {
			throw new UnsupportedOperationException("game operation " + resolvedOperation.getClass().getSimpleName() + " (" + resolvedOperation.getType()
					+ ") not supported");
		}

		R visit(ResolvedSignalOperation resolvedOperation) throws E;

		R visit(ResolvedMoveOperation resolvedOperation) throws E;
	}

	public abstract <R, E extends Throwable> R visit(ResolvedOperation.Visitor<R, E> visitor) throws E;

	protected ResolvedOperation() {

	}

	protected abstract class Editor<T extends ResolvedOperation> extends AbstractEditor<T> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			requirePresent(CORE, "type", m_type);
			requirePresent(CORE, "role", m_role);
		}

		protected void setTypeInternal(GameOperation.Type type) {
			requireNotDone();
			m_type = type;
		}

		protected void setRoleInternal(GameRole role) {
			requireNotDone();
			m_role = role;
		}

	}
}