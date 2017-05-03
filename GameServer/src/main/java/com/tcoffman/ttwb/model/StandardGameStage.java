package com.tcoffman.ttwb.model;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.StandardDocumentableComponent;
import com.tcoffman.ttwb.plugin.PluginName;

public class StandardGameStage extends StandardDocumentableComponent implements GameStage {

	private final Collection<GameRule> m_rules = new ArrayList<GameRule>();
	private final Reference<GameStageContainer> m_owner;
	private final Collection<GameStage> m_stages = new ArrayList<GameStage>();
	public boolean m_terminal = false;

	private StandardGameStage(GameStageContainer owner) {
		m_owner = new WeakReference<GameStageContainer>(owner);
	}

	@Override
	public Stream<? extends GameStage> stages() {
		return m_stages.stream();
	}

	@Override
	public boolean isTerminal() {
		return m_terminal;
	}

	@Override
	public Stream<GameRule> rules() {
		return m_rules.stream();
	}

	@Override
	public GameStageContainer container() {
		return m_owner.get();
	}

	public static Editor create(GameStageContainer owner) {
		return new StandardGameStage(owner).edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public class Editor extends StandardDocumentableComponent.Editor<StandardGameStage> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
		}

		public Editor setTerminal(boolean isTerminal) {
			requireNotDone();
			m_terminal = isTerminal;
			return this;
		}

		public Editor createRule(PluginName plugin, Initializer<StandardGameRule.Editor> initializer) throws GameComponentBuilderException {
			return configure(StandardGameRule.create().completed(m_rules::add), initializer);
		}

		public Editor createStage(Initializer<StandardGameStage.Editor> initializer) throws GameComponentBuilderException {
			return configure(StandardGameStage.create(model()).completed(m_stages::add), initializer);
		}

	}

}
