package com.tcoffman.ttwb.model.pattern.place;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePlacePrototype;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePlace;
import com.tcoffman.ttwb.state.QueryExecutor;

public class StandardIntersectionPlacePatternTest {

	private GameComponentRef<GamePlaceType> m_placeTypeRef;

	private GameRole m_roleA;
	private GameRole m_roleB;

	private GamePlacePrototype m_placePrototypeA;
	private GamePlacePrototype m_placePrototypeB;
	private GamePlace m_placeA;
	private GamePlace m_placeB;

	private GamePartPrototype m_prototypeA;
	private GamePartPrototype m_prototypeB;
	private GamePart m_partA;
	private GamePart m_partB;

	private GamePlacePattern m_matchesNoPlacePattern;
	private GamePlacePattern m_matchesAnyPlacePattern;

	private GamePlacePattern m_matchesPrototypeAPattern;
	private GamePlacePattern m_matchesPrototypeBPattern;

	private QueryExecutor m_queryExecutor;

	@Before
	public void setup() {
		m_roleA = mock(GameRole.class);
		m_roleB = mock(GameRole.class);

		m_prototypeA = mock(GamePartPrototype.class);
		m_prototypeB = mock(GamePartPrototype.class);

		when(m_prototypeA.getRoleBinding()).thenReturn(Optional.of(GameComponentRef.wrap(m_roleA)));
		when(m_prototypeB.getRoleBinding()).thenReturn(Optional.of(GameComponentRef.wrap(m_roleB)));

		m_placeTypeRef = GameComponentRef.wrap(mock(GamePlaceType.class));

		m_placePrototypeA = mock(GamePlacePrototype.class);
		m_placePrototypeB = mock(GamePlacePrototype.class);
		when(m_placePrototypeA.getOwner()).thenReturn(m_prototypeA);
		when(m_placePrototypeB.getOwner()).thenReturn(m_prototypeB);
		when(m_placePrototypeA.getType()).thenReturn(m_placeTypeRef);
		when(m_placePrototypeB.getType()).thenReturn(m_placeTypeRef);

		m_placeA = mock(GamePlace.class);
		m_placeB = mock(GamePlace.class);

		when(m_placeA.toString()).thenReturn("Place-A");
		when(m_placeB.toString()).thenReturn("Place-B");

		when(m_placeA.getPrototype()).thenReturn(GameComponentRef.wrap(m_placePrototypeA));
		when(m_placeB.getPrototype()).thenReturn(GameComponentRef.wrap(m_placePrototypeB));

		m_partA = mock(GamePart.class);
		m_partB = mock(GamePart.class);

		when(m_placeA.getOwner()).thenReturn(m_partA);
		when(m_placeB.getOwner()).thenReturn(m_partB);

		when(m_partA.toString()).thenReturn("Part-A");
		when(m_partB.toString()).thenReturn("Part-B");

		when(m_partA.getPrototype()).thenReturn(GameComponentRef.wrap(m_prototypeA));
		when(m_partB.getPrototype()).thenReturn(GameComponentRef.wrap(m_prototypeB));

		when(m_partA.places()).thenReturn((Stream) Stream.of(m_placeA));
		when(m_partB.places()).thenReturn((Stream) Stream.of(m_placeB));

		m_matchesAnyPlacePattern = mock(GameAnyPlacePattern.class);
		when(m_matchesAnyPlacePattern.matches()).thenReturn((p) -> true);
		when(m_matchesAnyPlacePattern.matchesParts()).thenReturn((p) -> true);
		when(m_matchesAnyPlacePattern.limitQuantity((Stream<?>) Mockito.any(Stream.class))).thenAnswer((inv) -> inv.getArguments()[0]);

		m_matchesNoPlacePattern = mock(GameAnyPlacePattern.class);
		when(m_matchesNoPlacePattern.matches()).thenReturn((p) -> false);
		when(m_matchesNoPlacePattern.matchesParts()).thenReturn((p) -> true);
		when(m_matchesNoPlacePattern.limitQuantity((Stream<?>) Mockito.any(Stream.class))).thenAnswer((inv) -> inv.getArguments()[0]);

		final GamePartPattern prototypeAPartPattern = mock(GamePartPattern.class);
		when(prototypeAPartPattern.matches()).thenReturn((p) -> p.getPrototype().get() == m_prototypeA);

		m_matchesPrototypeAPattern = mock(GameFilterPlacePattern.class);
		when(m_matchesPrototypeAPattern.matches()).thenReturn((p) -> true);
		when(m_matchesPrototypeAPattern.matchesParts()).thenReturn((p) -> p.getPrototype().get() == m_prototypeA);

		final GamePartPattern prototypeBPartPattern = mock(GamePartPattern.class);
		when(prototypeBPartPattern.matches()).thenReturn((p) -> p.getPrototype().get() == m_prototypeB);

		m_matchesPrototypeBPattern = mock(GameFilterPlacePattern.class);
		when(m_matchesPrototypeBPattern.matches()).thenReturn((p) -> true);
		when(m_matchesPrototypeBPattern.matchesParts()).thenReturn((p) -> p.getPrototype().get() == m_prototypeB);

		m_queryExecutor = new QueryExecutor(() -> Stream.of(m_partA, m_partB));
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void unrestrictedPatternMatchesAnything() throws GameComponentBuilderException {
		final StandardIntersectionPlacePattern pattern = StandardIntersectionPlacePattern.create().done();
		assertThat(pattern.matches().test(null), equalTo(true));
		assertThat(pattern.matches().test(mock(GamePlace.class)), equalTo(true));
	}

	@Test
	public void singlePatternCanMatchAnything() throws GameComponentBuilderException {
		final StandardIntersectionPlacePattern pattern = StandardIntersectionPlacePattern.create().addPattern(m_matchesAnyPlacePattern).done();
		assertThat(pattern.matches().test(null), equalTo(true));
		assertThat(pattern.matches().test(mock(GamePlace.class)), equalTo(true));
	}

	@Test
	public void singlePatternCanMatchNothing() throws GameComponentBuilderException {
		final StandardIntersectionPlacePattern pattern = StandardIntersectionPlacePattern.create().addPattern(m_matchesNoPlacePattern).done();
		assertThat(pattern.matches().test(null), equalTo(false));
		assertThat(pattern.matches().test(mock(GamePlace.class)), equalTo(false));
	}

	@Test
	public void dualIdenticalPatternsCanMatchAnything() throws GameComponentBuilderException {
		final StandardIntersectionPlacePattern pattern = StandardIntersectionPlacePattern.create().addPattern(m_matchesAnyPlacePattern)
				.addPattern(m_matchesAnyPlacePattern).done();
		assertThat(pattern.matches().test(null), equalTo(true));
		assertThat(pattern.matches().test(mock(GamePlace.class)), equalTo(true));
	}

	@Test
	public void dualIdenticalPatternsCanMatchNothing() throws GameComponentBuilderException {
		final StandardIntersectionPlacePattern pattern = StandardIntersectionPlacePattern.create().addPattern(m_matchesNoPlacePattern)
				.addPattern(m_matchesNoPlacePattern).done();
		assertThat(pattern.matches().test(null), equalTo(false));
		assertThat(pattern.matches().test(mock(GamePlace.class)), equalTo(false));
	}

	@Test
	public void dualMixedPatternsCannotMatchAnything() throws GameComponentBuilderException {
		final StandardIntersectionPlacePattern pattern = StandardIntersectionPlacePattern.create().addPattern(m_matchesAnyPlacePattern)
				.addPattern(m_matchesNoPlacePattern).done();
		assertThat(pattern.matches().test(null), equalTo(false));
		assertThat(pattern.matches().test(mock(GamePlace.class)), equalTo(false));
	}

	@Test
	public void dualMixedPatternsCanMatchNothing() throws GameComponentBuilderException {
		final StandardIntersectionPlacePattern pattern = StandardIntersectionPlacePattern.create().addPattern(m_matchesNoPlacePattern)
				.addPattern(m_matchesAnyPlacePattern).done();
		assertThat(pattern.matches().test(null), equalTo(false));
		assertThat(pattern.matches().test(mock(GamePlace.class)), equalTo(false));
	}

	@Test
	public void contradicatingMixedPatternsCanMatchNothing() throws GameComponentBuilderException {
		final StandardIntersectionPlacePattern pattern = StandardIntersectionPlacePattern.create().addPattern(m_matchesNoPlacePattern)
				.addPattern(m_matchesAnyPlacePattern).done();

		final List<? extends GamePlace> places = m_queryExecutor.find(pattern).collect(Collectors.toList());
		assertThat(places, equalTo(Arrays.asList()));
	}

}
