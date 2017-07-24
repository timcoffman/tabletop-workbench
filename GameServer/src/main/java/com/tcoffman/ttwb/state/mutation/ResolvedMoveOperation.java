package com.tcoffman.ttwb.state.mutation;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GameRole;

public final class ResolvedMoveOperation extends ResolvedOperation {

	private final Collection<ResolvedMoveSubjectTargetPairs> m_subjectTargetPairs = new ArrayList<>();

	public Stream<? extends ResolvedMoveSubjectTargetPairs> subjects() {
		return m_subjectTargetPairs.stream();
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
			requireNotEmpty(CORE, "subjects", m_subjectTargetPairs);
		}

		@Override
		protected ResolvedMoveOperation model() {
			return ResolvedMoveOperation.this;
		}

		public Editor setRole(GameRole role) {
			setRoleInternal(role);
			return this;
		}

		public Editor createSubjectTargetPair(AbstractEditor.Initializer<ResolvedMoveSubjectTargetPairs.Editor> initializer) throws GameComponentBuilderException {
			return configure(ResolvedMoveSubjectTargetPairs.create().completed(m_subjectTargetPairs::add), initializer);
		}

	}

	@Override
	public String toString() {
		return m_subjectTargetPairs.toString();
	}

}