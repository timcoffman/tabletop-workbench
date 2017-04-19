package com.tcoffman.ttwb.state;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.tcoffman.ttwb.model.GameComponentRef;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GameModelBuilderException;
import com.tcoffman.ttwb.model.GamePart;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameRule;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.pattern.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.GameOperationPatternSet;
import com.tcoffman.ttwb.model.pattern.StandardGameOperationPattern;
import com.tcoffman.ttwb.model.pattern.StandardGameOperationPatternSet;
import com.tcoffman.ttwb.plugin.Plugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.PluginSet;

public class StandardGameState implements GameState {

	private final GameModel m_model;
	private final PluginSet m_pluginSet;
	private final Map<Long, StandardGamePart> m_parts = new HashMap<Long, StandardGamePart>();
	private GameComponentRef<GameStage> m_currentStage;

	public StandardGameState(GameModel model, PluginFactory pluginFactory) {
		m_model = model;
		m_pluginSet = new PluginSet(pluginFactory);
		m_currentStage = m_model.getInitialStage();
	}

	@Override
	public GameComponentRef<GameStage> getCurrentStage() {
		return m_currentStage;
	}

	@Override
	public StandardGameParticipant assignRole(GameRole role) {
		return new StandardGameParticipant(role);
	}

	private Plugin requirePlugin(PluginName name) throws PluginException {
		return m_pluginSet.requirePlugin(name);
	}

	public GameStateException makeDeadEndException() {
		return new GameStateException(CORE, this, "dead end");
	}

	@Override
	public Stream<? extends GamePart> parts() {
		return m_parts.values().stream();
	}

	@Override
	public Stream<? extends GamePartRelationship> relationships() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel getModel() {
		return m_model;
	}

	@Override
	public Stream<? extends GameRole> roles() {
		return Stream.empty();
	}

	@Override
	public StateView roleView(GameRole role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameRole addRole() {
		return null;
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
		operationPattern.getSubjectPattern().ifPresent(editor::setSubjectPattern);
		operationPattern.getTargetPattern().ifPresent(editor::setTargetPattern);
		try {
			return editor.done();
		} catch (final GameModelBuilderException ex) {
			throw new IllegalArgumentException("cannot copy an ill-formed operation pattern", ex);
		}
	}

	@Override
	public void mutate(GameStateLogEntry log) {
		log.forwardMutations().forEach(this::mutate);
		m_currentStage = log.getForward();
	}

	public void mutate(GameStateMutation mutation) {

	}
}
