package com.tcoffman.ttwb.state.mutation;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GameStage;

public final class ResolvedOperationSet {

	private GameComponentRef<GameStage> m_result;
	private final Collection<ResolvedOperation> m_operations = new ArrayList<ResolvedOperation>();

	public GameComponentRef<GameStage> getResult() {
		return m_result;
	}

	public Stream<? extends ResolvedOperation> operations() {
		return m_operations.stream();
	}

	private ResolvedOperationSet() {

	}

	public static Editor create() {
		return new ResolvedOperationSet().edit();
	}

	public Editor edit() {
		return new Editor();
	}

	public class Editor extends AbstractEditor<ResolvedOperationSet> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			requirePresent(CORE, "result", m_result);
		}

		@Override
		protected ResolvedOperationSet model() {
			return ResolvedOperationSet.this;
		}

		public Editor setResult(GameComponentRef<GameStage> result) {
			requireNotDone();
			m_result = result;
			return this;
		}

		public Editor addOperation(ResolvedOperation operation) {
			requireNotDone();
			m_operations.add(operation);
			return this;
		}

	}

	@Override
	public String toString() {
		return "-> " + m_result.get() + " by " + m_operations;
	}

}