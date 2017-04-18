package com.tcoffman.ttwb.state;

import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;

public class GameStateException extends PluginException {

	private static final long serialVersionUID = 1L;

	private final GameState m_gameState;

	private static String messageWithPrefix(GameState gameState, String message) {
		if (null == gameState)
			return message;
		else if (null == message)
			return "game \"" + gameState.getModel().getName() + "\"";
		else
			return "game \"" + gameState.getModel().getName() + "\": " + message;
	}

	public GameStateException(PluginName pluginName, GameState gameState) {
		super(pluginName);
		m_gameState = gameState;
	}

	public GameStateException(PluginName pluginName, GameState gameState, String message, Throwable cause) {
		super(pluginName, messageWithPrefix(gameState, message), cause);
		m_gameState = gameState;
	}

	public GameStateException(PluginName pluginName, GameState gameState, String message) {
		super(pluginName, messageWithPrefix(gameState, message));
		m_gameState = gameState;
	}

	public GameStateException(PluginName pluginName, GameState gameState, Throwable cause) {
		super(pluginName, messageWithPrefix(gameState, null), cause);
		m_gameState = gameState;
	}

	public GameState getGameState() {
		return m_gameState;
	}

}
