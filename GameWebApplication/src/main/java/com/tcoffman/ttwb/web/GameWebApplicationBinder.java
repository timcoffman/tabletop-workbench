package com.tcoffman.ttwb.web;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class GameWebApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(GameStateFileRepository.class).to(GameStateFileRepository.class).in(Singleton.class);
		bind(GameModelFileRepository.class).to(GameModelFileRepository.class).in(Singleton.class);
	}

}
