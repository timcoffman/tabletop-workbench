package com.tcoffman.ttwb.model.persistance;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.doc.GameModelDocumentation;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefResolver;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.plugin.PluginSet;

public interface GameModelRepository {

	public interface Bundle {

		PluginSet getPluginSet();

		String getModelId();

		GameModelDocumentation getDocumentation();

		DocumentationRefResolver getDocumentationResolver();

		ModelRefResolver getModelRefResolver();

		GameModel getModel();

	}

	Bundle getBundle(String name, String documentationLang) throws GameComponentBuilderException;

	Bundle getBundle(String name) throws GameComponentBuilderException;

	Bundle getBundle(GameModel model) throws GameComponentBuilderException;

}
