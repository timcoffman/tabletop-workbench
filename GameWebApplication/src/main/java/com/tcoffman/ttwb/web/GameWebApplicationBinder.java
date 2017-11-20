package com.tcoffman.ttwb.web;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class GameWebApplicationBinder extends AbstractBinder {

	public GameWebApplicationBinder() {
		super();
	}

	@Override
	protected void configure() {
		// bind(GameStateFileRepository.class).to(GameStateFileRepository.class).in(Singleton.class);
		// bind(GameModelFileRepository.class).to(GameModelFileRepository.class).in(Singleton.class);
		final GameStateFileRepository stateRepo = new GameStateFileRepository();
		final GameModelFileRepository modelRepo = new GameModelFileRepository();
		bind(stateRepo).to(GameStateFileRepository.class);
		bind(modelRepo).to(GameModelFileRepository.class);
	}

}
