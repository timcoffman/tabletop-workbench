package com.tcoffman.ttwb.model;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.Optional;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;

public class StandardGamePartInstance implements GamePartInstance {

	private GameComponentRef<GamePartPrototype> m_prototype;
	public Optional<GameComponentRef<GameRole>> m_binding = Optional.empty();

	private StandardGamePartInstance() {
	}

	@Override
	public GameComponentRef<GamePartPrototype> getPrototype() {
		return m_prototype;
	}

	@Override
	public Optional<GameComponentRef<GameRole>> getRoleBinding() {
		return m_binding;
	}

	public static Editor create() {
		return new StandardGamePartInstance().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public class Editor extends AbstractEditor<StandardGamePartInstance> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			requirePresent(CORE, "prototype", m_prototype);
		}

		@Override
		protected StandardGamePartInstance model() {
			return StandardGamePartInstance.this;
		}

		public Editor setPrototype(GameComponentRef<GamePartPrototype> prototype) {
			requireNotDone();
			m_prototype = prototype;
			return this;
		}

		public Editor setBinding(GameComponentRef<GameRole> binding) {
			requireNotDone();
			m_binding = Optional.of(binding);
			return this;
		}

	}

}
