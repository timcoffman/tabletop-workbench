package com.tcoffman.ttwb.model.pattern.role;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;

public class StandardGameAnyRolePattern extends StandardGameRolePattern implements GameAnyRolePattern {

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

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

	@Override
	public String toString() {
		return "ANY ROLE";
	}

}
