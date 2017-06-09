package com.tcoffman.ttwb.web.resource.state;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.web.GameModelRepository;
import com.tcoffman.ttwb.web.GameStateRepository;
import com.tcoffman.ttwb.web.UnrecognizedValueException;
import com.tcoffman.ttwb.web.resource.AbstractSubresource;
import com.tcoffman.ttwb.web.resource.state.pattern.PatternUtils;

public abstract class AbstractStateSubresource extends AbstractSubresource implements StateContextAwareResource {

	private final GameStateRepository.Bundle m_stateBundle;
	private final PatternUtils m_patternResourceUtils;

	public AbstractStateSubresource(GameStateRepository.Bundle stateBundle) {
		super();
		m_stateBundle = stateBundle;
		m_patternResourceUtils = new PatternUtils(this);
	}

	@Override
	public GameStateRepository.Bundle stateBundle() {
		return m_stateBundle;
	}

	@Override
	public GameModelRepository.Bundle modelBundle() {
		try {
			return m_modelRepository.getBundle(stateBundle().getModelId());
		} catch (final GameComponentBuilderException ex) {
			ex.printStackTrace();
			throw new RuntimeException("failed to load model bundle", ex);
		}
	}

	@Override
	public PatternUtils patternUtils() {
		return m_patternResourceUtils;
	}

	@Override
	public GameComponentRef<GamePlaceType> lookupPlaceType(String placeTypeId) throws UnrecognizedValueException {
		return lookupPlaceType(modelBundle(), placeTypeId);
	}

	@Override
	public GameComponentRef<GamePartRelationshipType> lookupRelationshipType(String relationshipTypeId) throws UnrecognizedValueException {
		return lookupRelationshipType(modelBundle(), relationshipTypeId);
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

	/* State lookups */

	@Override
	public GameComponentRef<GamePart> lookupPart(String partId) throws UnrecognizedValueException {
		return lookupPart(stateBundle(), partId);
	}

	@Override
	public String requirePartId(GameComponentRef<GamePart> part) throws GameComponentBuilderException {
		return requirePartId(stateBundle(), part);
	}

	@Override
	public String requireId(GamePart part) throws GameComponentBuilderException {
		return requireId(stateBundle(), part);
	}

}