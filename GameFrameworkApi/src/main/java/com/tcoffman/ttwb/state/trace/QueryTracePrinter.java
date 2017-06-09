package com.tcoffman.ttwb.state.trace;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;

public class QueryTracePrinter extends QueryTraceAdapter {

	private final PrintStream m_printStream;
	private int m_indentLevel = 0;

	public QueryTracePrinter() {
		this(System.err);
	}

	public QueryTracePrinter(PrintStream printStream) {
		m_printStream = printStream;
	}

	private PrintStream indent() {
		for (int i = 0; i < m_indentLevel; ++i)
			m_printStream.print("  ");
		return m_printStream;
	}

	private <T> void println(List<T> items) {
		indent().println("-> " + items.stream().map(Object::toString).collect(Collectors.joining(", ")));
	}

	@Override
	public void start(GamePlacePattern pattern) {
		indent().println(pattern + ":");
		++m_indentLevel;
	}

	@Override
	public void end(GamePlacePattern pattern) {
		--m_indentLevel;
		indent();
	}

	@Override
	public <T> Stream<T> source(Object source, Stream<T> items) {
		return items.collect(peekAll((a) -> indent().println("matching on " + a.size() + " item(s) from " + source)));
	}

	@Override
	public <T> Stream<T> results(Stream<T> results) {
		return results.collect(peekAll(this::println));
	}

	@Override
	public void start(GamePartPattern pattern) {
		indent().println(pattern + ":");
		++m_indentLevel;
	}

	@Override
	public void end(GamePartPattern pattern) {
		--m_indentLevel;
		indent();
	}

	private <T> Collector<T, List<T>, Stream<T>> peek(Consumer<T> consumer) {
		final Supplier<List<T>> supplier = ArrayList::new;
		final BiConsumer<List<T>, T> accumulator = (a, t) -> {
			consumer.accept(t);
			a.add(t);
		};
		final BinaryOperator<List<T>> combiner = (a, b) -> {
			a.addAll(b);
			return a;
		};
		final Function<List<T>, Stream<T>> finisher = List::stream;
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	private <T> Collector<T, List<T>, Stream<T>> peekAll(Consumer<List<T>> consumer) {
		final Supplier<List<T>> supplier = ArrayList::new;
		final BiConsumer<List<T>, T> accumulator = List::add;
		final BinaryOperator<List<T>> combiner = (a, b) -> {
			a.addAll(b);
			return a;
		};
		final Function<List<T>, Stream<T>> finisher = (a) -> {
			consumer.accept(a);
			return a.stream();
		};
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

}
