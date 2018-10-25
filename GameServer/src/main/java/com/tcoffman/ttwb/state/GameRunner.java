package com.tcoffman.ttwb.state;

import static com.tcoffman.ttwb.core.Core.TOKEN_SUBJECT;
import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.core.Core;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.pattern.StandardPatternContext;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPatternSet;
import com.tcoffman.ttwb.model.pattern.part.StandardVariablePartPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.model.pattern.place.StandardAnyPlacePattern;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.state.mutation.GameMoveOperation;
import com.tcoffman.ttwb.state.mutation.GameOperation;
import com.tcoffman.ttwb.state.mutation.GameOperationSet;
import com.tcoffman.ttwb.state.mutation.GameSignalOperation;
import com.tcoffman.ttwb.state.mutation.GameStateAddRelationship;
import com.tcoffman.ttwb.state.mutation.GameStateLogEntry;
import com.tcoffman.ttwb.state.mutation.GameStateRemoveRelationship;
import com.tcoffman.ttwb.state.mutation.ResolvedMoveOperation;
import com.tcoffman.ttwb.state.mutation.ResolvedMoveSubjectTargetPairs;
import com.tcoffman.ttwb.state.mutation.ResolvedOperation;
import com.tcoffman.ttwb.state.mutation.ResolvedOperationSet;
import com.tcoffman.ttwb.state.mutation.ResolvedSignalOperation;

public class GameRunner {

	private final GameState m_state;
	private final GameComponentRef<GamePartRelationshipType> m_location;

	public GameRunner(GameState state) throws PluginException {
		m_state = state;
		m_location = m_state.requireModelPlugin(CORE).getRelationshipType(Core.RELATIONSHIP_PHYSICAL);
	}

	public void step() {

		m_state.allowedOperations().collect(Collectors.toList());

	}

	private GameOperationPatternSet validateOperationSet(ResolvedOperationSet resolvedOps) throws IllegalArgumentException {
		final GameStage result = resolvedOps.getResult().get();
		final List<? extends GameOperationPatternSet> validResultPatternSets = m_state.allowedOperations().filter(matchesResult(result))
				.collect(Collectors.toList());
		if (validResultPatternSets.isEmpty())
			throw new IllegalArgumentException("no allowed operations match the result \"" + result + "\"; try one of "
					+ m_state.allowedOperations().map(GameOperationPatternSet::getResult).map(GameComponentRef::get).distinct().map(Object::toString)
							.collect(Collectors.joining(", ", "[", "]")));

		final List<? extends ResolvedOperation> operations = resolvedOps.operations().collect(Collectors.toList());
		final List<? extends GameOperationPatternSet> validOperationsPatternSets = validResultPatternSets.stream().filter(matchesOperations(operations))
				.collect(Collectors.toList());
		if (validOperationsPatternSets.isEmpty())
			throw new IllegalArgumentException("no allowed operations match the requested operations: " + resolvedOps);
		return validOperationsPatternSets.get(0);
	}

	private Predicate<GameOperationPatternSet> matchesResult(GameStage result) {
		return (ops) -> ops.getResult().get() == result;
	}

	private Predicate<GameOperationPatternSet> matchesOperations(List<? extends ResolvedOperation> operations) {
		return (ops) -> matchOperationPatterns(ops.operations().collect(Collectors.toList()), operations).findAny().isPresent();
	}

	private Stream<List<OperationPatternMatch>> matchOperationPatterns(List<? extends GameOperationPattern> opPatterns,
			List<? extends ResolvedOperation> operations) {
		final Stream.Builder<List<OperationPatternMatch>> builder = Stream.builder();

		for (final ResolvedOperation resolvedOp : operations) {
			final Predicate<GameOperationPattern> opPatternMatcher = matchesOperation(resolvedOp);

			for (final GameOperationPattern opPattern : opPatterns)
				if (opPatternMatcher.test(opPattern)) {

					final List<? extends GameOperationPattern> remainingOpPatterns = new ArrayList<GameOperationPattern>(opPatterns);
					remainingOpPatterns.remove(opPattern);
					final List<? extends ResolvedOperation> remainingOps = new ArrayList<ResolvedOperation>(operations);
					remainingOps.remove(resolvedOp);

					if (remainingOps.isEmpty() && remainingOpPatterns.isEmpty()) {

						/* nothing left to match; report this match */

						final List<OperationPatternMatch> match = new ArrayList<>();
						match.add(new OperationPatternMatch(opPattern, resolvedOp));
						builder.add(match);

					} else
						matchOperationPatterns(remainingOpPatterns, remainingOps).forEach((submatch) -> {
							final List<OperationPatternMatch> match = new ArrayList<>();
							match.add(new OperationPatternMatch(opPattern, resolvedOp));
							match.addAll(submatch);
							builder.add(match);
						});
				} else {
					/* match failed */
				}
		}
		return builder.build();
	}

	private Predicate<GameOperationPattern> matchesOperation(ResolvedOperation resolvedOp) {
		return (op) -> {
			if (!op.getType().equals(resolvedOp.getType()))
				return false;
			if (!op.getRolePattern().allows(m_state).test(resolvedOp.getRole()))
				return false;
			if (!resolvedOp.visit(new ResolvedOperation.Visitor<Boolean, RuntimeException>() {

				@Override
				public Boolean visit(ResolvedSignalOperation resolvedOperation) throws RuntimeException {
					/* signal has no properties which could fail to match */
					return true;
				}

				@Override
				public Boolean visit(ResolvedMoveOperation resolvedOperation) throws RuntimeException {
					// check the subject quantity here!!
					if (!resolvedOperation.subjects().allMatch((s) -> matchesMoveSubject(s).test(op)))
						return false;
					return true;
				}

			}))
				return false;
			return true;
		};
	}

	private Predicate<GameOperationPattern> matchesMoveSubject(ResolvedMoveSubjectTargetPairs subject) {
		// check the subject quantity here!!
		return (op) -> {
			final GamePlacePattern subjectPattern = op.getSubjectPlacePattern()
					.orElseThrow(() -> new IllegalArgumentException("missing subject place pattern"));
			;
			if (!m_state.test(subjectPattern, subject.getSubject().getOwner()))
				return false;
			if (!m_state.test(subjectPattern, subject.getSubject()))
				return false;

			final GamePlacePattern targetPattern = op.getTargetPlacePattern().orElseThrow(() -> new IllegalArgumentException("missing target place pattern"));
			if (!subject.targets().map(GamePlace::getOwner).filter((p) -> m_state.test(targetPattern, p)).findAny().isPresent())
				return false;
			if (!subject.targets().filter((p) -> m_state.test(targetPattern, p)).findAny().isPresent())
				return false;

			return true;
		};
	}

	private static class OperationPatternMatch {
		private final GameOperationPattern m_operationPattern;
		private final ResolvedOperation m_resolvedOperation;

		public OperationPatternMatch(GameOperationPattern operationPattern, ResolvedOperation resolvedOperation) {
			m_operationPattern = operationPattern;
			m_resolvedOperation = resolvedOperation;
		}

		public GameOperationPattern getOperationPattern() {
			return m_operationPattern;
		}

		public ResolvedOperation getOperation() {
			return m_resolvedOperation;
		}

	}

	public ResolvedOperationSet resolve(GameOperationSet ops) throws GameComponentBuilderException {
		final ResolvedOperationSet.Editor resolvedOps = ResolvedOperationSet.create();
		resolvedOps.setResult(ops.getResult());

		ops.operations().forEach((op) -> {
			final GameRole role = op.getRole();

			try {
				final ResolvedOperation resolvedOp = op.visit(new GameOperation.Visitor<ResolvedOperation, GameComponentBuilderException>() {

					@Override
					public ResolvedOperation visit(GameOperation op) {
						throw new UnsupportedOperationException("game operation " + op.getClass().getSimpleName() + " (" + op.getType() + ") not supported");
					}

					@Override
					public ResolvedOperation visit(GameSignalOperation op) throws GameComponentBuilderException {
						final ResolvedSignalOperation.Editor resolvedOp = ResolvedSignalOperation.create();
						resolvedOp.setRole(role);
						/* no subjects or targets from a SIGNAL */
						return resolvedOp.done();
					}

					private GamePlacePattern placePatternExcludingSubjectPart(final GamePlacePattern target) throws GameComponentBuilderException {
						return StandardAnyPlacePattern.anyQuantityForPart(StandardVariablePartPattern.forToken(TOKEN_SUBJECT).inverted()).and(target);
					}

					@Override
					public ResolvedOperation visit(final GameMoveOperation op) throws GameComponentBuilderException {
						final ResolvedMoveOperation.Editor resolvedOp = ResolvedMoveOperation.create();
						resolvedOp.setRole(role);

						final Stream<? extends GamePlace> _subjects = m_state.find(op.getSubject());
						final Stream<? extends GamePlace> subjects = cloneAndDumpStream(System.out, "subjects", _subjects);

						final GamePlacePattern target = placePatternExcludingSubjectPart(op.getTarget());
						System.out.println("subject: " + op.getSubject());
						cloneAndDumpStream(System.out, "subject", m_state.find(op.getSubject()));

						subjects.forEach((s) -> {

							try {
								resolvedOp.createSubjectTargetPair((m) -> {
									m.setSubject(s);

									try (StandardPatternContext ctx = new StandardPatternContext()) {

										ctx.bindPart(TOKEN_SUBJECT, (p) -> p == s.getOwner());
										final Stream<? extends GamePlace> _targets = m_state.find(target);

										final Stream<? extends GamePlace> targets = cloneAndDumpStream(System.out, "targets", _targets);

										targets.forEach((t) -> {
											if (t.getOwner() == s.getOwner())
												throw new UnsupportedOperationException("cannot move " + t.getOwner() + " to itself");

											m.addTarget(t);
										});
									}
								});
							} catch (final GameComponentBuilderException ex) {
								ex.printStackTrace();
								throw new RuntimeException("failed to construct resolved subject", ex);
							}

						});
						return resolvedOp.done();
					}

				});
				resolvedOps.addOperation(resolvedOp);
			} catch (final GameComponentBuilderException ex) {
				ex.printStackTrace();
				throw new RuntimeException("failed to construct resolved operation", ex);
			}
		});

		return resolvedOps.done();

	}

	public GameStateLogEntry advance(ResolvedOperationSet ops) {
		validateOperationSet(ops);

		final GameStateLogEntry log = new GameStateLogEntry(ops.getResult(), m_state.getCurrentStage());
		ops.operations().forEach((resolvedOp) -> {

			resolvedOp.visit(new ResolvedOperation.Visitor<Void, RuntimeException>() {

				@Override
				public Void visit(ResolvedSignalOperation resolvedOp) throws RuntimeException {
					/* no state mutations from a SIGNAL */
					return null;
				}

				@Override
				public Void visit(ResolvedMoveOperation resolvedOp) throws RuntimeException {
					resolvedOp.subjects().forEach((s) -> {

						/*
						 * remove outgoing location relationship(s), if any
						 * [rollback] restore outgoing location relationship(s),
						 * if any
						 */
						s.getSubject().outgoingRelationships().filter((r) -> r.getType().get() == m_location.get()).forEach((r) -> {
							log.append(new GameStateRemoveRelationship(resolvedOp.getRole(), resolvedOp.getType(), r.getType(), r.getSource().get(),
									r.getDestination().get()));
						});

						s.targets().forEach((t) -> {
							log.append(new GameStateAddRelationship(resolvedOp.getRole(), resolvedOp.getType(), m_location, s.getSubject(), t));
						});
					});
					return null;
				}

			});
		});
		m_state.mutate(log);

		return log;
	}

	public Optional<GameStateLogEntry> autoAdvance() throws GameComponentBuilderException {
		return autoAdvance(AutomaticResolutionStrategy::new);
	}

	public Optional<GameStateLogEntry> autoAdvance(Function<GameState, ResolutionStrategy> strategyFactory) throws GameComponentBuilderException {
		final Iterator<? extends GameOperationPatternSet> i = m_state.allowedOperations().iterator();
		if (!i.hasNext())
			return Optional.empty(); /* literally NO allowed operations */
		final GameOperationPatternSet opPatternSet = i.next();
		if (i.hasNext())
			return Optional
					.empty(); /* more than 1 allowed operation, can't decide */

		final ResolutionStrategy strategy = strategyFactory.apply(m_state);
		final Optional<ResolvedOperationSet> resolvedOpSet = strategy.autoResolve(opPatternSet);
		if (!resolvedOpSet.isPresent())
			return Optional.empty();

		return Optional.of(advance(resolvedOpSet.get()));
	}

	private <T> Stream<T> cloneAndDumpStream(PrintStream ps, String label, Stream<T> items) {
		final List<T> list = items.collect(Collectors.toList());
		ps.println(label + ": " + list);
		return list.stream();
	}

}
