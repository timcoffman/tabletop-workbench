package com.tcoffman.ttwb.plugin;

import java.util.function.Consumer;

import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GameModelBuilderException;

public interface ModelPlugin extends Plugin {

	void validate(GameModel model, Consumer<GameModelBuilderException> reporter);

}
