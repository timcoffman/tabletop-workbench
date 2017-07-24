package com.tcoffman.ttwb.web.resource.model;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.web.GameModelFileRepository;
import com.tcoffman.ttwb.web.UnrecognizedValueException;

public interface ModelContextAwareResource {

	GameModelFileRepository.Bundle modelBundle();

	String lookupId(GamePartPrototype prototype) throws GameComponentBuilderException;

	String lookupId(GameStage stage) throws GameComponentBuilderException;

	String lookupId(GameRole role) throws GameComponentBuilderException;

	String lookupPartPrototypeId(GameComponentRef<GamePartPrototype> prototype) throws GameComponentBuilderException;

	String lookupStageId(GameComponentRef<GameStage> stage) throws GameComponentBuilderException;

	String lookupRoleId(GameComponentRef<GameRole> role) throws GameComponentBuilderException;

	GameComponentRef<GamePartPrototype> lookupPartPrototype(String prototypeId) throws UnrecognizedValueException;

	GameComponentRef<GameStage> lookupStage(String stageId) throws UnrecognizedValueException;

	GameComponentRef<GameRole> lookupRole(String roleId) throws UnrecognizedValueException;

	GameComponentRef<GamePartRelationshipType> lookupRelationshipType(String relationshipTypeId) throws UnrecognizedValueException;

	GameComponentRef<GamePlaceType> lookupPlaceType(String placeTypeId) throws UnrecognizedValueException;

}
