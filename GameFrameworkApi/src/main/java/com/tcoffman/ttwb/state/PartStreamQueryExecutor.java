package com.tcoffman.ttwb.state;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;

public final class PartStreamQueryExecutor implements QueryExecutor {
	private final Supplier<Stream<? extends GamePart>> m_partSource;

	public PartStreamQueryExecutor(Supplier<Stream<? extends GamePart>> partSource) {
		m_partSource = partSource;
	}

	@Override
	public Stream<? extends GamePart> find(GamePartPattern pattern) {
		final Stream<? extends GamePart> parts = find(pattern.matches());
		return pattern.limitQuantity(parts);
	}

	@Override
	public Stream<? extends GamePart> find(Predicate<GamePart> matcher) {
		return m_partSource.get().filter(matcher);
	}

	@Override
	public Stream<? extends GamePlace> find(GamePlacePattern pattern) {
		final Stream<? extends GamePlace> places = find(pattern.matchesParts()).flatMap(GamePart::places).filter(pattern.matches());
		return pattern.limitQuantity(places);
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