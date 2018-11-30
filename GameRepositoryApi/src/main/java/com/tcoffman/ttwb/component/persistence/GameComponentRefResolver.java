package com.tcoffman.ttwb.component.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponent;
import com.tcoffman.ttwb.component.GameComponentRef;

public interface GameComponentRefResolver<T extends GameComponent> {

	Optional<GameComponentRef<T>> lookup(String id);

	Optional<String> lookupId(T component);

	Stream<Map.Entry<String, T>> references();

	default Optional<String> lookupId(GameComponentRef<T> component) {
		return lookupId(component.get());
	}

}