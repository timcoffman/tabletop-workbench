package com.tcoffman.ttwb.plugin;

import java.util.function.Consumer;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GamePlaceType;

public class AbstractModelPlugin extends AbstractPlugin implements ModelPlugin {

	@Override
	public void validate(GameModel model, Consumer<GameComponentBuilderException> reporter) {
		/* nothing to report */
	}

	@Override
	public GameComponentRef<GamePlaceType> getPlaceType(String localName) throws PluginException {
		throw new PluginException(getName(), "no definition for place type \"" + localName + "\"");
	}

	@Override
	public GameComponentRef<GamePartRelationshipType> getRelationshipType(String localName) throws PluginException {
		throw new PluginException(getName(), "no definition for relationship type \"" + localName + "\"");
	}

}
