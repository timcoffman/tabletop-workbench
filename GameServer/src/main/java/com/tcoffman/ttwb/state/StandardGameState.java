package com.tcoffman.ttwb.state;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameRule;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.pattern.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.GameOperationPatternSet;
import com.tcoffman.ttwb.model.pattern.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.GamePlacePattern;
import com.tcoffman.ttwb.model.pattern.StandardGameOperationPattern;
import com.tcoffman.ttwb.model.pattern.StandardGameOperationPatternSet;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.PluginSet;

public class StandardGameState implements GameState {

	private final GameModel m_model;
	private final PluginSet m_pluginSet;
	private final Collection<StandardGameParticipant> m_participants = new ArrayList<StandardGameParticipant>();
	private final Collection<StandardGamePart> m_parts = new ArrayList<StandardGamePart>();
	private final Collection<GamePartRelationship> m_relationships = new ArrayList<GamePartRelationship>();
	private GameComponentRef<GameStage> m_currentStage;
	private final List<GameStateLogEntry> m_log = new ArrayList<GameStateLogEntry>();

	public StandardGameState(GameModel model, PluginFactory pluginFactory) {
		m_model = model;
		m_pluginSet = new PluginSet(pluginFactory);
		m_currentStage = m_model.getInitialStage();

		m_model.parts().map((i) -> {

			return new StandardGamePart(i.getPrototype());

		}).forEach(m_parts::add);
	}

	public final class Resetter {
		public Resetter appendLogEntry(GameStateLogEntry logEntry) {
			m_log.add(logEntry);
			return this;
		}

		public Resetter setCurrentStage(GameComponentRef<GameStage> stage) {
			m_currentStage = stage;
			return this;
		}

		public Resetter createRelationship(AbstractEditor.Initializer<StandardPartRelationship.Editor> initializer) throws GameComponentBuilderException {
			final StandardPartRelationship.Editor r = StandardPartRelationship.create().completed(m_relationships::add);
			initializer.configure(r);
			r.done();
			return this;
		}

		private final Collection<GamePart> m_takenParts = new ArrayList<GamePart>();
		public final Predicate<GamePart> m_notTaken = (p) -> !m_takenParts.contains(p);

		public GamePart takePart(GamePartPattern pattern) {
			final GamePart part = find(pattern).filter(m_notTaken).findAny()
					.orElseThrow(() -> new IllegalArgumentException("no un-taken parts match the pattern \"" + pattern + "\""));
			m_takenParts.add(part);
			return part;
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
	public StandardGameParticipant createParticipant(GameRole role) {
		return new StandardGameParticipant(this, role);
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
		final Mutator mutator = new Mutator() {

			@Override
			public void createRelationship(GameComponentRef<GamePartRelationshipType> relationshipType, GamePlace subject, GamePlace target) {
				System.err.println("creating relationship " + relationshipType + ":" + subject + "->" + target);
			}

			@Override
			public void destroyRelationship(GameComponentRef<GamePartRelationshipType> relationshipType, GamePlace subject, GamePlace target) {
				System.err.println("destroying relationship " + relationshipType + ":" + subject + "->" + target);
			}

		};
		log.forwardMutations().forEach((m) -> m.apply(mutator));
		m_currentStage = log.getForward();
	}

	@Override
	public Stream<? extends GamePart> find(GamePartPattern pattern) {
		return m_parts.stream().filter(pattern.matches());
	}

	@Override
	public Stream<? extends GamePlace> find(GamePlacePattern pattern) {
		return find(pattern.getPartPattern()).flatMap(GamePart::places).filter(pattern.matches());
	}

	@Override
	public GamePart findOne(GamePartPattern pattern) {
		return findOneOrZero(pattern).orElseThrow(() -> new IllegalArgumentException("no places match the pattern \"" + pattern + "\""));
	}

	@Override
	public GamePlace findOne(GamePlacePattern pattern) {
		return findOneOrZero(pattern).orElseThrow(() -> new IllegalArgumentException("no places match the pattern \"" + pattern + "\""));
	}

	@Override
	public Optional<? extends GamePart> findOneOrZero(GamePartPattern pattern) {
		final Iterator<? extends GamePart> i = find(pattern).iterator();
		if (!i.hasNext())
			return Optional.empty();
		else {
			final GamePart p = i.next();
			if (i.hasNext())
				throw new IllegalArgumentException("too many parts match the pattern \"" + pattern + "\"");
			else
				return Optional.of(p);
		}

	}

	@Override
	public Optional<? extends GamePlace> findOneOrZero(GamePlacePattern pattern) {
		find(pattern.getPartPattern()).forEach((p) -> {
			System.err.println("*** " + p);
			p.places().forEach(System.err::println);
		});
		final Iterator<? extends GamePlace> i = find(pattern).iterator();
		if (!i.hasNext())
			return Optional.empty();
		else {
			final GamePlace p = i.next();
			if (i.hasNext())
				throw new IllegalArgumentException("too many places match the pattern \"" + pattern + "\"");
			else
				return Optional.of(p);
		}

	}
}
