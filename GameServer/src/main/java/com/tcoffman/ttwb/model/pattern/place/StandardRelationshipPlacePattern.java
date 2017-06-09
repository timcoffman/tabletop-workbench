package com.tcoffman.ttwb.model.pattern.place;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePartRelationship;
import com.tcoffman.ttwb.state.GamePlace;

public class StandardRelationshipPlacePattern extends StandardNarrowingPlacePattern implements GameRelationshipPlacePattern {

	private boolean m_matchesForward = true;
	private boolean m_matchesExistence = true;
	private Optional<GameComponentRef<GamePartRelationshipType>> m_matchRelationshipType = Optional.empty();
	private Optional<GamePlacePattern> m_relatedPlacePattern = Optional.empty();

	@Override
	public Predicate<GamePlace> matches() {
		return this::matchesPlace;
	}

	private boolean matchesPlace(GamePlace place) {
		Stream<? extends GamePartRelationship> relationships = m_matchesForward ? place.outgoingRelationships() : place.incomingRelationships();
		if (m_matchRelationshipType.isPresent())
			relationships = relationships.filter((r) -> r.getType().get() == m_matchRelationshipType.get().get());
		boolean anyRelatedPlaceExists;
		if (m_relatedPlacePattern.isPresent()) {
			final GamePlacePattern placePattern = m_relatedPlacePattern.get();
			final Stream<? extends GamePlace> places = relationships.map(m_matchesForward ? (r) -> r.getDestination() : (r) -> r.getSource())
					.map(GameComponentRef::get).filter(placePattern.matches());

			final Stream<? extends GamePart> parts = places.map(GamePlace::getOwner).filter(placePattern.matchesParts());
			anyRelatedPlaceExists = parts.findAny().isPresent();
		} else
			anyRelatedPlaceExists = relationships.findAny().isPresent();
		return m_matchesExistence ? anyRelatedPlaceExists : !anyRelatedPlaceExists;
	}

	private StandardRelationshipPlacePattern() {
	}

	@Override
	public <R, E extends Throwable> R visit(Visitor<R, E> visitor) throws E {
		return visitor.visit(this);
	}

	@Override
	public boolean getTraverseForward() {
		return m_matchesForward;
	}

	@Override
	public boolean getMatchExistence() {
		return m_matchesExistence;
	}

	@Override
	public Optional<GameComponentRef<GamePartRelationshipType>> getMatchType() {
		return m_matchRelationshipType;
	}

	@Override
	public Optional<GamePlacePattern> getRelatedPlacePattern() {
		return m_relatedPlacePattern;
	}

	public static Editor create() {
		return new StandardRelationshipPlacePattern().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends StandardNarrowingPlacePattern.Editor<StandardRelationshipPlacePattern> {

		private Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
			requirePresent(CORE, "relationshipType", m_matchRelationshipType);
			requirePresent(CORE, "relatedPlacePattern", m_relatedPlacePattern);
		}

		public Editor setQuantityPattern(GameQuantityPattern quantityPattern) {
			setQuantityPatternInternal(quantityPattern);
			return this;
		}

		public Editor setPartPattern(GamePartPattern partPattern) {
			setPartPatternInternal(partPattern);
			return this;
		}

		public Editor setMatchForward(boolean forward) {
			requireNotDone();
			m_matchesForward = forward;
			return this;
		}

		public Editor setMatchExistence(boolean existence) {
			requireNotDone();
			m_matchesExistence = existence;
			return this;
		}

		public Editor setMatchType(GameComponentRef<GamePartRelationshipType> relationshipType) {
			requireNotDone();
			m_matchRelationshipType = Optional.of(relationshipType);
			return this;
		}

		public Editor setRelatedPlacePattern(GamePlacePattern relatedPlacePattern) {
			requireNotDone();
			m_relatedPlacePattern = Optional.of(relatedPlacePattern);
			return this;
		}

	}

	@Override
	public String toString() {
		final PlacePatternFormatter formatter = PlacePatternFormatter.create().matchesQuantity(getQuantityPattern()).matchesPart(getPartPattern());
		formatter.matchesRelationship(m_matchesExistence, m_matchesForward, m_matchRelationshipType, m_relatedPlacePattern);
		return formatter.toString();
	}

}
