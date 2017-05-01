package com.tcoffman.ttwb.state.pattern;

import java.util.Optional;
import java.util.function.Predicate;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.pattern.GamePartPattern;
import com.tcoffman.ttwb.state.GamePart;

public class StandardFilterPartPattern implements GamePartPattern {

	private Optional<GameComponentRef<GamePartPrototype>> m_matchesPrototype = Optional.empty();
	private Optional<GameComponentRef<GameRole>> m_matchesRoleBinding = Optional.empty();

	private static boolean prototypeMatches(GamePart part, GamePartPrototype matchingPrototype) {
		final GamePartPrototype prototype = part.getPrototype().get();
		return prototype == matchingPrototype;
	}

	private Predicate<GamePart> allowsPrototype() {
		return (part) -> m_matchesPrototype.map(GameComponentRef::get).map((match) -> prototypeMatches(part, match)).orElse(true);
	}

	private static boolean bindingMatches(GamePart part, GameRole matchingRole) {
		final Optional<GameComponentRef<GameRole>> binding = part.getPrototype().get().getRoleBinding();
		return binding.isPresent() && binding.get().get() == matchingRole;
	}

	private Predicate<GamePart> allowsBinding() {
		return (part) -> m_matchesRoleBinding.map(GameComponentRef::get).map((match) -> bindingMatches(part, match)).orElse(true);
	}

	@Override
	public Predicate<GamePart> matches() {
		return allowsPrototype().and(allowsBinding());
	}

	private StandardFilterPartPattern() {
	}

	public static Editor create() {
		return new StandardFilterPartPattern().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends AbstractEditor<StandardFilterPartPattern> {

		private Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
		}

		@Override
		protected StandardFilterPartPattern model() {
			return StandardFilterPartPattern.this;
		}

		public Editor setMatchPrototype(GameComponentRef<GamePartPrototype> prototype) {
			requireNotDone();
			m_matchesPrototype = Optional.of(prototype);
			return this;
		}

		public Editor setMatchBinding(GameComponentRef<GameRole> role) {
			requireNotDone();
			m_matchesRoleBinding = Optional.of(role);
			return this;
		}

	}

	@Override
	public String toString() {
		final PartPatternFormatter formatter = PartPatternFormatter.create();
		m_matchesPrototype.ifPresent((p) -> formatter.equalProperty("prototype", p.get()));
		m_matchesRoleBinding.ifPresent((r) -> formatter.equalProperty("binding", r.get()));
		return formatter.toString();
	}

}
