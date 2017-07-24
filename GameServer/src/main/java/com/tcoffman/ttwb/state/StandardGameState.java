package com.tcoffman.ttwb.state;

import static com.tcoffman.ttwb.core.Core.TOKEN_ROOT;
import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameRule;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.pattern.StandardPatternContext;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPatternSet;
import com.tcoffman.ttwb.model.pattern.operation.StandardGameOperationPattern;
import com.tcoffman.ttwb.model.pattern.operation.StandardGameOperationPatternSet;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.part.StandardFilterPartPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.PluginSet;
import com.tcoffman.ttwb.state.mutation.GameStateLogEntry;

public class StandardGameState implements GameState {

	private final GameModel m_model;
	private final PluginSet m_pluginSet;
	private final Collection<StandardGameParticipant> m_participants = new ArrayList<>();
	private final Collection<StandardGamePart> m_parts = new ArrayList<>();
	private final StandardGamePart m_partRoot;
	private final Collection<GamePartRelationship> m_relationships = new ArrayList<>();
	private GameComponentRef<GameStage> m_currentStage;
	private final List<GameStateLogEntry> m_log = new ArrayList<>();

	public StandardGameState(GameModel model, PluginSet pluginSet) {
		m_model = model;
		m_pluginSet = pluginSet;
		m_currentStage = m_model.getInitialStage();

		final GameComponentRef<GamePartPrototype> rootPrototype = m_model.effectiveRootPrototype();
		m_partRoot = new StandardGamePart(rootPrototype, Optional.empty());
		m_parts.add(m_partRoot);

		m_model.parts().map((i) -> {

			return new StandardGamePart(i.getPrototype(), i.getRoleBinding());

		}).forEach(m_parts::add);
	}

	private void createRelationship(AbstractEditor.Initializer<StandardPartRelationship.Editor> initializer) throws GameComponentBuilderException {
		final StandardPartRelationship.Editor editor = StandardPartRelationship.create();
		editor.completed(m_relationships::add);
		editor.completed((r) -> ((StandardGamePlace) r.getSource().get()).addOutgoingRelationship(r));
		editor.completed((r) -> ((StandardGamePlace) r.getDestination().get()).addIncomingRelationship(r));
		initializer.configure(editor);
		editor.done();
	}

	public void destroyRelationship(GamePartRelationship relationship) {
		m_relationships.remove(relationship);
		((StandardGamePlace) relationship.getSource().get()).removeOutgoingRelationship(relationship);
		((StandardGamePlace) relationship.getDestination().get()).removeIncomingRelationship(relationship);
	}

	public final class Resetter implements AutoCloseable {
		private final List<StandardPartRelationship> m_relationshipsToResolve = new ArrayList<>();

		public Resetter appendLogEntry(GameStateLogEntry logEntry) {
			m_log.add(logEntry);
			return this;
		}

		public Resetter setCurrentStage(GameComponentRef<GameStage> stage) {
			m_currentStage = stage;
			return this;
		}

		public Resetter createRelationship(AbstractEditor.Initializer<StandardPartRelationship.Editor> initializer) throws GameComponentBuilderException {
			final StandardPartRelationship.Editor editor = StandardPartRelationship.create();
			editor.completed(m_relationships::add);
			editor.completed(m_relationshipsToResolve::add);
			initializer.configure(editor);
			editor.done();
			return this;
		}

		private final Collection<GamePart> m_takenParts = new ArrayList<>();
		public final Predicate<GamePart> m_notTaken = (p) -> !m_takenParts.contains(p);

		public GamePart takePart(GamePartPattern pattern) {
			final GamePart part = find(pattern).filter(m_notTaken).findAny().orElseThrow(() -> missingPartPattern(pattern));
			m_takenParts.add(part);
			return part;
		}

		private IllegalArgumentException missingPartPattern(GamePartPattern pattern) {
			return new IllegalArgumentException("no un-taken parts match the pattern \"" + pattern + "\"");
		}

		@Override
		public void close() throws Exception {
			m_relationshipsToResolve.forEach((r) -> {
				((StandardGamePlace) r.getSource().get()).addOutgoingRelationship(r);
				((StandardGamePlace) r.getDestination().get()).addIncomingRelationship(r);
			});
		}
	}

	public Resetter reset() {
		m_log.clear();
		m_relationships.clear();
		return new Resetter();
	}

	@Override
	public GameComponentRef<GameStage> getCurrentStage() {
		return m_currentStage;
	}

	@Override
	public StandardGameParticipant createParticipant(GameRole role, Object authorization) {
		StandardGameParticipant participant = m_participants.stream().filter((s) -> s.getRole() == role).findAny().orElse(null);
		if (null != participant) {
			if (!participant.getAuthorization().equals(authorization))
				throw new IllegalArgumentException("participant already exists for role \"" + role + "\", but authorization does not match");
		} else {
			participant = new StandardGameParticipant(this, role, authorization);
			m_participants.add(participant);
		}
		return participant;
	}

	@Override
	public ModelPlugin requireModelPlugin(PluginName name) throws PluginException {
		return (ModelPlugin) m_pluginSet.requirePlugin(name);
	}

	public GameStateException makeDeadEndException() {
		return new GameStateException(CORE, this, "dead end");
	}

	@Override
	public Stream<? extends GamePart> parts() {
		return m_parts.stream();
	}

	@Override
	public Stream<? extends GamePartRelationship> relationships() {
		return m_relationships.stream();
	}

	@Override
	public GameModel getModel() {
		return m_model;
	}

	@Override
	public Stream<? extends GameParticipant> participants() {
		return m_participants.stream();
	}

	@Override
	public StateView participantView(GameParticipant participant) {
		return this;
	}

	@Override
	public Stream<? extends GameOperationPatternSet> allowedOperations() {
		return m_currentStage.get().rules().map(this::createOperationPatternSet);
	}

	private StandardGameOperationPatternSet createOperationPatternSet(GameRule rule) {
		final StandardGameOperationPatternSet ops = new StandardGameOperationPatternSet(rule.getResult());
		rule.operationPatterns().map(this::createOperationPattern).forEach(ops::add);
		return ops;
	}

	private StandardGameOperationPattern createOperationPattern(GameOperationPattern operationPattern) {
		final StandardGameOperationPattern.Editor editor = StandardGameOperationPattern.create();
		editor.setType(operationPattern.getType());
		editor.setRolePattern(operationPattern.getRolePattern());
		editor.setDocumentation(operationPattern.getDocumentation().self(GameComponentDocumentation.class));
		operationPattern.getQuantityPattern().ifPresent(editor::setQuantityPattern);
		operationPattern.getSubjectPlacePattern().ifPresent(editor::setSubjectPlacePattern);
		operationPattern.getSubjectPattern().ifPresent(editor::setSubjectPattern);
		operationPattern.getTargetPlacePattern().ifPresent(editor::setTargetPlacePattern);
		operationPattern.getTargetPattern().ifPresent(editor::setTargetPattern);
		try {
			return editor.done();
		} catch (final GameComponentBuilderException ex) {
			throw new IllegalArgumentException("cannot copy an ill-formed operation pattern", ex);
		}
	}

	@Override
	public Stream<GameStateLogEntry> log() {
		return m_log.stream();
	}

	@Override
	public void mutate(GameStateLogEntry log) {
		m_log.add(log);
		final Mutator mutator = new Mutator() {

			@Override
			public void createRelationship(GameComponentRef<GamePartRelationshipType> relationshipType, GamePlace source, GamePlace destination) {
				try {
					StandardGameState.this.createRelationship((r) -> {
						r.setType(relationshipType).setSource(source.self(GamePlace.class)).setDestination(destination.self(GamePlace.class));
					});
				} catch (final GameComponentBuilderException ex) {
					ex.printStackTrace();
					throw new IllegalStateException("failed to create relationship", ex);
				}
			}

			@Override
			public void destroyRelationship(GameComponentRef<GamePartRelationshipType> relationshipType, GamePlace source, GamePlace destination) {
				final List<? extends GamePartRelationship> relationshipsToDestroy = source.outgoingRelationships()
						.filter((r) -> r.getType().get() == relationshipType.get()).filter((r) -> r.getDestination().get() == destination)
						.collect(Collectors.toList());
				relationshipsToDestroy.forEach(StandardGameState.this::destroyRelationship);
			}

		};
		log.forwardMutations().forEach((m) -> m.apply(mutator));
		m_currentStage = log.getForward();
	}

	private final QueryExecutor m_executor = new QueryExecutor(() -> m_parts.stream());

	@Override
	public QueryExecutor queryExecutor() {
		return m_executor;
	}

	public @Override Stream<? extends GamePart> find(GamePartPattern pattern) {
		try (StandardPatternContext ctx = new StandardPatternContext()) {
			// ctx.bindPart(TOKEN_ROOT, );
			return m_executor.find(pattern);
		}
	}

	private StandardPatternContext prepareContext(StandardPatternContext ctx) throws GameComponentBuilderException {
		final GamePart root = m_executor.findOne(StandardFilterPartPattern.create().setMatchPrototype(m_model.effectiveRootPrototype()).done());
		ctx.bindPart(TOKEN_ROOT, (p) -> p == root);
		return ctx;
	}

	public @Override Stream<? extends GamePlace> find(GamePlacePattern pattern) {
		try (StandardPatternContext ctx = new StandardPatternContext()) {
			prepareContext(ctx);
			return m_executor.find(pattern);
		} catch (final IllegalArgumentException ex) {
			throw new RuntimeException("failed to match pattern: " + pattern, ex);
		} catch (final GameComponentBuilderException ex) {
			throw new RuntimeException("can't find root part", ex);
		}
	}

	@Override
	public boolean test(GamePlacePattern pattern, GamePart part) {
		try (StandardPatternContext ctx = new StandardPatternContext()) {
			prepareContext(ctx);
			return pattern.matchesParts().test(part);
		} catch (final IllegalArgumentException ex) {
			throw new RuntimeException("failed to test part with predicate", ex);
		} catch (final GameComponentBuilderException ex) {
			throw new RuntimeException("can't find root part", ex);
		}
	}

	@Override
	public boolean test(GamePlacePattern pattern, GamePlace place) {
		try (StandardPatternContext ctx = new StandardPatternContext()) {
			prepareContext(ctx);
			return pattern.matches().test(place);
		} catch (final IllegalArgumentException ex) {
			throw new RuntimeException("failed to test part with predicate", ex);
		} catch (final GameComponentBuilderException ex) {
			throw new RuntimeException("can't find root part", ex);
		}
	}

	@Override
	public boolean test(GamePartPattern pattern, GamePart part) {
		try (StandardPatternContext ctx = new StandardPatternContext()) {
			prepareContext(ctx);
			return pattern.matches().test(part);
		} catch (final IllegalArgumentException ex) {
			throw new RuntimeException("failed to test part with predicate", ex);
		} catch (final GameComponentBuilderException ex) {
			throw new RuntimeException("can't find root part", ex);
		}
	}
}
