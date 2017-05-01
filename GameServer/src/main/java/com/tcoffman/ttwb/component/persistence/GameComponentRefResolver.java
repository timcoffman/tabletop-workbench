package com.tcoffman.ttwb.component.persistence;

import java.util.Optional;

import com.tcoffman.ttwb.component.GameComponentRef;

public interface GameComponentRefResolver<T> {

	Optional<GameComponentRef<T>> lookup(String id);

	Optional<String> lookupId(T component);

	default Optional<String> lookupId(GameComponentRef<T> component) {
		return lookupId(component.get());
	}

}