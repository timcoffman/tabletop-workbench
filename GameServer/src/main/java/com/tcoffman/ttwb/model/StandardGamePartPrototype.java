package com.tcoffman.ttwb.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.StandardDocumentableComponent;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.plugin.PluginName;

public class StandardGamePartPrototype extends StandardDocumentableComponent implements GamePartPrototype {

	private final PluginName m_declaringPlugin;
	private Optional<GameComponentRef<GamePartPrototype>> m_extendsPrototype = Optional.empty();
	private Optional<GameComponentRef<GameRole>> m_roleBinding = Optional.empty();
	private final Collection<StandardGamePlacePrototype> m_places = new ArrayList<>();

	protected StandardGamePartPrototype(PluginName declaringPlugin) {
		m_declaringPlugin = declaringPlugin;
	}

	@Override
	public PluginName getDeclaringPlugin() {
		return m_declaringPlugin;
	}

	@Override
	public Optional<GameComponentRef<GameRole>> getRoleBinding() {
		return m_roleBinding;
	}

	@Override
	public Optional<GameComponentRef<GameRole>> effectiveRoleBinding() {
		if (m_roleBinding.isPresent() || !m_extendsPrototype.isPresent())
			return m_roleBinding;
		else
			return m_extendsPrototype.get().get().getRoleBinding();
	}

	@Override
	public Optional<GameComponentRef<GamePartPrototype>> getExtends() {
		return m_extendsPrototype;
	}

	@Override
	public Stream<? extends GamePlacePrototype> places() {
		return m_places.stream();
	}

	@Override
	public Stream<? extends GamePlacePrototype> effectivePlaces() {
		return Stream.concat(m_places.stream(), m_extendsPrototype.map(GameComponentRef::get).map(GamePartPrototype::places).orElse(Stream.empty()));
	}

	public static Editor create(PluginName declaringPlugin) {
		return new StandardGamePartPrototype(declaringPlugin).edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public class Editor extends StandardDocumentableComponent.Editor<StandardGamePartPrototype> {

		@Override
		public Editor setDocumentation(GameComponentRef<GameComponentDocumentation> documentation) {
			return (Editor) super.setDocumentation(documentation);
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
		}

		public Editor setExtends(GameComponentRef<GamePartPrototype> prototypeRef) {
			requireNotDone();
			m_extendsPrototype = Optional.of(prototypeRef);
			return this;
		}

		public Editor setBinding(GameComponentRef<GameRole> binding) {
			requireNotDone();
			m_roleBinding = Optional.of(binding);
			return this;
		}

		public Editor createPlace(AbstractEditor.Initializer<StandardGamePlacePrototype.Editor> initializer) throws GameComponentBuilderException {
			return configure(StandardGamePlacePrototype.create(model()).completed(m_places::add), initializer);
		}

		public Editor setAbstract(boolean isAbstract) {
			// TODO Auto-generated method stub
			return this;
		}

	}

}
