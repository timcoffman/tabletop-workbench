package com.tcoffman.ttwb.state;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.StandardComponent;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePlacePrototype;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;

public class StandardGamePart extends StandardComponent implements GamePart {

	private final GameComponentRef<GamePartPrototype> m_prototype;
	private final Optional<GameComponentRef<GameRole>> m_roleBinding;
	private final Collection<GamePlace> m_places = new ArrayList<>();

	public StandardGamePart(GameComponentRef<GamePartPrototype> prototype, Optional<GameComponentRef<GameRole>> roleBinding) {
		Objects.requireNonNull(prototype);
		m_prototype = prototype;
		m_roleBinding = roleBinding;
		m_prototype.get().effectivePlaces().map((p) -> new StandardGamePlace(p.self(GamePlacePrototype.class), new WeakReference<>(StandardGamePart.this)))
				.forEach(m_places::add);
	}

	@Override
	public GameComponentRef<GamePartPrototype> getPrototype() {
		return m_prototype;
	}

	@Override
	public Optional<GameComponentRef<GameRole>> getRoleBinding() {
		return m_roleBinding;
	}

	@Override
	public Optional<GameComponentRef<GameRole>> effectiveRoleBinding() {
		if (m_roleBinding.isPresent())
			return m_roleBinding;
		else
			return m_prototype.get().effectiveRoleBinding();
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
		return m_prototype.get().toString() + m_roleBinding.map((r) -> "{" + r.toString() + "}").orElse("");
	}

}
