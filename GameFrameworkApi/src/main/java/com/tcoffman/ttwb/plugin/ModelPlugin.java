package com.tcoffman.ttwb.plugin;

import java.util.function.Consumer;

import com.tcoffman.ttwb.model.GameComponentRef;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GameModelBuilderException;
import com.tcoffman.ttwb.model.GamePlaceType;

public interface ModelPlugin extends Plugin {

	void validate(GameModel model, Consumer<GameModelBuilderException> reporter);

	GameComponentRef<GamePlaceType> getPlaceType(String localName) throws PluginException;

}
