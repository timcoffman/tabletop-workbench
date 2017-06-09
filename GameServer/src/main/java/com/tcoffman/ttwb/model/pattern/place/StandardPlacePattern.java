package com.tcoffman.ttwb.model.pattern.place;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;

public abstract class StandardPlacePattern implements GamePlacePattern {

	protected StandardPlacePattern() {
	}

	public abstract class Editor<T extends StandardPlacePattern> extends AbstractEditor<T> {
		protected Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			/* nothing to check */
		}

		@Override
		protected T model() {
			@SuppressWarnings("unchecked")
			final T self = (T) StandardPlacePattern.this;
			return self;
		}

	}

	public GamePlacePattern inverted() throws GameComponentBuilderException {
		return StandardInversionPlacePattern.create().setPattern(this).done();
	}

	public StandardPlacePattern and(GamePlacePattern pattern) throws GameComponentBuilderException {
		return StandardIntersectionPlacePattern.create().addPattern(this).addPattern(pattern).done();
	}
}
