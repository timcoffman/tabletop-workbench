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
	private GameStageRef m_result;

	private StandardGameRule() {
	}

	@Override
	public Stream<? extends GameOperationPattern> operationPatterns() {
		return m_operationPatterns.parallelStream();
	}

	@Override
	public GameStageRef getResult() {
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
			if (m_operationPatterns.isEmpty())
				throw new GameModelBuilderException(CORE, "missing operation patterns");
			if (null == m_type)
				throw new GameModelBuilderException(CORE, "missing operation type");
			if (null == m_result)
				throw new IllegalStateException("missing result");
		}

		@Override
		protected StandardGameRule model() {
			return StandardGameRule.this;
		}

		public StandardGameOperationPattern.Editor createOperationPattern() {
			requireNotDone();
			return StandardGameOperationPattern.create().completed(m_operationPatterns::add);
		}

		public void setResult(GameStageRef result) {
			requireNotDone();
			m_result = result;
		}

		public void setType(GameOperation.Type type) {
			requireNotDone();
			m_type = type;
		}

	}

}
