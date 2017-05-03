package com.tcoffman.ttwb.state;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.pattern.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.GameOperationPatternSet;
import com.tcoffman.ttwb.model.pattern.GamePlacePattern;
import com.tcoffman.ttwb.state.pattern.StandardAnyPlacePattern;
import com.tcoffman.ttwb.state.pattern.StandardIntersectionPlacePattern;

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
		final GamePlace subject = view.find(opPattern.getSubjectPlacePattern().get()).findFirst()
				.orElseThrow(() -> new IllegalStateException("move op cannot be fulfilled: no subject"));

		GamePlacePattern targetPattern;
		try {
			final GamePlacePattern excludingSubjectPattern = StandardAnyPlacePattern.create().setPartPattern(() -> (part) -> part != subject.getOwner()).done();
			targetPattern = StandardIntersectionPlacePattern.create().addPattern(opPattern.getTargetPlacePattern().get()).addPattern(excludingSubjectPattern)
					.done();
		} catch (final GameComponentBuilderException ex) {
			throw new RuntimeException("move op cannot be fulfilled: failed to build target pattern", ex);
		}

		final GamePlace target = view.find(targetPattern).findFirst().orElseThrow(() -> new IllegalStateException("move op cannot be fulfilled: no target"));

		return new StandardGameMoveOperation(opRole, subject, target);
	}
}
