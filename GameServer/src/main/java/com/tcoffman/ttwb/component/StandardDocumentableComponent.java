package com.tcoffman.ttwb.component;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import com.tcoffman.ttwb.doc.GameComponentDocumentation;

public abstract class StandardDocumentableComponent extends StandardComponent implements GameDocumentableComponent {

	private GameComponentRef<GameComponentDocumentation> m_documentation;

	@Override
	public GameComponentDocumentation getDocumentation() {
		return m_documentation.get();
	}

	public abstract class Editor<T extends StandardDocumentableComponent> extends StandardComponent.Editor<T> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
			requirePresent(CORE, "documentation", m_documentation);
		}

		public <E extends Editor<T>> E setDocumentation(GameComponentRef<GameComponentDocumentation> documentation) {
			requireNotDone();
			m_documentation = documentation;
			@SuppressWarnings("unchecked")
			final E self = (E) this;
			return self;
		}
	}

	@Override
	public String toString() {
		return null == m_documentation ? super.toString() : m_documentation.get().toString();
	}

}
