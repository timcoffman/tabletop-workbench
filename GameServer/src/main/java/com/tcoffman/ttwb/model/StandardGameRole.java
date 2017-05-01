package com.tcoffman.ttwb.model;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;

public class StandardGameRole implements GameRole {

	private StandardGameRole() {
	}

	public static Editor create() {
		return new StandardGameRole().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends AbstractEditor<StandardGameRole> {

		@Override
		protected StandardGameRole model() {
			return StandardGameRole.this;
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			/* no validation necessary */
		}
	}
}
