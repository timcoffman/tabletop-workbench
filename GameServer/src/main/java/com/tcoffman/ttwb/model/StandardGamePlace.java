package com.tcoffman.ttwb.model;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.plugin.PluginName;

public class StandardGamePlace implements GamePlace {

	private final Reference<StandardGamePartPrototype> m_prototype;
	private GameComponentRef<GamePlaceType> m_type;
	private final Collection<GameModelProperty> m_properties = new ArrayList<GameModelProperty>();
	private final Collection<GameModelComponent> m_components = new ArrayList<GameModelComponent>();

	private StandardGamePlace(StandardGamePartPrototype prototype) {
		m_prototype = new WeakReference<StandardGamePartPrototype>(prototype);
	}

	@Override
	public Stream<? extends GameModelProperty> properties() {
		return m_properties.parallelStream();
	}

	@Override
	public Stream<? extends GameModelComponent> components() {
		return m_components.parallelStream();
	}

	@Override
	public GamePartPrototype getOwningPrototype() {
		return m_prototype.get();
	}

	@Override
	public GameComponentRef<GamePlaceType> getType() {
		return m_type;
	}

	public static Editor create(StandardGamePartPrototype prototype) {
		return new StandardGamePlace(prototype).edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends AbstractEditor<StandardGamePlace> {

		@Override
		protected void validate() throws GameModelBuilderException {
			requirePresent(CORE, "place type", m_type);
		}

		@Override
		protected StandardGamePlace model() {
			return StandardGamePlace.this;
		}

		public Editor setType(GameComponentRef<GamePlaceType> type) {
			requireNotDone();
			m_type = type;
			return this;
		}

		public Editor createProperty(PluginName declaringPlugin, String localName, AbstractEditor.Initializer<StandardGameModelProperty.Editor> initializer)
				throws GameModelBuilderException {
			return configure(StandardGameModelProperty.create(declaringPlugin, localName).completed(m_properties::add), initializer);
		}

		public Editor createComponent(PluginName declaringPlugin, String localName, AbstractEditor.Initializer<StandardGameModelComponent.Editor> initializer)
				throws GameModelBuilderException {
			return configure(StandardGameModelComponent.create(declaringPlugin, localName).completed(m_components::add), initializer);
		}

	}

}
