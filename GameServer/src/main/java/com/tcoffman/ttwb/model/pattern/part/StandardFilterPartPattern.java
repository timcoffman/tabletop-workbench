package com.tcoffman.ttwb.model.pattern.part;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;
import com.tcoffman.ttwb.state.GamePart;

public class StandardFilterPartPattern extends StandardNarrowingPartPattern implements GameFilterPartPattern {

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

	@Override
	public Optional<GameComponentRef<GamePartPrototype>> getMatchesPrototype() {
		return m_matchesPrototype;
	}

	@Override
	public Optional<GameComponentRef<GameRole>> getMatchesRoleBinding() {
		return m_matchesRoleBinding;
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	public static Editor create() {
		return new StandardFilterPartPattern().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardNarrowingPartPattern.Editor<StandardFilterPartPattern> {

		private Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
			if (Stream.of(m_matchesPrototype, m_matchesRoleBinding).noneMatch(Optional::isPresent))
				throw new GameComponentBuilderException(CORE, "all filter properties are blank");
		}

		public Editor setQuantityPattern(GameQuantityPattern quantityPattern) {
			setQuantityPatternInternal(quantityPattern);
			return this;
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
		final PartPatternFormatter formatter = PartPatternFormatter.create().matchesQuantity(getQuantityPattern());
		m_matchesPrototype.ifPresent((p) -> formatter.equalProperty("prototype", p.get()));
		m_matchesRoleBinding.ifPresent((r) -> formatter.equalProperty("binding", r.get()));
		return formatter.toString();
	}

}
