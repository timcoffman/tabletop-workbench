package com.tcoffman.ttwb.state.pattern;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.pattern.GameAnyRolePattern;
import com.tcoffman.ttwb.model.pattern.StandardGameRolePattern;

public class StandardGameAnyRolePattern extends StandardGameRolePattern implements GameAnyRolePattern {

	public static final Editor create() {
		return INSTANCE.edit();
	}

	private StandardGameAnyRolePattern() {

	}

	private static final StandardGameAnyRolePattern INSTANCE = new StandardGameAnyRolePattern();

	private Editor edit() {
		return new Editor();
	}

	public class Editor extends AbstractEditor<StandardGameAnyRolePattern> {

		@Override
		protected void validate() throws GameComponentBuilderException {
		}

		@Override
		protected StandardGameAnyRolePattern model() {
			return StandardGameAnyRolePattern.this;
		}

	}

}
