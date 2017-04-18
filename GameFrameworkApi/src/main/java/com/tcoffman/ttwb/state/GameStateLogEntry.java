package com.tcoffman.ttwb.state;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

import com.tcoffman.ttwb.model.GameStageRef;

public final class GameStateLogEntry {

	private final GameStageRef m_forward;
	private final GameStageRef m_rollback;
	private final List<GameStateMutation> m_forwardMutations = new ArrayList<GameStateMutation>();
	private final List<GameStateMutation> m_rollbackMutations = new ArrayList<GameStateMutation>();

	public GameStateLogEntry(GameStageRef forward, GameStageRef rollback) {
		m_forward = forward;
		m_rollback = rollback;
	}

	public void apply(GameStateMutation forward, GameStateMutation rollback) {
		m_forwardMutations.add(forward);
		m_rollbackMutations.add(0, rollback);
	}

	public GameStageRef getForward() {
		return m_forward;
	}

	public GameStageRef getRollback() {
		return m_rollback;
	}

	public Stream<? extends GameStateMutation> forwardMutations() {
		return m_forwardMutations.stream();
	}

	public Stream<? extends GameStateMutation> rollbackMutations() {
		return m_rollbackMutations.stream();
	}

	public GameStateLogEntry invert() {
		final GameStateLogEntry log = new GameStateLogEntry(m_rollback, m_forward);
		final Iterator<GameStateMutation> i = m_forwardMutations.iterator();
		final ListIterator<GameStateMutation> j = m_rollbackMutations.listIterator(m_rollbackMutations.size() - 1);
		while (i.hasNext())
			log.apply(j.previous(), i.next());
		return log;
	}
}