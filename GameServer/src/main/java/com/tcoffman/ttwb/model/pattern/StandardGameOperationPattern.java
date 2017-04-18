package com.tcoffman.ttwb.model.pattern;

import java.util.Optional;

import com.tcoffman.ttwb.model.AbstractEditor;
import com.tcoffman.ttwb.model.GameModelBuilderException;
import com.tcoffman.ttwb.state.GameOperation;
import com.tcoffman.ttwb.state.GameOperation.Type;
import com.tcoffman.ttwb.state.pattern.StandardGameAnyRolePattern;

public class StandardGameOperationPattern implements GameOperationPattern {

	private GameOperation.Type m_type;
	private GameRolePattern m_rolePattern;
	private Optional<GamePartPattern> m_subjectPattern = Optional.empty();
	private Optional<GamePartPlacePattern> m_subjectPlacePattern = Optional.empty();
	private Optional<GamePartPattern> m_targetPattern = Optional.empty();
	private Optional<GamePartPlacePattern> m_targetPlacePattern = Optional.empty();
	private Optional<GamePartPlacePattern> m_quantityPattern = Optional.empty();

	protected StandardGameOperationPattern() {
	}

	@Override
	public GameOperation.Type getType() {
		return m_type;
	}

	@Override
	public GameRolePattern getRolePattern() {
		return m_rolePattern;
	}

	@Override
	public Optional<GamePartPattern> getSubjectPattern() {
		return m_subjectPattern;
	}

	@Override
	public Optional<GamePartPlacePattern> getSubjectPlacePattern() {
		return m_subjectPlacePattern;
	}

	@Override
	public Optional<GamePartPattern> getTargetPattern() {
		return m_targetPattern;
	}

	@Override
	public Optional<GamePartPlacePattern> getTargetPlacePattern() {
		return m_targetPlacePattern;
	}

	@Override
	public Optional<GamePartPlacePattern> getQuantityPattern() {
		return m_quantityPattern;
	}

	public static final Editor create() {
		return new StandardGameOperationPattern().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends AbstractEditor<StandardGameOperationPattern> {

		@Override
		protected StandardGameOperationPattern model() {
			return StandardGameOperationPattern.this;
		}

		@Override
		protected void validate() throws GameModelBuilderException {
			if (null == m_type)
				throw new IllegalStateException("missing operation type");
			if (null == m_rolePattern)
				m_rolePattern = StandardGameAnyRolePattern.create().done();
		}

		public void setType(Type type) {
			requireNotDone();
			m_type = type;
		}

		public void setRolePattern(GameRolePattern rolePattern) {
			requireNotDone();
			m_rolePattern = rolePattern;
		}

		public void setSubjectPattern(GamePartPattern subjectPattern) {
			requireNotDone();
			m_subjectPattern = Optional.of(subjectPattern);
		}

		public void setSubjectPlacePattern(GamePartPlacePattern subjectPlacePattern) {
			requireNotDone();
			m_subjectPlacePattern = Optional.of(subjectPlacePattern);
		}

		public void setTargetPattern(GamePartPattern targetPattern) {
			requireNotDone();
			m_targetPattern = Optional.of(targetPattern);
		}

		public void setTargetPlacePattern(GamePartPlacePattern targetPlacePattern) {
			requireNotDone();
			m_targetPlacePattern = Optional.of(targetPlacePattern);
		}

		public void setQuantityPattern(GamePartPlacePattern quantityPattern) {
			requireNotDone();
			m_quantityPattern = Optional.of(quantityPattern);
		}
	}

}
