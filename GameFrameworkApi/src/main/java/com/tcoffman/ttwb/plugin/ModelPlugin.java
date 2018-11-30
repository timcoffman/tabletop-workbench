package com.tcoffman.ttwb.plugin;

import static java.util.Collections.emptySet;

import java.util.Set;
import java.util.function.Consumer;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GamePlaceType;

public interface ModelPlugin extends Plugin {

	void validate(GameModel model, Consumer<GameComponentBuilderException> reporter);

	default Set<String> getPlaceTypeLocalNames() {
		return emptySet();
	}

	GameComponentRef<GamePlaceType> getPlaceType(String localName) throws PluginException;

	default Set<String> getRelationshipTypeLocalNames() {
		return emptySet();
	};

	GameComponentRef<GamePartRelationshipType> getRelationshipType(String localName) throws PluginException;

}
