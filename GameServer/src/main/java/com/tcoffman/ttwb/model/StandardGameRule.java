package com.tcoffman.ttwb.model;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.model.pattern.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.StandardGameOperationPattern;
import com.tcoffman.ttwb.state.GameOperation;
import com.tcoffman.ttwb.state.GameOperation.Type;

public class StandardGameRule implements GameRule {

	public Collection<GameOperationPattern> m_operationPatterns = new ArrayList<GameOperationPattern>();
	public Type m_type;
	private GameComponentRef<GameStage> m_result;

	private StandardGameRule() {
	}

	@Override
	public Stream<? extends GameOperationPattern> operationPatterns() {
		return m_operationPatterns.parallelStream();
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

	public final class Editor extends AbstractEditor<StandardGameRule> {

		@Override
		protected void validate() throws GameModelBuilderException {
			requireNotEmpty(CORE, "operation patterns", m_operationPatterns);
			requirePresent(CORE, "operation type", m_type);
			requirePresent(CORE, "result", m_result);
		}

		@Override
		protected StandardGameRule model() {
			return StandardGameRule.this;
		}

		public Editor createOperationPattern(Initializer<StandardGameOperationPattern.Editor> initializer) throws GameModelBuilderException {
			return configure(StandardGameOperationPattern.create().completed(m_operationPatterns::add), initializer);
		}

		public void setResult(GameComponentRef<GameStage> result) {
			requireNotDone();
			m_result = result;
		}

		public void setType(GameOperation.Type type) {
			requireNotDone();
			m_type = type;
		}

	}

}
