package com.tcoffman.ttwb.component;

public abstract class StandardComponent implements GameComponent {

	@Override
	public <T extends GameComponent> GameComponentRef<T> self(Class<T> asType) {
		return new ComponentSelfRef<T>(asType);
	}

	private final class ComponentSelfRef<T extends GameComponent> extends GameComponentRef<T> {
		public ComponentSelfRef(Class<T> asType) {
			asType.cast(StandardComponent.this);
		}

		@Override
		public T get() {
			@SuppressWarnings("unchecked")
			final T self = (T) StandardComponent.this;
			return self;
		}
	}

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
