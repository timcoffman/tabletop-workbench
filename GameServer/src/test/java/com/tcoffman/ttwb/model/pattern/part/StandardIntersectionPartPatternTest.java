package com.tcoffman.ttwb.model.pattern.part;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.pattern.part.StandardIntersectionPartPattern;
import com.tcoffman.ttwb.state.GamePart;

public class StandardIntersectionPartPatternTest {

	private GameRole m_roleA;
	private GameRole m_roleB;

	private GamePartPrototype m_prototypeA;
	private GamePartPrototype m_prototypeB;
	private GamePart m_partA;
	private GamePart m_partB;

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
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void unrestrictedPatternMatchesAnything() throws GameComponentBuilderException {
		final StandardIntersectionPartPattern pattern = StandardIntersectionPartPattern.create().done();
		assertThat(pattern.matches().test(null), equalTo(true));
		assertThat(pattern.matches().test(mock(GamePart.class)), equalTo(true));
	}

}
