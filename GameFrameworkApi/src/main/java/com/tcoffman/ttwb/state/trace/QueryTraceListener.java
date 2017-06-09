package com.tcoffman.ttwb.state.trace;

import java.util.stream.Stream;

import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;

public interface QueryTraceListener {

	void start(GamePlacePattern pattern);

	void end(GamePlacePattern pattern);

	void start(GamePartPattern pattern);

	void end(GamePartPattern pattern);

	<T> Stream<T> source(Object source, Stream<T> items);

	<T> Stream<T> results(Stream<T> results);

}
