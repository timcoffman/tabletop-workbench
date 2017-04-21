package com.tcoffman.ttwb.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.plugin.PluginName;

public class StandardGameModelComponent implements GameModelComponent {

	private final PluginName m_declaringPlugin;
	private final String m_localName;
	private final Collection<GameModelProperty> m_properties = new ArrayList<GameModelProperty>();
	private final Collection<GameModelComponent> m_components = new ArrayList<GameModelComponent>();

	@Override
	public PluginName getDeclaringPlugin() {
		return m_declaringPlugin;
	}

	@Override
	public String getLocalName() {
		return m_localName;
	}

	@Override
	public Stream<? extends GameModelProperty> properties() {
		return m_properties.parallelStream();
	}

	@Override
	public Stream<? extends GameModelComponent> components() {
		return m_components.parallelStream();
	}

	public StandardGameModelComponent(PluginName declaringPlugin, String localName) {
		m_declaringPlugin = declaringPlugin;
		m_localName = localName;
	}

	public static Editor create(PluginName declaringPlugin, String localName) {
		return new StandardGameModelComponent(declaringPlugin, localName).edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends AbstractEditor<StandardGameModelComponent> {

		@Override
		protected void validate() throws GameModelBuilderException {
		}

		@Override
		protected StandardGameModelComponent model() {
			return StandardGameModelComponent.this;
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
