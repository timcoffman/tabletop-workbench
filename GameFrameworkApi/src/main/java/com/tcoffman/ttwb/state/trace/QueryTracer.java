package com.tcoffman.ttwb.state.trace;

import java.io.PrintStream;
import java.util.stream.Stream;

import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePlace;

public class QueryTracer {

	private static final ThreadLocal<QueryTraceListener> tl_tracer = new ThreadLocal<QueryTraceListener>() {

		@Override
		protected QueryTraceListener initialValue() {
			return NULL_LISTENER;
		}

	};

	private static final QueryTraceAdapter NULL_LISTENER = new QueryTraceAdapter();

	private QueryTracer() {
	}

	private final static QueryTracer INSTANCE = new QueryTracer();

	public static QueryTracer tracer() {
		return INSTANCE;
	}

	public QueryTracer listeningWith(QueryTraceListener listener) {
		tl_tracer.set(listener);
		return this;
	}

	public QueryTracer printingOn(PrintStream printStream) {
		return listeningWith(new QueryTracePrinter(printStream));
	}

	public interface QueryTrace<T, E extends Exception> extends AutoCloseable {
		@Override
		void close() throws E;

		<U extends T> Stream<U> source(Object source, Stream<U> items);

		<U extends T> Stream<U> results(Stream<U> results);
	}

	private class QueryTracePlacePattern<E extends Exception> implements QueryTrace<GamePlace, E> {

		private final GamePlacePattern m_pattern;

		public QueryTracePlacePattern(GamePlacePattern pattern) {
			m_pattern = pattern;
			tl_tracer.get().start(m_pattern);
		}

		@Override
		public void close() throws E {
			tl_tracer.get().end(m_pattern);
		}

		@Override
		public <U extends GamePlace> Stream<U> source(Object source, Stream<U> items) {
			return tl_tracer.get().source(source, items);
		}

		@Override
		public <U extends GamePlace> Stream<U> results(Stream<U> results) {
			return tl_tracer.get().results(results);
		}

	}

	private class QueryTracePartPattern<E extends Exception> implements QueryTrace<GamePart, E> {

		private final GamePartPattern m_pattern;

		public QueryTracePartPattern(GamePartPattern pattern) {
			m_pattern = pattern;
			tl_tracer.get().start(m_pattern);
		}

		@Override
		public void close() throws E {
			tl_tracer.get().end(m_pattern);
		}

		@Override
		public <U extends GamePart> Stream<U> source(Object source, Stream<U> items) {
			return tl_tracer.get().source(source, items);
		}

		@Override
		public <U extends GamePart> Stream<U> results(Stream<U> results) {
			return tl_tracer.get().results(results);
		}

	}

	public <E extends Exception> QueryTrace<GamePlace, E> trace(GamePlacePattern pattern, Class<E> exceptionClass) {
		return new QueryTracePlacePattern<E>(pattern);
	}

	public QueryTrace<GamePlace, RuntimeException> trace(GamePlacePattern pattern) {
		return trace(pattern, RuntimeException.class);
	}

	public <E extends Exception> QueryTrace<GamePart, E> trace(GamePartPattern pattern, Class<E> exceptionClass) {
		return new QueryTracePartPattern<E>(pattern);
	}

	public QueryTrace<GamePart, RuntimeException> trace(GamePartPattern pattern) {
		return trace(pattern, RuntimeException.class);
	}

}
