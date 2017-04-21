package com.tcoffman.ttwb.model;

import com.tcoffman.ttwb.plugin.PluginName;

public class StandardGameModelProperty implements GameModelProperty {

	private final PluginName m_declaringPlugin;
	private final String m_localName;
	private String m_value;

	@Override
	public PluginName getDeclaringPlugin() {
		return m_declaringPlugin;
	}

	@Override
	public String getLocalName() {
		return m_localName;
	}

	@Override
	public String getValue() {
		return m_value;
	}

	public StandardGameModelProperty(PluginName declaringPlugin, String localName) {
		m_declaringPlugin = declaringPlugin;
		m_localName = localName;
	}

	public static Editor create(PluginName declaringPlugin, String localName) {
		return new StandardGameModelProperty(declaringPlugin, localName).edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends AbstractEditor<StandardGameModelProperty> {

		@Override
		protected void validate() throws GameModelBuilderException {
			requirePresent(m_declaringPlugin, "value", m_value);
		}

		@Override
		protected StandardGameModelProperty model() {
			return StandardGameModelProperty.this;
		}

		public Editor setValue(String value) {
			requireNotDone();
			m_value = value;
			return this;
		}

	}
}
