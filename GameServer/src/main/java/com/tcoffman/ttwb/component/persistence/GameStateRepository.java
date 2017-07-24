package com.tcoffman.ttwb.component.persistence;

import java.util.function.Function;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.state.persistence.xml.StandardGameStateParser.ModelProvider;

public interface GameStateRepository {

	GameStateBundle getBundle(String stateId, Function<String, ModelProvider> modelProviderFactory) throws GameComponentBuilderException;

}
