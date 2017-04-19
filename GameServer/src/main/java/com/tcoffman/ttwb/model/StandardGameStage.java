package com.tcoffman.ttwb.model;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.plugin.PluginName;

public class StandardGameStage implements GameStage {

	private final Collection<GameRule> m_rules = new ArrayList<GameRule>();
	private final Reference<GameStageContainer> m_container;
	private final Collection<GameStage> m_stages = new ArrayList<GameStage>();
	public boolean m_terminal = false;

	private StandardGameStage(GameStageContainer container) {
		m_container = new WeakReference<GameStageContainer>(container);
	}

	@Override
	public Stream<? extends GameStage> stages() {
		return m_stages.parallelStream();
	}

	@Override
	public GameStageContainer container() {
		return m_container.get();
	}

	public static Editor create(GameStageContainer container) {
		return new StandardGameStage(container).edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public class Editor extends AbstractEditor<StandardGameStage> {

		@Override
		protected void validate() throws GameModelBuilderException {
			/* everything's ok */
		}

		@Override
		protected StandardGameStage model() {
			return StandardGameStage.this;
		}

		public Editor setTerminal(boolean isTerminal) {
			requireNotDone();
			m_terminal = isTerminal;
			return this;
		}

		public Editor createRule(PluginName plugin, Initializer<StandardGameRule.Editor> initializer) throws GameModelBuilderException {
			return configure(StandardGameRule.create().completed(m_rules::add), initializer);
		}

		public Editor createStage(Initializer<StandardGameStage.Editor> initializer) throws GameModelBuilderException {
			return configure(StandardGameStage.create(model()).completed(m_stages::add), initializer);
		}

	}

	@Override
	public boolean isTerminal() {
		return m_terminal;
	}

	@Override
	public Stream<GameRule> rules() {
		return m_rules.parallelStream();
	}

}
