package com.tcoffman.ttwb.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.plugin.PluginName;

public class StandardGamePartPrototype implements GamePartPrototype {

	private final PluginName m_declaringPlugin;
	private Optional<GameComponentRef<GamePartPrototype>> m_parent = Optional.empty();
	private final Collection<StandardGamePlace> m_places = new ArrayList<StandardGamePlace>();

	protected StandardGamePartPrototype(PluginName declaringPlugin) {
		m_declaringPlugin = declaringPlugin;
	}

	@Override
	public PluginName getDeclaringPlugin() {
		return m_declaringPlugin;
	}

	@Override
	public Optional<GameComponentRef<GamePartPrototype>> getParent() {
		return m_parent;
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

		public Editor setParent(GameComponentRef<GamePartPrototype> parentRef) {
			requireNotDone();
			m_parent = Optional.of(parentRef);
			return this;
		}

		public Editor createPlace(AbstractEditor.Initializer<StandardGamePlace.Editor> initializer) throws GameModelBuilderException {
			return configure(StandardGamePlace.create(model()).completed(m_places::add), initializer);
		}

	}

}
