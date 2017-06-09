package com.tcoffman.ttwb.model.pattern;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePlace;

public class StandardPatternContext implements AutoCloseable {

	private static ThreadLocal<StandardPatternContext> tl_stack = new ThreadLocal<StandardPatternContext>() {

		@Override
		protected StandardPatternContext initialValue() {
			return new RootStandardPatternContext();
		}
	};

	private static class RootStandardPatternContext extends StandardPatternContext {
		public RootStandardPatternContext() {
			super(0);
		}

		@Override
		protected Optional<Predicate<GamePlace>> outerPlaceMatcher(String token) {
			return Optional.empty();
		}

		@Override
		protected Optional<Predicate<GamePart>> outerPartMatcher(String token) {
			return Optional.empty();
		}

		@Override
		protected Optional<Predicate<Integer>> outerQuantityMatcher(String token) {
			return Optional.empty();
		}

	}

	public static StandardPatternContext current() {
		return tl_stack.get();
	}

	public static void with(Consumer<StandardPatternContext> consumer) {
		try (StandardPatternContext ctx = new StandardPatternContext()) {
			consumer.accept(ctx);
		}
	}

	private final String m_name;
	private final int m_level;
	private final StandardPatternContext m_outerContext;

	protected final Map<String, Predicate<GameRole>> m_roles = new HashMap<String, Predicate<GameRole>>();
	protected final Map<String, Predicate<GamePlace>> m_places = new HashMap<String, Predicate<GamePlace>>();
	protected final Map<String, Predicate<GamePart>> m_parts = new HashMap<String, Predicate<GamePart>>();
	protected final Map<String, Predicate<Integer>> m_quantities = new HashMap<String, Predicate<Integer>>();

	public StandardPatternContext() {
		this("context-" + (tl_stack.get().m_level + 1));
	}

	public StandardPatternContext(String name) {
		m_name = name;
		m_outerContext = tl_stack.get();
		m_level = m_outerContext.m_level + 1;
		tl_stack.set(this);
	}

	protected StandardPatternContext(int rootLevel) {
		m_name = "root";
		m_outerContext = null;
		m_level = rootLevel;
	}

	public StandardPatternContext bindRole(String token, Predicate<GameRole> role) {
		roleMatcher(token).ifPresent((m) -> {
			throw new IllegalStateException("cannot bind " + role + " to \"" + token + "\" because \"" + token + "\" is already bound to " + m);
		});
		m_roles.put(token, role);
		return this;
	}

	public StandardPatternContext bindPlace(String token, Predicate<GamePlace> place) {
		placeMatcher(token).ifPresent((m) -> {
			throw new IllegalStateException("cannot bind " + place + " to \"" + token + "\" because \"" + token + "\" is already bound to " + m);
		});
		m_places.put(token, place);
		return this;
	}

	public StandardPatternContext bindPart(String token, Predicate<GamePart> part) {
		partMatcher(token).ifPresent((m) -> {
			throw new IllegalStateException("cannot bind " + part + " to \"" + token + "\" because \"" + token + "\" is already bound to " + m);
		});
		m_parts.put(token, part);
		return this;
	}

	public StandardPatternContext bindQuantity(String token, Predicate<Integer> quantity) {
		partMatcher(token).ifPresent((m) -> {
			throw new IllegalStateException("cannot bind " + quantity + " to \"" + token + "\" because \"" + token + "\" is already bound to " + m);
		});
		m_quantities.put(token, quantity);
		return this;
	}

	public Optional<Predicate<GameRole>> roleMatcher(String token) {
		final Predicate<GameRole> roleMatcher = m_roles.get(token);
		return null != roleMatcher ? Optional.of(roleMatcher) : outerRoleMatcher(token);
	}

	public Optional<Predicate<GamePlace>> placeMatcher(String token) {
		final Predicate<GamePlace> placeMatcher = m_places.get(token);
		return null != placeMatcher ? Optional.of(placeMatcher) : outerPlaceMatcher(token);
	}

	public Optional<Predicate<GamePart>> partMatcher(String token) {
		final Predicate<GamePart> partMatcher = m_parts.get(token);
		return null != partMatcher ? Optional.of(partMatcher) : outerPartMatcher(token);
	}

	public Optional<Predicate<Integer>> quantityMatcher(String token) {
		final Predicate<Integer> quantityMatcher = m_quantities.get(token);
		return null != quantityMatcher ? Optional.of(quantityMatcher) : outerQuantityMatcher(token);
	}

	public Predicate<GameRole> requireRoleMatcher(String token) {
		return roleMatcher(token).orElseThrow(
				() -> new IllegalStateException("cannot provide role matcher for \"" + token + "\" because it was never bound in this context"));
	}

	public Predicate<GamePlace> requirePlaceMatcher(String token) {
		return placeMatcher(token).orElseThrow(
				() -> new IllegalStateException("cannot provide place matcher for \"" + token + "\" because it was never bound in this context"));
	}

	public Predicate<GamePart> requirePartMatcher(String token) {
		return partMatcher(token).orElseThrow(
				() -> new IllegalStateException("cannot provide part matcher for \"" + token + "\" because it was never bound in this context"));
	}

	public Predicate<Integer> requireQuantityMatcher(String token) {
		return quantityMatcher(token).orElseThrow(
				() -> new IllegalStateException("cannot provide quantity matcher for \"" + token + "\" because it was never bound in this context"));
	}

	protected Optional<Predicate<GameRole>> outerRoleMatcher(String token) {
		return m_outerContext.roleMatcher(token);
	}

	protected Optional<Predicate<GamePlace>> outerPlaceMatcher(String token) {
		return m_outerContext.placeMatcher(token);
	}

	protected Optional<Predicate<GamePart>> outerPartMatcher(String token) {
		return m_outerContext.partMatcher(token);
	}

	protected Optional<Predicate<Integer>> outerQuantityMatcher(String token) {
		return m_outerContext.quantityMatcher(token);
	}

	@Override
	public void close() throws IllegalStateException {
		if (null == m_outerContext)
			throw new IllegalStateException("attempted to close root context");
		tl_stack.set(m_outerContext);
	}

	@Override
	public String toString() {
		return "context \"" + m_name + "\" (" + m_level + ")";
	}

}
