package com.tcoffman.ttwb.state.pattern;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.pattern.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.GamePlacePattern;

public abstract class StandardPlacePattern implements GamePlacePattern {

	private GamePartPattern m_partPattern;

	protected StandardPlacePattern() {
	}

	@Override
	public GamePartPattern getPartPattern() {
		return m_partPattern;
	}

	public abstract class Editor<T extends StandardPlacePattern> extends AbstractEditor<T> {

		protected Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			if (null == m_partPattern)
				m_partPattern = StandardAnyPartPattern.create().done();
		}

		protected void setPartPatternInternal(GamePartPattern partPattern) {
			requireNotDone();
			m_partPattern = partPattern;
		}

	}

}
