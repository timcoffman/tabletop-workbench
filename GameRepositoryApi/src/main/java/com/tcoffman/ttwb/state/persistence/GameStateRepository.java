package com.tcoffman.ttwb.state.persistence;

import java.util.function.Function;

import com.tcoffman.ttwb.component.GameComponentBuilderException;

public interface GameStateRepository {

	GameStateBundle getBundle(String stateId, Function<String, ModelProvider> modelProviderFactory) throws GameComponentBuilderException;

}
