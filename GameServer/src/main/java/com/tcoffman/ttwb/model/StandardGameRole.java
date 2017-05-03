package com.tcoffman.ttwb.model;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.StandardDocumentableComponent;

public class StandardGameRole extends StandardDocumentableComponent implements GameRole {

	private StandardGameRole() {
	}

	public static Editor create() {
		return new StandardGameRole().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardDocumentableComponent.Editor<StandardGameRole> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
		}
	}
}
