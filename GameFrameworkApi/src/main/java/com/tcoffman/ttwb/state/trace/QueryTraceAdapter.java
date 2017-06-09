package com.tcoffman.ttwb.state.trace;

import java.util.stream.Stream;

import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;

public class QueryTraceAdapter implements QueryTraceListener {

	@Override
	public void start(GamePlacePattern pattern) {
	}

	@Override
	public void end(GamePlacePattern pattern) {
	}

	@Override
	public void start(GamePartPattern pattern) {
	}

	@Override
	public void end(GamePartPattern pattern) {
	}

	@Override
	public <T> Stream<T> source(Object source, Stream<T> items) {
		return items;
	}

	@Override
	public <T> Stream<T> results(Stream<T> results) {
		return results;
	}

}
