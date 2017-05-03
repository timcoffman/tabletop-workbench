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

	Stream<? extends GameParticipant> participants();

	GameParticipant createParticipant(GameRole role);

	StateView participantView(GameParticipant participant);

	Stream<? extends GameOperationPatternSet> allowedOperations();

	void mutate(GameStateLogEntry log);

	Stream<GameStateLogEntry> log();

	interface Mutator {
		void createRelationship(GameComponentRef<GamePartRelationshipType> relationshipType, GamePlace subject, GamePlace target);

		void destroyRelationship(GameComponentRef<GamePartRelationshipType> relationshipType, GamePlace subject, GamePlace target);
	}

	ModelPlugin requireModelPlugin(PluginName pluginName) throws PluginException;

}
