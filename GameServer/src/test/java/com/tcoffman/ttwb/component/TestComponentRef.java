package com.tcoffman.ttwb.component;

import com.tcoffman.ttwb.component.GameComponent;
import com.tcoffman.ttwb.component.GameComponentRef;

public class TestComponentRef {

	public static <T extends GameComponent> GameComponentRef<T> emptyRef() {
		return new GameComponentRef<T>() {
			@Override
			public T get() {
				throw new IllegalArgumentException("cannot get empty reference");
			}
		};
	}

}
