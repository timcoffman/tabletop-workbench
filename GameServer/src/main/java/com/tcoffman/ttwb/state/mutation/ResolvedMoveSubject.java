package com.tcoffman.ttwb.state.mutation;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.state.GamePlace;

public final class ResolvedMoveSubject {

	private GamePlace m_subject;
	private final Collection<GamePlace> m_targets = new ArrayList<>();

	public GamePlace getSubject() {
		return m_subject;
	}

	public Stream<? extends GamePlace> targets() {
		return m_targets.stream();
	}

	private ResolvedMoveSubject() {

	}

	public static Editor create() {
		return new ResolvedMoveSubject().edit();
	}

	public Editor edit() {
		return new Editor();
	}

	public class Editor extends AbstractEditor<ResolvedMoveSubject> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			requirePresent(CORE, "subject", m_subject);
			requireNotEmpty(CORE, "targets", m_targets);
		}

		@Override
		protected ResolvedMoveSubject model() {
			return ResolvedMoveSubject.this;
		}

		public Editor setSubject(GamePlace subject) {
			requireNotDone();
			m_subject = subject;
			return this;
		}

		public Editor addTarget(GamePlace target) {
			requireNotDone();
			m_targets.add(target);
			return this;
		}

	}

	@Override
	public String toString() {
		return GameOperation.Type.MOVE.toString() + " " + m_subject + " TO "
				+ m_targets.stream().map(Object::toString).collect(Collectors.joining(" + ", "(", ")"));
	}
}