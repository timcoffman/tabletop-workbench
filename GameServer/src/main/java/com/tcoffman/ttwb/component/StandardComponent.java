package com.tcoffman.ttwb.component;

public abstract class StandardComponent implements GameComponent {

	@Override
	public <T extends GameComponent> GameComponentRef<T> self() {
		return new ComponentSelfRef<T>();
	}

	private final class ComponentSelfRef<T extends GameComponent> extends GameComponentRef<T> {
		@Override
		public T get() {
			return (T) StandardComponent.this;
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
