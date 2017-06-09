package com.tcoffman.ttwb.component;

public interface GameComponent {

	<T extends GameComponent> GameComponentRef<T> self(Class<T> asType);

}
