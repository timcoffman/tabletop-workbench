package com.tcoffman.ttwb.state;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPatternSet;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;

public class RandomOperatorStrategy implements StrategyOperator.Strategy {

	@Override
	public Predicate<GameOperationPatternSet> operation(GameParticipant participant) {
		return (p) -> true;
	}

	@Override
	public void generateOperations(GameParticipant participant, GameOperationPattern pattern, Consumer<StandardGameOperation> consumer) {
		consumer.accept(fulfill(participant.getOwner().participantView(participant), pattern, participant.getRole()));
	}

	private StandardGameOperation fulfill(final StateView view, GameOperationPattern opPattern, final GameRole opRole) {
		switch (opPattern.getType()) {
		case SIGNAL:
			return fulfillSignal(view, opPattern, opRole);
		case MOVE:
			return fulfillMove(view, opPattern, opRole);
		default:
			throw new UnsupportedOperationException("cannot fulfill a \"" + opPattern.getType() + "\" operation pattern");
		}

	}

	private StandardGameOperation fulfillSignal(StateView view, GameOperationPattern opPattern, final GameRole opRole) {
		return new StandardGameSignalOperation(opRole);
	}

	private StandardGameOperation fulfillMove(StateView view, GameOperationPattern opPattern, final GameRole opRole) {
		final GamePlacePattern subjectPlacePattern = opPattern.getSubjectPlacePattern().orElseThrow(
				() -> new IllegalArgumentException("missing subject place pattern"));
		final GamePlacePattern targetPlacePattern = opPattern.getTargetPlacePattern().orElseThrow(
				() -> new IllegalArgumentException("missing target place pattern"));

		return new StandardGameMoveOperation(opRole, subjectPlacePattern, targetPlacePattern);
	}
}
