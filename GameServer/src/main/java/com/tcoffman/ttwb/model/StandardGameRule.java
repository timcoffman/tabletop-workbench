package com.tcoffman.ttwb.model;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.StandardDocumentableComponent;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.operation.StandardGameOperationPattern;

public class StandardGameRule extends StandardDocumentableComponent implements GameRule {

	public Collection<GameOperationPattern> m_operationPatterns = new ArrayList<>();
	private GameComponentRef<GameStage> m_result;

	private StandardGameRule() {
	}

	@Override
	public Stream<? extends GameOperationPattern> operationPatterns() {
		return m_operationPatterns.stream();
	}

	@Override
	public GameComponentRef<GameStage> getResult() {
		return m_result;
	}

	public static Editor create() {
		return new StandardGameRule().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardDocumentableComponent.Editor<StandardGameRule> {

		@Override
		public Editor setDocumentation(GameComponentRef<GameComponentDocumentation> documentation) {
			return (Editor) super.setDocumentation(documentation);
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
			requireNotEmpty(CORE, "operation patterns", m_operationPatterns);
			requirePresent(CORE, "result", m_result);
		}

		public Editor createOperationPattern(Initializer<StandardGameOperationPattern.Editor> initializer) throws GameComponentBuilderException {
			return configure(StandardGameOperationPattern.create().completed(m_operationPatterns::add), initializer);
		}

		public void setResult(GameComponentRef<GameStage> result) {
			requireNotDone();
			m_result = result;
		}

	}

}
