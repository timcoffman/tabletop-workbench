package com.tcoffman.ttwb.state;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.stream.Collectors;

import com.tcoffman.ttwb.core.Core;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.plugin.PluginException;

public class GameRunner {

	private final GameState m_state;
	private final GamePartRelationshipType m_location;

	public GameRunner(GameState state) throws PluginException {
		m_state = state;
		m_location = m_state.requireModelPlugin(CORE).getRelationshipType(Core.RELATIONSHIP_PHYSICAL).get();
	}

	public void step(GameState state) {

		state.allowedOperations().collect(Collectors.toList());

	}

	public void advance(GameOperationSet ops) {
		final GameStateLogEntry log = new GameStateLogEntry(ops.getResult(), m_state.getCurrentStage());
		ops.operations().forEach((op) -> {
			op.visit(new GameOperation.Visitor<Void>() {

				@Override
				public Void visit(GameOperation op) {
					throw new UnsupportedOperationException("game operation " + op.getClass().getSimpleName() + " (" + op.getType() + ") not supported");
				}

				@Override
				public Void visit(GameSignalOperation op) {
					/* no state mutations from a SIGNAL */
					return null;
				}

				@Override
				public Void visit(GameMoveOperation op) {
					final GamePlace subject = op.getSubject();
					final GamePlace target = op.getTarget();
					/*
					 * remove outgoing location relationship , if any
					 */
					subject.outgoingRelationships().filter((r) -> r.getType() == m_location).forEach((r) -> {
						log.append(new GameStateRemoveRelationship(op.getRole(), op.getType(), r.getType(), r.getSource().get(), r.getDestination().get()));
					});
					log.append(new GameStateAddRelationship(op.getRole(), op.getType(), m_location.self(), subject, target));
					return null;
				}

			});
		});

		m_state.mutate(log);

	}
}
