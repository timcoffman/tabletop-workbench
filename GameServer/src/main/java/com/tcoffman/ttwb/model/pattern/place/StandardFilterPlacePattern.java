package com.tcoffman.ttwb.model.pattern.place;

import java.util.Optional;
import java.util.function.Predicate;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;
import com.tcoffman.ttwb.state.GamePlace;

public class StandardFilterPlacePattern extends StandardNarrowingPlacePattern implements GameFilterPlacePattern {

	private Optional<GameComponentRef<GamePlaceType>> m_matchesType = Optional.empty();
	private Optional<GameComponentRef<GameRole>> m_matchesRoleBinding = Optional.empty();

	private Predicate<GamePlace> allowsType() {
		return (place) -> m_matchesType.map(GameComponentRef::get).map((match) -> match == place.getPrototype().get().getType().get()).orElse(true);
	}

	private Predicate<GamePlace> allowsBinding() {
		return (place) -> m_matchesRoleBinding.map(GameComponentRef::get)
				.map((match) -> place.getPrototype().get().getRoleBinding().map(GameComponentRef::get).map(b -> b == match).orElse(false)).orElse(true);
	}

	@Override
	public Predicate<GamePlace> matches() {
		return allowsType().and(allowsBinding());
	}

	private StandardFilterPlacePattern() {
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	@Override
	public Optional<GameComponentRef<GamePlaceType>> getMatchesType() {
		return m_matchesType;
	}

	@Override
	public Optional<GameComponentRef<GameRole>> getMatchesRoleBinding() {
		return m_matchesRoleBinding;
	}

	public static Editor create() {
		return new StandardFilterPlacePattern().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardNarrowingPlacePattern.Editor<StandardFilterPlacePattern> {

		private Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
		}

		public Editor setPartPattern(GamePartPattern partPattern) {
			setPartPatternInternal(partPattern);
			return this;
		}

		public Editor setQuantityPattern(GameQuantityPattern quantityPattern) {
			setQuantityPatternInternal(quantityPattern);
			return this;
		}

		public Editor setMatchType(GameComponentRef<GamePlaceType> placeType) {
			requireNotDone();
			m_matchesType = Optional.of(placeType);
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
		final PlacePatternFormatter formatter = PlacePatternFormatter.create().matchesQuantity(getQuantityPattern()).matchesPart(getPartPattern());
		m_matchesType.ifPresent((p) -> formatter.equalProperty("type", p.get()));
		m_matchesRoleBinding.ifPresent((r) -> formatter.equalProperty("binding", r.get()));
		return formatter.toString();
	}

}
