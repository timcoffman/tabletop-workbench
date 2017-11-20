package com.tcoffman.ttwb.model;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
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

public class StandardGamePlacePrototype extends StandardDocumentableComponent implements GamePlacePrototype {

	private final Reference<StandardGamePartPrototype> m_prototype;
	private GameComponentRef<GamePlaceType> m_type;
	private Optional<GameComponentRef<GameRole>> m_roleBinding = Optional.empty();
	private final Collection<GameModelProperty> m_properties = new ArrayList<>();
	private final Collection<GameModelComponent> m_components = new ArrayList<>();

	private StandardGamePlacePrototype(StandardGamePartPrototype prototype) {
		m_prototype = new WeakReference<>(prototype);
	}

	@Override
	public Optional<GameComponentRef<GameRole>> getRoleBinding() {
		return m_roleBinding;
	}

	@Override
	public Stream<? extends GameModelProperty> properties() {
		return m_properties.stream();
	}

	@Override
	public Stream<? extends GameModelComponent> components() {
		return m_components.stream();
	}

	@Override
	public GamePartPrototype getOwner() {
		return m_prototype.get();
	}

	@Override
	public GameComponentRef<GamePlaceType> getType() {
		return m_type;
	}

	public static Editor create(StandardGamePartPrototype prototype) {
		return new StandardGamePlacePrototype(prototype).edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardDocumentableComponent.Editor<StandardGamePlacePrototype> {

		@Override
		public Editor setDocumentation(GameComponentRef<GameComponentDocumentation> documentation) {
			return (Editor) super.setDocumentation(documentation);
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
			requirePresent(CORE, "place type", m_type);
		}

		public Editor setType(GameComponentRef<GamePlaceType> type) {
			requireNotDone();
			m_type = type;
			return this;
		}

		public Editor setBinding(GameComponentRef<GameRole> binding) {
			requireNotDone();
			m_roleBinding = Optional.of(binding);
			return this;
		}

		public Editor createProperty(PluginName declaringPlugin, String localName, AbstractEditor.Initializer<StandardGameModelProperty.Editor> initializer)
				throws GameComponentBuilderException {
			return configure(StandardGameModelProperty.create(declaringPlugin, localName).completed(m_properties::add), initializer);
		}

		public Editor createComponent(PluginName declaringPlugin, String localName, AbstractEditor.Initializer<StandardGameModelComponent.Editor> initializer)
				throws GameComponentBuilderException {
			return configure(StandardGameModelComponent.create(declaringPlugin, localName).completed(m_components::add), initializer);
		}

	}

}
