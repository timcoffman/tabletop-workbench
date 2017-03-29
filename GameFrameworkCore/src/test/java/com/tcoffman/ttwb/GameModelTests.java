package com.tcoffman.ttwb;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.tcoffman.ttwb.plugin.PluginFactory;

public class GameModelTests {

	@Test
	public void canInstantiateGameModel() {
		final PluginFactory pluginFactory = mock(PluginFactory.class);
		final GameModel gameModel = new GameModel(pluginFactory);
		assertThat(gameModel, notNullValue());
	}

}
