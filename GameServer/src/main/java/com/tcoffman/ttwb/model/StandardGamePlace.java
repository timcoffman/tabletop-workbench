package com.tcoffman.ttwb.model;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class StandardGamePlace implements GamePlace {

	private final Reference<StandardGamePartPrototype> m_prototype;
	private GameComponentRef<GamePlaceType> m_type;

	private StandardGamePlace(StandardGamePartPrototype prototype) {
		m_prototype = new WeakReference<StandardGamePartPrototype>(prototype);
	}

	@Override
	public GamePartPrototype getPart() {
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

		public void setType(GameComponentRef<GamePlaceType> type) {
			requireNotDone();
			m_type = type;
		}

	}

}
