package com.tcoffman.ttwb.state.pattern;

import java.util.Optional;
import java.util.function.Predicate;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePlacePrototype;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.pattern.GamePartPattern;
import com.tcoffman.ttwb.state.GamePlace;

public class StandardFilterPlacePattern extends StandardPlacePattern {

	private Optional<GameComponentRef<GamePlacePrototype>> m_matchesPrototype = Optional.empty();
	private Optional<GameComponentRef<GameRole>> m_matchesRoleBinding = Optional.empty();

	private Predicate<GamePlace> allowsPrototype() {
		return (place) -> m_matchesPrototype.map(GameComponentRef::get).map((match) -> match == place.getPrototype().get()).orElse(true);
	}

	private Predicate<GamePlace> allowsBinding() {
		return (place) -> m_matchesRoleBinding.map(GameComponentRef::get)
				.map((match) -> place.getPrototype().get().getRoleBinding().map(GameComponentRef::get).map(b -> b == match).orElse(false)).orElse(true);
	}

	@Override
	public Predicate<GamePlace> matches() {
		return allowsPrototype().and(allowsBinding());
	}

	private StandardFilterPlacePattern() {
	}

	public static Editor create() {
		return new StandardFilterPlacePattern().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardPlacePattern.Editor<StandardFilterPlacePattern> {

		private Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
		}

		@Override
		protected StandardFilterPlacePattern model() {
			return StandardFilterPlacePattern.this;
		}

		public Editor setPartPattern(GamePartPattern partPattern) {
			setPartPatternInternal(partPattern);
			return this;
		}

		public Editor setMatchPrototype(GameComponentRef<GamePlacePrototype> prototype) {
			requireNotDone();
			m_matchesPrototype = Optional.of(prototype);
			return this;
		}

		public Editor setMatchBinding(GameComponentRef<GameRole> role) {
			requireNotDone();
			m_matchesRoleBinding = Optional.of(role);
			return this;
		}

		public Editor setMatchPlaceType(GameComponentRef<GamePlaceType> placeType) {
			requireNotDone();
			// m_matchesRoleBinding = Optional.of(role);
			return this;
		}

	}

}
