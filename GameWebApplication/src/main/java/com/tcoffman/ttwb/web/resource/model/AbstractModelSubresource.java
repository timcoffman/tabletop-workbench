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
import com.tcoffman.ttwb.web.resource.AbstractSubresource;

public abstract class AbstractModelSubresource extends AbstractSubresource implements ModelContextAwareResource {

	private final GameModelFileRepository.Bundle m_modelBundle;

	public AbstractModelSubresource(GameModelFileRepository.Bundle modelBundle) {
		super();
		m_modelBundle = modelBundle;
	}

	@Override
	public GameModelFileRepository.Bundle modelBundle() {
		return m_modelBundle;
	}

	@Override
	public GameComponentRef<GamePlaceType> lookupPlaceType(String placeTypeId) throws UnrecognizedValueException {
		return lookupPlaceType(modelBundle(), placeTypeId);
	}

	@Override
	public GameComponentRef<GameRole> lookupRole(String roleId) throws UnrecognizedValueException {
		return lookupRole(modelBundle(), roleId);
	}

	@Override
	public GameComponentRef<GameStage> lookupStage(String stageId) throws UnrecognizedValueException {
		return lookupStage(modelBundle(), stageId);
	}

	@Override
	public GameComponentRef<GamePartPrototype> lookupPartPrototype(String prototypeId) throws UnrecognizedValueException {
		return lookupPartPrototype(modelBundle(), prototypeId);
	}

	@Override
	public String lookupRoleId(GameComponentRef<GameRole> role) throws GameComponentBuilderException {
		return lookupRoleId(modelBundle(), role);
	}

	@Override
	public String lookupStageId(GameComponentRef<GameStage> stage) throws GameComponentBuilderException {
		return lookupStageId(modelBundle(), stage);
	}

	@Override
	public String lookupPartPrototypeId(GameComponentRef<GamePartPrototype> prototype) throws GameComponentBuilderException {
		return lookupPartPrototypeId(modelBundle(), prototype);
	}

	@Override
	public String lookupId(GameRole role) throws GameComponentBuilderException {
		return lookupId(modelBundle(), role);
	}

	@Override
	public String lookupId(GameStage stage) throws GameComponentBuilderException {
		return lookupId(modelBundle(), stage);
	}

	@Override
	public String lookupId(GamePartPrototype prototype) throws GameComponentBuilderException {
		return lookupId(modelBundle(), prototype);
	}

	@Override
	public GameComponentRef<GamePartRelationshipType> lookupRelationshipType(String relationshipTypeId) throws UnrecognizedValueException {
		return null;
	}

}