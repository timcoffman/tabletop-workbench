package com.tcoffman.ttwb.component;


public abstract class StandardComponent {

	public abstract class Editor<T extends StandardComponent> extends AbstractEditor<T> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			// no problems, because no data
		}

		@Override
		protected T model() {
			@SuppressWarnings("unchecked")
			final T self = (T) StandardComponent.this;
			return self;
		}

	}

}
