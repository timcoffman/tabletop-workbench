package com.tcoffman.ttwb.state.pattern;

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
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.state.GamePart;

public class StandardFilterPartPatternTest {

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

		when(m_prototypeA.getRoleBinding()).thenReturn(Optional.of(() -> m_roleA));
		when(m_prototypeB.getRoleBinding()).thenReturn(Optional.of(() -> m_roleB));

		m_partA = mock(GamePart.class);
		m_partB = mock(GamePart.class);

		when(m_partA.getPrototype()).thenReturn(() -> m_prototypeA);
		when(m_partB.getPrototype()).thenReturn(() -> m_prototypeB);
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	private GameRole m_roleB;
	private GameRole m_roleA;

	@Test
	public void unrestrictedPatternMatchesAnything() throws GameComponentBuilderException {
		final StandardFilterPartPattern pattern = StandardFilterPartPattern.create().done();
		assertThat(pattern.matches().test(null), equalTo(true));
		assertThat(pattern.matches().test(mock(GamePart.class)), equalTo(true));
	}

	@Test
	public void restrictedPatternCannotCheckDegenerateArguments() throws GameComponentBuilderException {
		final StandardFilterPartPattern pattern = StandardFilterPartPattern.create().setMatchPrototype(() -> m_prototypeA).done();
		thrown.expect(NullPointerException.class);
		assertThat(pattern.matches().test(null), equalTo(true));
		thrown.expect(NullPointerException.class);
		assertThat(pattern.matches().test(mock(GamePart.class)), equalTo(true));
	}

	@Test
	public void patternFilteringOnPrototypeCanIdentifiyPrototype() throws GameComponentBuilderException {

		final StandardFilterPartPattern pattern = StandardFilterPartPattern.create().setMatchPrototype(() -> m_prototypeA).done();
		assertThat(pattern.matches().test(m_partA), equalTo(true));
		assertThat(pattern.matches().test(m_partB), equalTo(false));
	}

	@Test
	public void patternFilteringOnBindingCanIdentifiyBinding() throws GameComponentBuilderException {
		final StandardFilterPartPattern pattern = StandardFilterPartPattern.create().setMatchBinding(() -> m_roleA).done();
		assertThat(pattern.matches().test(m_partA), equalTo(true));
		assertThat(pattern.matches().test(m_partB), equalTo(false));
	}

	@Test
	public void patternFilteringOnPrototypeAndBindingCanIdentifiyPrototypeAndBinding() throws GameComponentBuilderException {
		final StandardFilterPartPattern pattern = StandardFilterPartPattern.create().setMatchPrototype(() -> m_prototypeA).setMatchBinding(() -> m_roleA)
				.done();
		assertThat(pattern.matches().test(m_partA), equalTo(true));
		assertThat(pattern.matches().test(m_partB), equalTo(false));
	}

}
