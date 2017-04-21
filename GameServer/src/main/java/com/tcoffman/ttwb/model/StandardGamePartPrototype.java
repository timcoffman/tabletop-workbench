package com.tcoffman.ttwb.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.plugin.PluginName;

public class StandardGamePartPrototype implements GamePartPrototype {

	private final PluginName m_declaringPlugin;
	private Optional<GameComponentRef<GamePartPrototype>> m_extendsPrototype = Optional.empty();
	private Optional<GameComponentRef<GameRole>> m_roleBinding = Optional.empty();
	private final Collection<StandardGamePlace> m_places = new ArrayList<StandardGamePlace>();

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
	public Optional<GameComponentRef<GamePartPrototype>> getExtends() {
		return m_extendsPrototype;
	}

	@Override
	public Stream<? extends GamePlace> places() {
		return m_places.parallelStream();
	}

	public static Editor create(PluginName declaringPlugin) {
		return new StandardGamePartPrototype(declaringPlugin).edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public class Editor extends AbstractEditor<StandardGamePartPrototype> {

		@Override
		protected void validate() throws GameModelBuilderException {
		}

		@Override
		protected StandardGamePartPrototype model() {
			return StandardGamePartPrototype.this;
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

		public Editor createPlace(AbstractEditor.Initializer<StandardGamePlace.Editor> initializer) throws GameModelBuilderException {
			return configure(StandardGamePlace.create(model()).completed(m_places::add), initializer);
		}

		public Editor setAbstract(boolean isAbstract) {
			// TODO Auto-generated method stub
			return this;
		}

	}

}
