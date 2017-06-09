package com.tcoffman.ttwb.model;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.StandardDocumentableComponent;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.doc.StandardComponentDocumentation;
import com.tcoffman.ttwb.plugin.PluginName;

public class StandardRootGameModel extends StandardDocumentableComponent implements GameModel {

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
	public GameRole getSystemRole() {
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

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
			final GameComponentDocumentation documentation = StandardComponentDocumentation.create().setName(GameComponentDocumentation.Format.SHORT, "SYSTEM")
					.done();
			m_systemRole = StandardGameRole.create().setDocumentation(GameComponentRef.wrap(documentation)).done();
		}

	}
}
