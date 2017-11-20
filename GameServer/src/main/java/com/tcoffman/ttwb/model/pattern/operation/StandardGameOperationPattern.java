package com.tcoffman.ttwb.model.pattern.operation;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.StandardDocumentableComponent;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.model.pattern.role.GameRolePattern;
import com.tcoffman.ttwb.model.pattern.role.StandardGameAnyRolePattern;
import com.tcoffman.ttwb.state.mutation.GameOperation;
import com.tcoffman.ttwb.state.mutation.GameOperation.Type;

public class StandardGameOperationPattern extends StandardDocumentableComponent implements GameOperationPattern {

	private GameOperation.Type m_type;
	private GameRolePattern m_rolePattern;
	private Optional<GamePartPattern> m_subjectPattern = Optional.empty();
	private Optional<GamePlacePattern> m_subjectPlacePattern = Optional.empty();
	private Optional<GamePartPattern> m_targetPattern = Optional.empty();
	private Optional<GamePlacePattern> m_targetPlacePattern = Optional.empty();
	// private Optional<GameQuantityPattern> m_quantityPattern =
	// Optional.empty();

	private StandardGameOperationPattern() {
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
	public Optional<GamePlacePattern> getSubjectPlacePattern() {
		return m_subjectPlacePattern;
	}

	@Override
	public Optional<GamePartPattern> getTargetPattern() {
		return m_targetPattern;
	}

	@Override
	public Optional<GamePlacePattern> getTargetPlacePattern() {
		return m_targetPlacePattern;
	}

	// @Override
	// public Optional<GameQuantityPattern> getQuantityPattern() {
	// return m_quantityPattern;
	// }

	public static final Editor create() {
		return new StandardGameOperationPattern().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardDocumentableComponent.Editor<StandardGameOperationPattern> {

		@Override
		public Editor setDocumentation(GameComponentRef<GameComponentDocumentation> documentation) {
			return (Editor) super.setDocumentation(documentation);
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
			if (null == m_type)
				throw new IllegalStateException("missing operation type");
			if (null == m_rolePattern)
				m_rolePattern = StandardGameAnyRolePattern.create().done();
		}

		public Editor setType(Type type) {
			requireNotDone();
			m_type = type;
			return this;
		}

		public Editor setRolePattern(GameRolePattern rolePattern) {
			requireNotDone();
			m_rolePattern = rolePattern;
			return this;
		}

		public Editor setSubjectPattern(GamePartPattern subjectPattern) {
			requireNotDone();
			m_subjectPattern = Optional.of(subjectPattern);
			return this;
		}

		public Editor setSubjectPlacePattern(GamePlacePattern subjectPlacePattern) {
			requireNotDone();
			m_subjectPlacePattern = Optional.of(subjectPlacePattern);
			return this;
		}

		public Editor setTargetPattern(GamePartPattern targetPattern) {
			requireNotDone();
			m_targetPattern = Optional.of(targetPattern);
			return this;
		}

		public Editor setTargetPlacePattern(GamePlacePattern targetPlacePattern) {
			requireNotDone();
			m_targetPlacePattern = Optional.of(targetPlacePattern);
			return this;
		}

		// public Editor setQuantityPattern(GameQuantityPattern quantityPattern)
		// {
		// requireNotDone();
		// m_quantityPattern = Optional.of(quantityPattern);
		// return this ;
		// }
	}

	@Override
	public String toString() {
		return m_type.toString() + " "
				+ Stream.of(Optional.ofNullable(m_rolePattern), m_subjectPlacePattern, m_subjectPattern, m_targetPlacePattern, m_targetPattern)
						.filter(Optional::isPresent).map(Optional::get).map(Object::toString).collect(Collectors.joining(" AND ", "WHERE ", ""));
	}

}
