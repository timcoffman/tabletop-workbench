package com.tcoffman.ttwb.model;

import static com.tcoffman.ttwb.core.Core.PLACE_PHYSICAL_TOP;
import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.StandardDocumentableComponent;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.doc.StandardComponentDocumentation;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.Plugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;

public class StandardRootGameModel extends StandardDocumentableComponent implements GameModel {

	private StandardGamePartPrototype m_rootPrototype;
	private StandardGameRole m_systemRole;

	private StandardRootGameModel() {
	}

	public static Editor create() {
		return new StandardRootGameModel().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	@Override
	public GameComponentRef<GamePartPrototype> effectiveRootPrototype() {
		return GameComponentRef.wrap(m_rootPrototype);
	}

	@Override
	public GameComponentRef<GameStage> getInitialStage() {
		throw new UnsupportedOperationException("initial stage not supported");
	}

	@Override
	public boolean isAbstract() {
		return true;
	}

	@Override
	public Stream<? extends GameModel> importedModels() {
		return Stream.empty();
	}

	@Override
	public Stream<? extends GameModel> effectiveImportedModels() {
		return Stream.empty();
	}

	@Override
	public GameRole effectiveSystemRole() {
		return m_systemRole;
	}

	@Override
	public Stream<? extends GameStage> stages() {
		return Stream.empty();
	}

	@Override
	public GameModel model() {
		return this;
	}

	@Override
	public Collection<PluginName> getRequiredPlugins() {
		return Collections.singleton(CORE);
	}

	@Override
	public Stream<? extends GamePartPrototype> prototypes() {
		return Stream.empty();
	}

	@Override
	public Stream<? extends GamePartInstance> parts() {
		return Stream.empty();
	}

	@Override
	public Stream<? extends GameRole> roles() {
		return Stream.of(m_systemRole);
	}

	public final class Editor extends StandardDocumentableComponent.Editor<StandardRootGameModel> {

		private ModelPlugin m_corePlugin;

		@Override
		public Editor setDocumentation(GameComponentRef<GameComponentDocumentation> documentation) {
			return (Editor) super.setDocumentation(documentation);
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
			requirePresent(CORE, "core plugin", m_corePlugin);
			final GameComponentDocumentation documentation = StandardComponentDocumentation.create().setName(GameComponentDocumentation.Format.SHORT, "SYSTEM")
					.done();
			m_systemRole = StandardGameRole.create().setDocumentation(GameComponentRef.wrap(documentation)).done();
			try {
				final GameComponentRef<GamePlaceType> placeTop = m_corePlugin.getPlaceType(PLACE_PHYSICAL_TOP);
				final GameComponentDocumentation docsRoot = StandardComponentDocumentation.create().setName(GameComponentDocumentation.Format.SHORT, "root")
						.done();
				final GameComponentDocumentation docsRootTop = StandardComponentDocumentation.create().setName(GameComponentDocumentation.Format.SHORT, "top")
						.done();
				m_rootPrototype = StandardGamePartPrototype.create(CORE)
						.createPlace((p) -> p.setType(placeTop).setDocumentation(GameComponentRef.wrap(docsRootTop)))
						.setDocumentation(GameComponentRef.wrap(docsRoot)).done();
			} catch (final PluginException ex) {
				throw new GameComponentBuilderException(CORE, ex);
			}
		}

		public Editor setCorePlugin(Plugin plugin) {
			m_corePlugin = (ModelPlugin) plugin;
			return this;
		}

	}
}
