package com.tcoffman.ttwb.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GameStage;

public class StandardGameOperationSet implements GameOperationSet {

	private final GameComponentRef<GameStage> m_result;
	private final Collection<StandardGameOperation> m_operations = new ArrayList<StandardGameOperation>();

	public StandardGameOperationSet(GameComponentRef<GameStage> result) {
		m_result = result;
	}

	@Override
	public Stream<? extends GameOperation> operations() {
		return m_operations.stream();
	}

	public void add(StandardGameOperation op) {
		m_operations.add(op);
	}

	@Override
	public GameComponentRef<GameStage> getResult() {
		return m_result;
	}

}
