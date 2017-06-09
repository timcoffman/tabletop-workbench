package com.tcoffman.ttwb.web;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class GameWebApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(GameStateRepository.class).to(GameStateRepository.class).in(Singleton.class);
		bind(GameModelRepository.class).to(GameModelRepository.class).in(Singleton.class);
	}

}
