package com.tcoffman.ttwb.model;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.StandardDocumentableComponent;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;

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
		public Editor setDocumentation(GameComponentRef<GameComponentDocumentation> documentation) {
			return (Editor) super.setDocumentation(documentation);
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
		}
	}
}
