package com.tcoffman.ttwb.model.pattern.part;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;

public abstract class StandardPartPattern implements GamePartPattern {

	public abstract class Editor<T extends StandardPartPattern> extends AbstractEditor<T> {

		protected Editor() {
		}

		@Override
		protected T model() {
			@SuppressWarnings("unchecked")
			final T self = (T) StandardPartPattern.this;
			return self;
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
		}

	}

	public GamePartPattern inverted() throws GameComponentBuilderException {
		return StandardInversionPartPattern.create().setPattern(this).done();
	}

	public StandardPartPattern and(GamePartPattern pattern) throws GameComponentBuilderException {
		return StandardIntersectionPartPattern.create().addPattern(this).addPattern(pattern).done();
	}

}
