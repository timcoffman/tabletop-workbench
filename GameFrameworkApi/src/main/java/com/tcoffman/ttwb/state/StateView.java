package com.tcoffman.ttwb.state;

import java.util.stream.Stream;

import com.tcoffman.ttwb.model.GamePart;
import com.tcoffman.ttwb.model.GameStageRef;

public interface StateView {

	GameStageRef getCurrentStage();

	Stream<? extends GamePart> parts();

	Stream<? extends GamePartRelationship> relationships();

}
