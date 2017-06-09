package com.tcoffman.ttwb.doc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.StandardComponent;

public class StandardModelDocumentation extends StandardComponent implements GameModelDocumentation {

	private GameComponentDocumentation m_model;
	private final Collection<GameComponentDocumentation> m_prototypes = new ArrayList<GameComponentDocumentation>();
	private final Collection<GameComponentDocumentation> m_stages = new ArrayList<GameComponentDocumentation>();
	private final Collection<GameComponentDocumentation> m_roles = new ArrayList<GameComponentDocumentation>();
	private final Collection<GameComponentDocumentation> m_rules = new ArrayList<GameComponentDocumentation>();
	private final Collection<GameComponentDocumentation> m_operations = new ArrayList<GameComponentDocumentation>();

	@Override
	public GameComponentDocumentation model() {
		return m_model;
	}

	@Override
	public Stream<? extends GameComponentDocumentation> prototypes() {
		return m_prototypes.stream();
	}

	@Override
	public Stream<? extends GameComponentDocumentation> stages() {
		return m_stages.stream();
	}

	@Override
	public Stream<? extends GameComponentDocumentation> roles() {
		return m_roles.stream();
	}

	@Override
	public Stream<? extends GameComponentDocumentation> rules() {
		return m_rules.stream();
	}

	@Override
	public Stream<? extends GameComponentDocumentation> operations() {
		return m_operations.stream();
	}

	public static Editor create() {
		return new StandardModelDocumentation().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public class Editor extends StandardComponent.Editor<StandardModelDocumentation> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
		}

		public Editor createModel(AbstractEditor.Initializer<StandardComponentDocumentation.Editor> initializer) throws GameComponentBuilderException {
			return configure(StandardComponentDocumentation.create().completed((d) -> m_model = d), initializer);
		}

		public Editor createPrototype(AbstractEditor.Initializer<StandardComponentDocumentation.Editor> initializer) throws GameComponentBuilderException {
			return configure(StandardComponentDocumentation.create().completed(m_prototypes::add), initializer);
		}

		public Editor createStage(AbstractEditor.Initializer<StandardComponentDocumentation.Editor> initializer) throws GameComponentBuilderException {
			return configure(StandardComponentDocumentation.create().completed(m_stages::add), initializer);
		}

		public Editor createRule(AbstractEditor.Initializer<StandardComponentDocumentation.Editor> initializer) throws GameComponentBuilderException {
			return configure(StandardComponentDocumentation.create().completed(m_rules::add), initializer);
		}

		public Editor createOperation(AbstractEditor.Initializer<StandardComponentDocumentation.Editor> initializer) throws GameComponentBuilderException {
			return configure(StandardComponentDocumentation.create().completed(m_operations::add), initializer);
		}

		public Editor createRole(AbstractEditor.Initializer<StandardComponentDocumentation.Editor> initializer) throws GameComponentBuilderException {
			return configure(StandardComponentDocumentation.create().completed(m_roles::add), initializer);
		}
	}
}
