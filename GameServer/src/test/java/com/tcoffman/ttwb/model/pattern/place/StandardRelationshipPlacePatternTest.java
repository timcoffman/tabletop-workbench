package com.tcoffman.ttwb.model.pattern.place;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GamePlacePrototype;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePartRelationship;
import com.tcoffman.ttwb.state.GamePlace;

public class StandardRelationshipPlacePatternTest {

	private GameRole m_roleA;
	private GameRole m_roleB;

	private GamePartPrototype m_prototypeA;
	private GamePartPrototype m_prototypeB;
	private GamePart m_partA;
	private GamePart m_partB;

	private GamePlaceType m_placeTypeGamma;
	private GamePlaceType m_placeTypeDelta;

	private GamePlacePrototype m_placeProtoGammaA;
	private GamePlacePrototype m_placeProtoDeltaA;
	private GamePlacePrototype m_placeProtoGammaB;
	private GamePlacePrototype m_placeProtoDeltaB;

	private GamePlace m_placeGammaA;
	private GamePlace m_placeDeltaA;
	private GamePlace m_placeGammaB;
	private GamePlace m_placeDeltaB;

	private GamePartRelationshipType m_relTypeRho;
	private GamePartRelationshipType m_relTypeTau;

	private GamePartRelationship m_relRhoGammaAB;
	private GamePartRelationship m_relTauGammaBA;

	/**
	 * <pre>
	 *  Proto A  <------------------- Part A
	 *  +-- Binding: Role A             |
	 *  +-- Place Proto Gamma A <-----  +-- Place Gamma A ->-\ <--\
	 *      +-- Type: Gamma             |                    |    |
	 *  +-- Place Proto Delta A <-----  +-- Place Delta A    |    |
	 *      +-- Type: Delta                                  |   Tau
	 *                                                      Rho   |
	 *  Proto B  <------------------- Part B                 |    |
	 *  +-- Binding: Role B             |                    |    |
	 *  +-- Place Proto Gamma B <-----  +-- Place Gamma B <--/ ->-/
	 *      +-- Type: Gamma             |
	 *  +-- Place Proto Delta B <-----  +-- Place Delta B
	 *      +-- Type: Delta
	 * </pre>
	 */
	@Before
	public void setup() {
		m_roleA = mock(GameRole.class);
		m_roleB = mock(GameRole.class);

		m_prototypeA = mock(GamePartPrototype.class);
		m_prototypeB = mock(GamePartPrototype.class);

		when(m_prototypeA.getRoleBinding()).thenReturn(Optional.of(GameComponentRef.wrap(m_roleA)));
		when(m_prototypeB.getRoleBinding()).thenReturn(Optional.of(GameComponentRef.wrap(m_roleB)));

		m_partA = mock(GamePart.class);
		m_partB = mock(GamePart.class);

		when(m_partA.getPrototype()).thenReturn(GameComponentRef.wrap(m_prototypeA));
		when(m_partB.getPrototype()).thenReturn(GameComponentRef.wrap(m_prototypeB));

		m_placeTypeGamma = mock(GamePlaceType.class);
		m_placeTypeDelta = mock(GamePlaceType.class);

		m_placeProtoGammaA = mock(GamePlacePrototype.class);
		m_placeProtoDeltaA = mock(GamePlacePrototype.class);
		m_placeProtoGammaB = mock(GamePlacePrototype.class);
		m_placeProtoDeltaB = mock(GamePlacePrototype.class);

		when(m_placeProtoGammaA.getOwner()).thenReturn(m_prototypeA);
		when(m_placeProtoDeltaA.getOwner()).thenReturn(m_prototypeA);
		when(m_placeProtoGammaB.getOwner()).thenReturn(m_prototypeB);
		when(m_placeProtoDeltaB.getOwner()).thenReturn(m_prototypeB);

		when(m_placeProtoGammaA.getType()).thenReturn(GameComponentRef.wrap(m_placeTypeGamma));
		when(m_placeProtoDeltaA.getType()).thenReturn(GameComponentRef.wrap(m_placeTypeDelta));
		when(m_placeProtoGammaB.getType()).thenReturn(GameComponentRef.wrap(m_placeTypeGamma));
		when(m_placeProtoDeltaB.getType()).thenReturn(GameComponentRef.wrap(m_placeTypeDelta));

		m_placeGammaA = mock(GamePlace.class);
		m_placeDeltaA = mock(GamePlace.class);
		m_placeGammaB = mock(GamePlace.class);
		m_placeDeltaB = mock(GamePlace.class);

		when(m_partA.places()).thenAnswer((i) -> Stream.of(m_placeGammaA, m_placeDeltaA));
		when(m_partB.places()).thenAnswer((i) -> Stream.of(m_placeGammaB, m_placeDeltaB));

		when(m_placeGammaA.getOwner()).thenReturn(m_partA);
		when(m_placeDeltaA.getOwner()).thenReturn(m_partA);
		when(m_placeGammaB.getOwner()).thenReturn(m_partB);
		when(m_placeDeltaB.getOwner()).thenReturn(m_partB);

		m_relTypeRho = mock(GamePartRelationshipType.class);
		m_relTypeTau = mock(GamePartRelationshipType.class);

		m_relRhoGammaAB = mock(GamePartRelationship.class);
		m_relTauGammaBA = mock(GamePartRelationship.class);

		when(m_relRhoGammaAB.getType()).thenReturn(GameComponentRef.wrap(m_relTypeRho));
		when(m_relTauGammaBA.getType()).thenReturn(GameComponentRef.wrap(m_relTypeTau));

		when(m_relRhoGammaAB.getSource()).thenReturn(GameComponentRef.wrap(m_placeGammaA));
		when(m_relRhoGammaAB.getDestination()).thenReturn(GameComponentRef.wrap(m_placeGammaB));
		when(m_relTauGammaBA.getSource()).thenReturn(GameComponentRef.wrap(m_placeGammaB));
		when(m_relTauGammaBA.getDestination()).thenReturn(GameComponentRef.wrap(m_placeGammaA));

		when(m_placeGammaA.incomingRelationships()).thenAnswer((i) -> Stream.of(m_relTauGammaBA));
		when(m_placeGammaA.outgoingRelationships()).thenAnswer((i) -> Stream.of(m_relRhoGammaAB));
		when(m_placeGammaB.incomingRelationships()).thenAnswer((i) -> Stream.of(m_relRhoGammaAB));
		when(m_placeGammaB.outgoingRelationships()).thenAnswer((i) -> Stream.of(m_relTauGammaBA));
		when(m_placeDeltaA.incomingRelationships()).thenAnswer((i) -> Stream.empty());
		when(m_placeDeltaA.outgoingRelationships()).thenAnswer((i) -> Stream.empty());
		when(m_placeDeltaB.incomingRelationships()).thenAnswer((i) -> Stream.empty());
		when(m_placeDeltaB.outgoingRelationships()).thenAnswer((i) -> Stream.empty());
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void unrestrictedPatternMatchesCannotCheckMissingPlace() throws GameComponentBuilderException {
		final StandardRelationshipPlacePattern pattern = StandardRelationshipPlacePattern.create().done();
		thrown.expect(NullPointerException.class);
		assertThat(pattern.matches().test(null), equalTo(true));
		thrown.expect(NullPointerException.class);
		assertThat(pattern.matches().test(mock(GamePlace.class)), equalTo(true));
	}

	@Test
	public void unrestrictedPatternMatchesAnyPlaceWithSomeRelationships() throws GameComponentBuilderException {
		final StandardRelationshipPlacePattern pattern = StandardRelationshipPlacePattern.create().done();
		assertThat(pattern.matches().test(m_placeGammaA), equalTo(true));
		assertThat(pattern.matches().test(m_placeGammaB), equalTo(true));
		assertThat(pattern.matches().test(m_placeDeltaA), equalTo(false));
		assertThat(pattern.matches().test(m_placeDeltaB), equalTo(false));
	}

	@Test
	public void patternMatchingRelationshipTypeCanIdentiftyRelationshipTypeForward() throws GameComponentBuilderException {

		final StandardRelationshipPlacePattern pattern = StandardRelationshipPlacePattern.create().setMatchType(GameComponentRef.wrap(m_relTypeRho)).done();
		assertThat(pattern.matches().test(m_placeGammaA), equalTo(true));
		assertThat(pattern.matches().test(m_placeDeltaA), equalTo(false));
		assertThat(pattern.matches().test(m_placeGammaB), equalTo(false));
		assertThat(pattern.matches().test(m_placeDeltaB), equalTo(false));
	}

	@Test
	public void patternMatchingRelationshipTypeCanIdentiftyRelationshipTypeBackward() throws GameComponentBuilderException {

		final StandardRelationshipPlacePattern pattern = StandardRelationshipPlacePattern.create().setMatchForward(false)
				.setMatchType(GameComponentRef.wrap(m_relTypeRho)).done();
		assertThat(pattern.matches().test(m_placeGammaA), equalTo(false));
		assertThat(pattern.matches().test(m_placeDeltaA), equalTo(false));
		assertThat(pattern.matches().test(m_placeGammaB), equalTo(true));
		assertThat(pattern.matches().test(m_placeDeltaB), equalTo(false));
	}

	@Test
	public void patternMatchingRelationshipTypeCanIdentiftyRelationshipTypeForwardDoesNotExist() throws GameComponentBuilderException {

		final StandardRelationshipPlacePattern pattern = StandardRelationshipPlacePattern.create().setMatchExistence(false)
				.setMatchType(GameComponentRef.wrap(m_relTypeRho)).done();
		assertThat(pattern.matches().test(m_placeGammaA), equalTo(false));
		assertThat(pattern.matches().test(m_placeDeltaA), equalTo(true));
		assertThat(pattern.matches().test(m_placeGammaB), equalTo(true));
		assertThat(pattern.matches().test(m_placeDeltaB), equalTo(true));
	}

	@Test
	public void patternMatchingRelationshipTypeCanIdentiftyRelationshipTypeBackwardDoesNotExist() throws GameComponentBuilderException {

		final StandardRelationshipPlacePattern pattern = StandardRelationshipPlacePattern.create().setMatchExistence(false).setMatchForward(false)
				.setMatchType(GameComponentRef.wrap(m_relTypeRho)).done();
		assertThat(pattern.matches().test(m_placeGammaA), equalTo(true));
		assertThat(pattern.matches().test(m_placeDeltaA), equalTo(true));
		assertThat(pattern.matches().test(m_placeGammaB), equalTo(false));
		assertThat(pattern.matches().test(m_placeDeltaB), equalTo(true));
	}

	@Test
	public void patternMatchingDestinationCanIdentifyDestinationByPattern() throws GameComponentBuilderException {
		final GamePlacePattern dstPlacePattern = mock(GamePlacePattern.class);
		when(dstPlacePattern.matches()).thenReturn((p) -> true);
		when(dstPlacePattern.matchesParts()).thenReturn((p) -> p == m_partB);

		final StandardRelationshipPlacePattern pattern = StandardRelationshipPlacePattern.create().setRelatedPlacePattern(dstPlacePattern).done();
		assertThat(pattern.matches().test(m_placeGammaA), equalTo(true));
		assertThat(pattern.matches().test(m_placeGammaB), equalTo(false));
	}

}
