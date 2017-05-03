package com.tcoffman.ttwb.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.StandardPluginComponent;
import com.tcoffman.ttwb.plugin.PluginName;

public class StandardGameModelComponent extends StandardPluginComponent implements GameModelComponent {

	private final Collection<GameModelProperty> m_properties = new ArrayList<GameModelProperty>();
	private final Collection<GameModelComponent> m_components = new ArrayList<GameModelComponent>();

	@Override
	public Stream<? extends GameModelProperty> properties() {
		return m_properties.stream();
	}

	@Override
	public Stream<? extends GameModelComponent> components() {
		return m_components.stream();
	}

	public StandardGameModelComponent(PluginName declaringPlugin, String localName) {
		super(declaringPlugin, localName);
	}

	public static Editor create(PluginName declaringPlugin, String localName) {
		return new StandardGameModelComponent(declaringPlugin, localName).edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends AbstractEditor<StandardGameModelComponent> {

		@Override
		protected void validate() throws GameComponentBuilderException {
		}

		@Override
		protected StandardGameModelComponent model() {
			return StandardGameModelComponent.this;
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
