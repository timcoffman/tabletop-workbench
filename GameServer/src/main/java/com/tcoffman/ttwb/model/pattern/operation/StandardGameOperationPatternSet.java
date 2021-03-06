package com.tcoffman.ttwb.model.pattern.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GameStage;

public class StandardGameOperationPatternSet implements GameOperationPatternSet {

	private final Collection<StandardGameOperationPattern> m_operationPatterns = new ArrayList<StandardGameOperationPattern>();
	private final GameComponentRef<GameStage> m_result;

	public StandardGameOperationPatternSet(GameComponentRef<GameStage> result) {
		m_result = result;
	}

	public void add(StandardGameOperationPattern operationPattern) {
		m_operationPatterns.add(operationPattern);
	}

	@Override
	public Stream<? extends GameOperationPattern> operations() {
		return m_operationPatterns.stream();
	}

	@Override
	public GameComponentRef<GameStage> getResult() {
		return m_result;
	}

	@Override
	public String toString() {
		return "-> " + m_result.get() + " by " + m_operationPatterns;
	}

}
