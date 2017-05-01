package com.tcoffman.ttwb.state;

import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.pattern.GameOperationPatternSet;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;

public interface GameState extends StateView {

	GameModel getModel();

	Stream<? extends GameRole> roles();

	StateView roleView(GameRole role);

	GameRole addRole();

	Stream<? extends GameOperationPatternSet> allowedOperations();

	GameParticipant assignRole(GameRole role);

	void mutate(GameStateLogEntry log);

	Stream<GameStateLogEntry> log();

	interface Mutator {
		void createRelationship(GameComponentRef<GamePartRelationshipType> relationshipType, GamePlace subject, GamePlace target);

		void destroyRelationship(GameComponentRef<GamePartRelationshipType> relationshipType, GamePlace subject, GamePlace target);
	}

	ModelPlugin requireModelPlugin(PluginName pluginName) throws PluginException;

}
