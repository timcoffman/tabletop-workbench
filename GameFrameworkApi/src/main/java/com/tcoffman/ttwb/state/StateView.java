package com.tcoffman.ttwb.state;

import java.util.stream.Stream;

import com.tcoffman.ttwb.model.GameComponentRef;
import com.tcoffman.ttwb.model.GamePart;
import com.tcoffman.ttwb.model.GameStage;

public interface StateView {

	GameComponentRef<GameStage> getCurrentStage();

	Stream<? extends GamePart> parts();

	Stream<? extends GamePartRelationshipInstance> relationships();

}
