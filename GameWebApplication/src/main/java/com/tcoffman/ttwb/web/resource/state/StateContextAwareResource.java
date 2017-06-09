package com.tcoffman.ttwb.web.resource.state;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.web.GameStateRepository;
import com.tcoffman.ttwb.web.UnrecognizedValueException;
import com.tcoffman.ttwb.web.resource.model.ModelContextAwareResource;
import com.tcoffman.ttwb.web.resource.state.pattern.PatternUtils;

public interface StateContextAwareResource extends ModelContextAwareResource {

	GameStateRepository.Bundle stateBundle();

	PatternUtils patternUtils();

	String requireId(GamePart part) throws GameComponentBuilderException;

	String requirePartId(GameComponentRef<GamePart> part) throws GameComponentBuilderException;

	GameComponentRef<GamePart> lookupPart(String partId) throws UnrecognizedValueException;

}