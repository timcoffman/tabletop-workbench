package com.tcoffman.ttwb.model;

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
		protected void validate() throws GameModelBuilderException {
			/* no validation necessary */
		}
	}
}
