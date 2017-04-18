package com.tcoffman.ttwb.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.model.GameStageRef;

public class StandardGameOperationSet implements GameOperationSet {

	private final GameStageRef m_result;
	private final Collection<StandardGameOperation> m_operations = new ArrayList<StandardGameOperation>();

	public StandardGameOperationSet(GameStageRef result) {
		m_result = result;
	}

	@Override
	public Stream<? extends GameOperation> operations() {
		return m_operations.parallelStream();
	}

	public void add(StandardGameOperation op) {
		m_operations.add(op);
	}

	@Override
	public GameStageRef getResult() {
		return m_result;
	}

}
