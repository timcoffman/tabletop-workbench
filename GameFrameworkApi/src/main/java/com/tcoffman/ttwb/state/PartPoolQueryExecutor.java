package com.tcoffman.ttwb.state;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.pattern.part.GameAnyPartPattern;
import com.tcoffman.ttwb.model.pattern.part.GameFilterPartPattern;
import com.tcoffman.ttwb.model.pattern.part.GameIntersectionPartPattern;
import com.tcoffman.ttwb.model.pattern.part.GameInversionPartPattern;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.part.GameVariablePartPattern;
import com.tcoffman.ttwb.model.pattern.place.GameAnyPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GameFilterPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GameIntersectionPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GameInversionPlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.model.pattern.place.GameRelationshipPlacePattern;
import com.tcoffman.ttwb.state.pool.GamePartPool;
import com.tcoffman.ttwb.state.pool.GamePartStaticPool;
import com.tcoffman.ttwb.state.pool.GamePlaceSubset;

public final class PartPoolQueryExecutor implements QueryExecutor {
	private final GamePartPool m_pool;

	public PartPoolQueryExecutor(Collection<? extends GamePart> parts) {
		this(new GamePartStaticPool(parts));
	}

	public PartPoolQueryExecutor(GamePartPool pool) {
		m_pool = pool;
	}

	@Override
	public Stream<? extends GamePart> find(GamePartPattern pattern) {
		return subsetFromPool(pattern).stream().map(GamePlace::getOwner).distinct();
	}

	private GamePlaceSubset subsetFromPool(GamePartPattern pattern) {
		return pattern.visit(new GamePartPattern.Visitor<GamePlaceSubset, RuntimeException>() {

			@Override
			public GamePlaceSubset visit(GameAnyPartPattern pattern) {
				return m_pool.all();
			}

			@Override
			public GamePlaceSubset visit(GameVariablePartPattern pattern) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public GamePlaceSubset visit(GameFilterPartPattern pattern) {
				return subsetFrom(m_pool.all(), pattern);
			}

			@Override
			public GamePlaceSubset visit(GameIntersectionPartPattern pattern) {
				final Iterator<? extends GamePartPattern> i = pattern.patterns().iterator();
				if (!i.hasNext())
					return m_pool.all();
				GamePlaceSubset subset = subsetFromPool(i.next());
				while (i.hasNext())
					subset = subsetFrom(subset, i.next());
				return subset;
			}

			@Override
			public GamePlaceSubset visit(GameInversionPartPattern pattern) {
				return subsetFromPool(pattern.getPattern()).invert();
			}

		});
	}

	private static boolean prototypeMatches(GamePartPrototype prototype, GamePartPrototype matchingPrototype) {
		return prototype == matchingPrototype
				|| prototype.getExtends().map(GameComponentRef::get).map((p) -> prototypeMatches(p, matchingPrototype)).orElse(false);
	}

	private static boolean prototypeMatches(GamePart part, GamePartPrototype matchingPrototype) {
		final GamePartPrototype prototype = part.getPrototype().get();
		return prototypeMatches(prototype, matchingPrototype);
	}

	private GamePlaceSubset subsetFrom(GamePlaceSubset subset, GamePartPattern pattern) {
		return pattern.visit(new GamePartPattern.Visitor<GamePlaceSubset, RuntimeException>() {

			@Override
			public GamePlaceSubset visit(GameAnyPartPattern pattern) {
				return subset;
			}

			@Override
			public GamePlaceSubset visit(GameVariablePartPattern pattern) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public GamePlaceSubset visit(GameFilterPartPattern pattern) {
				pattern.getMatchesPrototype().ifPresent(prototype -> {
					subset.retainIf((p) -> prototypeMatches(p.getOwner(), prototype.get()));
				});
				pattern.getMatchesRoleBinding().ifPresent(role -> {
					subset.retainIf((p) -> p.getOwner().getRoleBinding().map(r -> r == role.get()).orElse(false));
				});
				return subset;
			}

			@Override
			public GamePlaceSubset visit(GameIntersectionPartPattern pattern) {
				final Iterator<? extends GamePartPattern> i = pattern.patterns().iterator();
				GamePlaceSubset subset2 = subset;
				while (i.hasNext())
					subset2 = subsetFrom(subset2, i.next());
				return subset2;
			}

			@Override
			public GamePlaceSubset visit(GameInversionPartPattern pattern) {
				return subsetFrom(subset, pattern.getPattern()).invert();
			}

		});
	}

	@Deprecated
	@Override
	public Stream<? extends GamePart> find(Predicate<GamePart> matcher) {
		final GamePlaceSubset subset = m_pool.none();
		m_pool.stream().filter(matcher).flatMap(GamePart::places).forEach(subset::add);
		return subset.stream().map(GamePlace::getOwner).distinct();
	}

	@Override
	public Stream<? extends GamePlace> find(GamePlacePattern pattern) {
		return subsetFromPool(pattern).stream();
	}

	private GamePlaceSubset subsetFromPool(GamePlacePattern pattern) {
		return pattern.visit(new GamePlacePattern.Visitor<GamePlaceSubset, RuntimeException>() {

			@Override
			public GamePlaceSubset visit(GameAnyPlacePattern pattern) throws RuntimeException {
				return m_pool.all();
			}

			@Override
			public GamePlaceSubset visit(GameFilterPlacePattern pattern) throws RuntimeException {
				return subsetFrom(m_pool.all(), pattern);
			}

			@Override
			public GamePlaceSubset visit(GameIntersectionPlacePattern pattern) throws RuntimeException {
				final Iterator<? extends GamePlacePattern> i = pattern.patterns().iterator();
				if (!i.hasNext())
					return m_pool.all();
				GamePlaceSubset subset = subsetFromPool(i.next());
				while (i.hasNext())
					subset = subsetFrom(subset, i.next());
				return subset;
			}

			@Override
			public GamePlaceSubset visit(GameRelationshipPlacePattern pattern) throws RuntimeException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public GamePlaceSubset visit(GameInversionPlacePattern pattern) throws RuntimeException {
				return subsetFromPool(pattern.getPattern()).invert();
			}
		});
	}

	private GamePlaceSubset subsetFrom(GamePlaceSubset subset, GamePlacePattern pattern) {
		return pattern.visit(new GamePlacePattern.Visitor<GamePlaceSubset, RuntimeException>() {

			@Override
			public GamePlaceSubset visit(GameAnyPlacePattern pattern) throws RuntimeException {
				return subset;
			}

			@Override
			public GamePlaceSubset visit(GameFilterPlacePattern pattern) throws RuntimeException {
				pattern.getMatchesType().ifPresent(prototype -> {
					subset.retainIf((p) -> prototype.get() == p.getPrototype());
				});
				pattern.getMatchesRoleBinding().ifPresent(role -> {
					subset.retainIf((p) -> p.getOwner().getRoleBinding().map(r -> r == role.get()).orElse(false));
				});
				return subset;
			}

			@Override
			public GamePlaceSubset visit(GameIntersectionPlacePattern pattern) throws RuntimeException {
				final Iterator<? extends GamePlacePattern> i = pattern.patterns().iterator();
				GamePlaceSubset subset2 = subset;
				while (i.hasNext())
					subset2 = subsetFrom(subset2, i.next());
				return subset2;
			}

			@Override
			public GamePlaceSubset visit(GameRelationshipPlacePattern pattern) throws RuntimeException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public GamePlaceSubset visit(GameInversionPlacePattern pattern) throws RuntimeException {
				return subsetFrom(subset, pattern.getPattern()).invert();
			}
		});
	}

	@Override
	public GamePart findOne(GamePartPattern pattern) {
		return findOneOrZero(pattern).orElseThrow(() -> new IllegalArgumentException("no places match the pattern \"" + pattern + "\""));
	}

	@Override
	public GamePlace findOne(GamePlacePattern pattern) {
		return findOneOrZero(pattern).orElseThrow(() -> new IllegalArgumentException("no places match the pattern \"" + pattern + "\""));
	}

	@Override
	public Optional<? extends GamePart> findOneOrZero(GamePartPattern pattern) {
		final Iterator<? extends GamePart> i = find(pattern).iterator();
		if (!i.hasNext())
			return Optional.empty();
		else {
			final GamePart p = i.next();
			if (i.hasNext())
				throw new IllegalArgumentException("too many parts match the pattern \"" + pattern + "\"");
			else
				return Optional.of(p);
		}

	}

	@Override
	public Optional<? extends GamePlace> findOneOrZero(GamePlacePattern pattern) {
		find(pattern.matchesParts()).forEach((p) -> {
			System.err.println("*** " + p);
			p.places().forEach(System.err::println);
		});
		final Iterator<? extends GamePlace> i = find(pattern).iterator();
		if (!i.hasNext())
			return Optional.empty();
		else {
			final GamePlace p = i.next();
			if (i.hasNext())
				throw new IllegalArgumentException("too many places match the pattern \"" + pattern + "\"");
			else
				return Optional.of(p);
		}

	}
}