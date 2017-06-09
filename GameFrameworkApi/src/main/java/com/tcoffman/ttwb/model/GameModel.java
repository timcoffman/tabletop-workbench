package com.tcoffman.ttwb.model;

import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.GameDocumentableComponent;
import com.tcoffman.ttwb.plugin.PluginName;

public interface GameModel extends GameStageContainer, GameDocumentableComponent {

	Stream<? extends GameModel> importedModels();

	Stream<? extends GameModel> effectiveImportedModels();

	GameRole getSystemRole();

	Collection<PluginName> getRequiredPlugins();

	Stream<? extends GamePartPrototype> prototypes();

	Stream<? extends GamePartInstance> parts();

	Stream<? extends GameRole> roles();

	GameComponentRef<GameStage> getInitialStage();

	boolean isAbstract();

}
