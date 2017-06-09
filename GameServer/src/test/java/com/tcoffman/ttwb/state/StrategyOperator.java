package com.tcoffman.ttwb.state;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.tcoffman.ttwb.model.pattern.operation.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPatternSet;
import com.tcoffman.ttwb.state.mutation.GameOperationSet;

public class StrategyOperator {

	private final Strategy m_strategy;
	private final GameParticipant m_participant;

	public StrategyOperator(GameParticipant participant, Strategy strategy) {
		m_participant = participant;
		m_strategy = strategy;
	}

	public interface Strategy {

		void generateOperations(GameParticipant participant, GameOperationPattern pattern, Consumer<StandardGameOperation> operationConsumer);

		Predicate<GameOperationPatternSet> operation(GameParticipant participant);

	}

	public GameOperationSet calculateOperations(GameState state) {
		final GameOperationPatternSet operationPatternSet = state.allowedOperations().filter(m_strategy.operation(m_participant)).findAny()
				.orElseThrow(() -> new IllegalStateException("strategy dead end"));

		final StandardGameOperationSet opSet = new StandardGameOperationSet(operationPatternSet.getResult());
		operationPatternSet.operations().forEach((op) -> {
			m_strategy.generateOperations(m_participant, op, opSet::add);
		});
		return opSet;
	}
}
