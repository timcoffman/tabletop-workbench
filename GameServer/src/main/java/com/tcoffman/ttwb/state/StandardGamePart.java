package com.tcoffman.ttwb.state;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.StandardComponent;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePlaceType;

public class StandardGamePart extends StandardComponent implements GamePart {

	private final GameComponentRef<GamePartPrototype> m_prototype;
	private final Collection<GamePlace> m_places = new ArrayList<GamePlace>();

	public StandardGamePart(GameComponentRef<GamePartPrototype> prototype) {
		m_prototype = prototype;
		m_prototype.get().effectivePlaces().map((p) -> new StandardGamePlace(p.self(), new WeakReference<StandardGamePart>(StandardGamePart.this)))
				.forEach(m_places::add);
	}

	@Override
	public GameComponentRef<GamePartPrototype> getPrototype() {
		return m_prototype;
	}

	@Override
	public Stream<? extends GamePlace> places() {
		return m_places.stream();
	}

	@Override
	public GamePlace findPlace(GameComponentRef<GamePlaceType> placeType) {
		return m_places.stream().filter((p) -> p.getPrototype().get().getType().get() == placeType.get()).findAny()
				.orElseThrow(() -> new IllegalArgumentException(StandardGamePart.this + " does not have a place of type \"" + placeType.get() + "\""));
	}

	@Override
	public String toString() {
		return m_prototype.get().toString();
	}

}
