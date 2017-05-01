package com.tcoffman.ttwb.state;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartRelationshipType;

public class StandardPartRelationship implements GamePartRelationship {

	private GameComponentRef<GamePartRelationshipType> m_type;
	private GameComponentRef<GamePlace> m_source;
	private GameComponentRef<GamePlace> m_destination;

	private StandardPartRelationship() {
	}

	@Override
	public GameComponentRef<GamePartRelationshipType> getType() {
		return m_type;
	}

	@Override
	public GameComponentRef<GamePlace> getSource() {
		return m_source;
	}

	@Override
	public GameComponentRef<GamePlace> getDestination() {
		return m_destination;
	}

	public static Editor create() {
		return new StandardPartRelationship().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public class Editor extends AbstractEditor<StandardPartRelationship> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			requirePresent(CORE, "type", m_type);
			requirePresent(CORE, "source", m_source);
			requirePresent(CORE, "destination", m_destination);
		}

		@Override
		protected StandardPartRelationship model() {
			return StandardPartRelationship.this;
		}

		public Editor setType(GameComponentRef<GamePartRelationshipType> type) {
			requireNotDone();
			m_type = type;
			return this;
		}

		public Editor setSource(GameComponentRef<GamePlace> source) {
			requireNotDone();
			m_source = source;
			return this;
		}

		public Editor setDestination(GameComponentRef<GamePlace> destination) {
			requireNotDone();
			m_destination = destination;
			return this;
		}

	}

}
