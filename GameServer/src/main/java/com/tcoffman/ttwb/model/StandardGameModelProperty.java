package com.tcoffman.ttwb.model;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.StandardPluginComponent;
import com.tcoffman.ttwb.plugin.PluginName;

public class StandardGameModelProperty extends StandardPluginComponent implements GameModelProperty {

	private String m_value;

	@Override
	public String getValue() {
		return m_value;
	}

	public StandardGameModelProperty(PluginName declaringPlugin, String localName) {
		super(declaringPlugin, localName);
	}

	public static Editor create(PluginName declaringPlugin, String localName) {
		return new StandardGameModelProperty(declaringPlugin, localName).edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends AbstractEditor<StandardGameModelProperty> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			requirePresent(getDeclaringPlugin(), "value", m_value);
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
