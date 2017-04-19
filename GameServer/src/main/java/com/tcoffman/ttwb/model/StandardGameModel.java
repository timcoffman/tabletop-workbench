package com.tcoffman.ttwb.model;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.Plugin;
import com.tcoffman.ttwb.plugin.PluginName;

public class StandardGameModel implements GameModel {

	private final Set<PluginName> m_requiredPlugins = new HashSet<PluginName>();
	private final Collection<StandardGamePartPrototype> m_prototypes = new ArrayList<StandardGamePartPrototype>();
	private final Collection<StandardGamePartInstance> m_parts = new ArrayList<StandardGamePartInstance>();
	private final Collection<StandardGameRole> m_roles = new ArrayList<StandardGameRole>();
	private final Collection<GameStage> m_stages = new ArrayList<GameStage>();
	private String m_name;
	private GameComponentRef<GameStage> m_initialStage;

	private StandardGameModel() {
	}

	public static Editor create() {
		return new StandardGameModel().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	@Override
	public String getName() {
		return m_name;
	}

	@Override
	public GameComponentRef<GameStage> getInitialStage() {
		return m_initialStage;
	}

	@Override
	public Stream<? extends GameStage> stages() {
		return m_stages.parallelStream();
	}

	@Override
	public GameModel model() {
		return this;
	}

	@Override
	public Collection<PluginName> getRequiredPlugins() {
		return Collections.unmodifiableSet(m_requiredPlugins);
	}

	@Override
	public Stream<? extends GamePartPrototype> prototypes() {
		return m_prototypes.parallelStream();
	}

	@Override
	public Stream<? extends GamePartInstance> parts() {
		return m_parts.parallelStream();
	}

	@Override
	public Stream<? extends GameRole> roles() {
		return m_roles.parallelStream();
	}

	public final class Editor extends AbstractEditor<GameModel> {

		private final Set<ModelPlugin> m_modelPlugins = new HashSet<ModelPlugin>();

		@Override
		protected GameModel model() {
			return StandardGameModel.this;
		}

		@Override
		protected void validate() throws GameModelBuilderException {
			final Collection<GameModelBuilderException> errors = new ArrayList<GameModelBuilderException>();
			m_modelPlugins.parallelStream().forEach((mp) -> mp.validate(StandardGameModel.this, errors::add));
			if (!errors.isEmpty())
				throw errors.iterator().next();
			requirePresent(CORE, "initial stage", m_initialStage);
		}

		public void setName(String name) {
			requireNotDone();
			m_name = name;
		}

		public void addRequiredPlugin(PluginName name, Plugin plugin) {
			requireNotDone();
			m_requiredPlugins.add(name);
			if (plugin instanceof ModelPlugin)
				m_modelPlugins.add((ModelPlugin) plugin);
		}

		public Editor setInitialStage(GameComponentRef<GameStage> initialStage) {
			requireNotDone();
			m_initialStage = initialStage;
			return this;
		}

		public Editor createRole(Initializer<StandardGameRole.Editor> initializer) throws GameModelBuilderException {
			return configure(StandardGameRole.create().completed(m_roles::add), initializer);
		}

		public Editor createStage(Initializer<StandardGameStage.Editor> initializer) throws GameModelBuilderException {
			return configure(StandardGameStage.create(model()).completed(m_stages::add).completed(this::autoAssignInitialStage), initializer);
		}

		private void autoAssignInitialStage(StandardGameStage stage) {
			if (null == m_initialStage)
				setInitialStage(() -> stage);
		}

		public Editor createPrototype(PluginName declaringPlugin, Initializer<StandardGamePartPrototype.Editor> initializer) throws GameModelBuilderException {
			return configure(StandardGamePartPrototype.create(declaringPlugin).completed(m_prototypes::add), initializer);
		}

		public Editor createInstance(Initializer<StandardGamePartInstance.Editor> initializer) throws GameModelBuilderException {
			return configure(StandardGamePartInstance.create().completed(m_parts::add), initializer);
		}

	}
}
