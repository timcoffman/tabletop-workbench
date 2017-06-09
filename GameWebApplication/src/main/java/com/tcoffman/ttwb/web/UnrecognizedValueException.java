package com.tcoffman.ttwb.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnrecognizedValueException extends Exception {

	private static final long serialVersionUID = 1L;
	private final List<?> m_allowedValues = new ArrayList<Object>();

	private static String buildMessage(String message, Stream<?> allowedValues) {
		return message + "; try one of " + allowedValues.map(Object::toString).map((s) -> "\"" + s + "\"").collect(Collectors.joining(", ", "[", "]"));
	}

	private static String buildMessage(Stream<?> allowedValues) {
		return buildMessage("unrecognized value", allowedValues);
	}

	public UnrecognizedValueException(Stream<?> allowedValues) {
		super(buildMessage(allowedValues));
	}

	public UnrecognizedValueException(String message, Stream<?> allowedValues, Throwable cause) {
		super(buildMessage(message, allowedValues), cause);
	}

	public UnrecognizedValueException(String message, Stream<?> allowedValues) {
		super(buildMessage(message, allowedValues));
	}

	public UnrecognizedValueException(Stream<?> allowedValues, Throwable cause) {
		super(buildMessage(allowedValues), cause);
	}

	public List<?> getAllowedValues() {
		return Collections.unmodifiableList(m_allowedValues);
	}

}
