package com.tcoffman.ttwb.state.mutation;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GameRole;

public final class ResolvedMoveOperation extends ResolvedOperation {

	private final Collection<ResolvedMoveSubject> m_subjects = new ArrayList<>();

	public Stream<? extends ResolvedMoveSubject> subjects() {
		return m_subjects.stream();
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	private ResolvedMoveOperation() {

	}

	public static Editor create() {
		return new ResolvedMoveOperation().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public class Editor extends ResolvedOperation.Editor<ResolvedMoveOperation> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			setTypeInternal(GameOperation.Type.MOVE);
			super.validate();
			requireNotEmpty(CORE, "subjects", m_subjects);
		}

		@Override
		protected ResolvedMoveOperation model() {
			return ResolvedMoveOperation.this;
		}

		public Editor setRole(GameRole role) {
			setRoleInternal(role);
			return this;
		}

		public Editor createSubject(AbstractEditor.Initializer<ResolvedMoveSubject.Editor> initializer) throws GameComponentBuilderException {
			return configure(ResolvedMoveSubject.create().completed(m_subjects::add), initializer);
		}

	}

	@Override
	public String toString() {
		return m_subjects.toString();
	}

}