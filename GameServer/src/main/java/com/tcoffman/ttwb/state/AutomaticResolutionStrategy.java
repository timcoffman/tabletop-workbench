package com.tcoffman.ttwb.state;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPatternSet;
import com.tcoffman.ttwb.state.mutation.ResolvedMoveOperation;
import com.tcoffman.ttwb.state.mutation.ResolvedOperation;
import com.tcoffman.ttwb.state.mutation.ResolvedOperationSet;
import com.tcoffman.ttwb.state.mutation.ResolvedSignalOperation;

public class AutomaticResolutionStrategy implements ResolutionStrategy {

	private final GameState m_state;

	public AutomaticResolutionStrategy(GameState state) {
		m_state = state;
	}

	@Override
	public Optional<ResolvedOperationSet> autoResolve(GameOperationPatternSet ops) {
		final List<Optional<ResolvedOperation>> resolvedOps = ops.operations().map(this::autoResolve).collect(Collectors.toList());
		if (!resolvedOps.stream().allMatch(Optional::isPresent))
			return Optional.empty();

		final ResolvedOperationSet.Editor resolvedOpSet = ResolvedOperationSet.create();
		resolvedOpSet.setResult(ops.getResult());
		resolvedOps.stream().map(Optional::get).forEach(resolvedOpSet::addOperation);

		try {
			return Optional.of(resolvedOpSet.done());
		} catch (final GameComponentBuilderException ex) {
			throw new IllegalStateException("failed to build resolved operation set", ex);
		}
	}

	private Optional<ResolvedOperation> autoResolve(GameOperationPattern opPattern) {

		try {
			switch (opPattern.getType()) {
			case SIGNAL:
				return resolveSignal();
			case MOVE:
				return resolveMove(opPattern);
			default:
				throw new UnsupportedOperationException(opPattern.getType() + " not yet supported");
			}
		} catch (final GameComponentBuilderException ex) {
			throw new IllegalStateException("failed to build resolved operation", ex);
		}
	}

	private Optional<ResolvedOperation> resolveSignal() throws GameComponentBuilderException {
		final GameRole systemRole = m_state.getModel().effectiveSystemRole();
		return Optional.of(ResolvedSignalOperation.create().setRole(systemRole).done());
	}

	private Optional<ResolvedOperation> resolveMove(GameOperationPattern opPattern) throws GameComponentBuilderException {
		final GameRole systemRole = m_state.getModel().effectiveSystemRole();
		final ResolvedMoveOperation.Editor editor = ResolvedMoveOperation.create();
		editor.setRole(systemRole);
		final List<? extends GamePlace> subjects = m_state.find(opPattern.getSubjectPlacePattern().get()).collect(Collectors.toList());
		final List<? extends GamePlace> targets = m_state.find(opPattern.getTargetPlacePattern().get()).collect(Collectors.toList());

		if (subjects.isEmpty() || targets.isEmpty())
			return Optional.empty();

		final Set<GamePlace> pairedSubjects = new HashSet<>();
		final Set<GamePlace> pairedTargets = new HashSet<>();

		for (final GamePlace s : subjects) {
			final List<GamePlace> pairableTargets = targets.stream().filter(Predicate.isEqual(s).negate()).collect(Collectors.toList());
			if (!pairableTargets.isEmpty())
				editor.createSubjectTargetPair((stp) -> {
					stp.setSubject(s);
					pairedSubjects.add(s);
					pairableTargets.forEach(stp::addTarget);
					pairedTargets.addAll(pairableTargets);
				});
		}

		if (pairedSubjects.size() == 1 || pairedTargets.size() == 1)
			return Optional.of(editor.done());

		return Optional.empty();
	}
}