package com.tcoffman.ttwb.model;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.StandardDocumentableComponent;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.Plugin;
import com.tcoffman.ttwb.plugin.PluginName;

public class StandardGameModel extends StandardDocumentableComponent implements GameModel {

	private final Set<PluginName> m_requiredPlugins = new HashSet<>();
	private final Collection<GameModel> m_imports = new ArrayList<>();
	private final Collection<StandardGamePartPrototype> m_prototypes = new ArrayList<>();
	private final Collection<StandardGamePartInstance> m_parts = new ArrayList<>();
	private final Collection<StandardGameRole> m_roles = new ArrayList<>();
	private final Collection<GameStage> m_stages = new ArrayList<>();
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
	public boolean isAbstract() {
		return false;
	}

	@Override
	public GameComponentRef<GameStage> getInitialStage() {
		return m_initialStage;
	}

	@Override
	public Stream<? extends GameModel> importedModels() {
		return m_imports.stream();
	}

	@Override
	public GameComponentRef<GamePartPrototype> effectiveRootPrototype() {
		return m_imports.stream().map(GameModel::effectiveRootPrototype).filter(Objects::nonNull).findAny()
				.orElseThrow(() -> new IllegalStateException("missing root prototype"));
	}

	@Override
	public Stream<? extends GameModel> effectiveImportedModels() {
		return Stream.concat(m_imports.stream(), m_imports.stream().flatMap(GameModel::effectiveImportedModels)).distinct();
	}

	@Override
	public GameRole effectiveSystemRole() {
		return effectiveImportedModels().map(GameModel::effectiveSystemRole).findFirst().orElseThrow(() -> new IllegalStateException("missing system role"));
	}

	@Override
	public Stream<? extends GameStage> stages() {
		return m_stages.stream();
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
		return m_prototypes.stream();
	}

	@Override
	public Stream<? extends GamePartInstance> parts() {
		return m_parts.stream();
	}

	@Override
	public Stream<? extends GameRole> roles() {
		return m_roles.stream();
	}

	public final class Editor extends StandardDocumentableComponent.Editor<StandardGameModel> {

		private final Set<ModelPlugin> m_modelPlugins = new HashSet<>();

		@Override
		public Editor setDocumentation(GameComponentRef<GameComponentDocumentation> documentation) {
			return (Editor) super.setDocumentation(documentation);
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
			final Collection<GameComponentBuilderException> errors = new ArrayList<>();

			requireNotEmpty(CORE, "imported models", m_imports);
			m_modelPlugins.stream().forEach((mp) -> mp.validate(StandardGameModel.this, errors::add));
			if (!errors.isEmpty())
				throw errors.iterator().next();
			requirePresent(CORE, "initial stage", m_initialStage);
		}

		public void addRequiredPlugin(Plugin plugin) {
			requireNotDone();
			final PluginName pluginName = plugin.getName();
			if (null == pluginName)
				throw new IllegalStateException("cannot require a plugin (" + plugin.getClass().getName() + ") that has no name");
			m_requiredPlugins.add(pluginName);
			if (plugin instanceof ModelPlugin)
				m_modelPlugins.add((ModelPlugin) plugin);
		}

		public Editor setInitialStage(GameComponentRef<GameStage> initialStage) {
			requireNotDone();
			m_initialStage = initialStage;
			return this;
		}

		public Editor addImportedModel(GameModel importedModel) {
			requireNotDone();
			m_imports.add(importedModel);
			return this;
		}

		public Editor createRole(Initializer<StandardGameRole.Editor> initializer) throws GameComponentBuilderException {
			return configure(StandardGameRole.create().completed(m_roles::add), initializer);
		}

		public Editor createStage(Initializer<StandardGameStage.Editor> initializer) throws GameComponentBuilderException {
			return configure(StandardGameStage.create(model()).completed(m_stages::add).completed(this::autoAssignInitialStage), initializer);
		}

		private void autoAssignInitialStage(StandardGameStage stage) {
			if (null == m_initialStage)
				setInitialStage(stage.self(GameStage.class));
		}

		public Editor createPrototype(PluginName declaringPlugin, Initializer<StandardGamePartPrototype.Editor> initializer)
				throws GameComponentBuilderException {
			return configure(StandardGamePartPrototype.create(declaringPlugin).completed(m_prototypes::add), initializer);
		}

		public Editor createPart(Initializer<StandardGamePartInstance.Editor> initializer) throws GameComponentBuilderException {
			return configure(StandardGamePartInstance.create().completed(m_parts::add), initializer);
		}

	}
}
