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
import com.tcoffman.ttwb.state.GamePart;

public class StandardFilterPartPatternTest {

	private GameRole m_roleB;
	private GameRole m_roleA;

	private GamePartPrototype m_prototypeA;
	private GamePartPrototype m_prototypeB;
	private GamePartPrototype m_prototypeC;
	private GamePart m_partA;
	private GamePart m_partB;
	private GamePart m_partC;

	@Before
	public void setup() {
		m_roleA = mock(GameRole.class);
		m_roleB = mock(GameRole.class);

		m_prototypeA = mock(GamePartPrototype.class);
		m_prototypeB = mock(GamePartPrototype.class);
		m_prototypeC = mock(GamePartPrototype.class);

		when(m_prototypeA.effectiveRoleBinding()).thenReturn(Optional.of(GameComponentRef.wrap(m_roleA)));
		when(m_prototypeB.effectiveRoleBinding()).thenReturn(Optional.of(GameComponentRef.wrap(m_roleB)));
		when(m_prototypeC.effectiveRoleBinding()).thenReturn(Optional.of(GameComponentRef.wrap(m_roleB)));

		when(m_prototypeA.getExtends()).thenReturn(Optional.empty());
		when(m_prototypeB.getExtends()).thenReturn(Optional.of(GameComponentRef.wrap(m_prototypeC)));
		when(m_prototypeC.getExtends()).thenReturn(Optional.empty());

		m_partA = mock(GamePart.class);
		m_partB = mock(GamePart.class);
		m_partC = mock(GamePart.class);

		when(m_partA.getPrototype()).thenReturn(GameComponentRef.wrap(m_prototypeA));
		when(m_partB.getPrototype()).thenReturn(GameComponentRef.wrap(m_prototypeB));
		when(m_partC.getPrototype()).thenReturn(GameComponentRef.wrap(m_prototypeC));

		when(m_partA.effectiveRoleBinding()).thenReturn(Optional.of(GameComponentRef.wrap(m_roleA)));
		when(m_partB.effectiveRoleBinding()).thenReturn(Optional.of(GameComponentRef.wrap(m_roleB)));
		when(m_partC.effectiveRoleBinding()).thenReturn(Optional.of(GameComponentRef.wrap(m_roleB)));

	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void unrestrictedPatternCannotBeConstructed() throws GameComponentBuilderException {
		thrown.expect(GameComponentBuilderException.class);
		StandardFilterPartPattern.create().done();
	}

	@Test
	public void restrictedPatternCannotCheckDegenerateArguments() throws GameComponentBuilderException {
		final StandardFilterPartPattern pattern = StandardFilterPartPattern.create().setMatchPrototype(GameComponentRef.wrap(m_prototypeA)).done();
		thrown.expect(NullPointerException.class);
		assertThat(pattern.matches().test(null), equalTo(true));
		thrown.expect(NullPointerException.class);
		assertThat(pattern.matches().test(mock(GamePart.class)), equalTo(true));
	}

	@Test
	public void patternFilteringOnPrototypeCanIdentifiyPrototype() throws GameComponentBuilderException {

		final StandardFilterPartPattern pattern = StandardFilterPartPattern.create().setMatchPrototype(GameComponentRef.wrap(m_prototypeA)).done();
		assertThat(pattern.matches().test(m_partA), equalTo(true));
		assertThat(pattern.matches().test(m_partB), equalTo(false));
		assertThat(pattern.matches().test(m_partC), equalTo(false));
	}

	@Test
	public void patternFilteringOnExtendedPrototypeCanIdentifiyPrototype() throws GameComponentBuilderException {

		final StandardFilterPartPattern pattern = StandardFilterPartPattern.create().setMatchPrototype(GameComponentRef.wrap(m_prototypeC)).done();
		assertThat(pattern.matches().test(m_partA), equalTo(false));
		assertThat(pattern.matches().test(m_partB), equalTo(true));
		assertThat(pattern.matches().test(m_partC), equalTo(true));
	}

	@Test
	public void patternFilteringOnBindingCanIdentifiyBinding() throws GameComponentBuilderException {
		final StandardFilterPartPattern pattern = StandardFilterPartPattern.create().setMatchBinding(GameComponentRef.wrap(m_roleA)).done();
		assertThat(pattern.matches().test(m_partA), equalTo(true));
		assertThat(pattern.matches().test(m_partB), equalTo(false));
	}

	@Test
	public void patternFilteringOnPrototypeAndBindingCanIdentifiyPrototypeAndBinding() throws GameComponentBuilderException {
		final StandardFilterPartPattern pattern = StandardFilterPartPattern.create().setMatchPrototype(GameComponentRef.wrap(m_prototypeA))
				.setMatchBinding(GameComponentRef.wrap(m_roleA)).done();
		assertThat(pattern.matches().test(m_partA), equalTo(true));
		assertThat(pattern.matches().test(m_partB), equalTo(false));
	}

}
