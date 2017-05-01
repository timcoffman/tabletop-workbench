package com.tcoffman.ttwb.state.pattern;

import java.util.function.Predicate;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.pattern.GamePartPattern;
import com.tcoffman.ttwb.state.GamePart;

public class StandardAnyPartPattern implements GamePartPattern {

	@Override
	public Predicate<GamePart> matches() {
		return (p) -> true;
	}

	private StandardAnyPartPattern() {
	}

	public static Editor create() {
		return new StandardAnyPartPattern().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public final class Editor extends AbstractEditor<StandardAnyPartPattern> {

		private Editor() {
		}

		@Override
		protected void validate() throws GameComponentBuilderException {
		}

		@Override
		protected StandardAnyPartPattern model() {
			return StandardAnyPartPattern.this;
		}

	}

	@Override
	public String toString() {
		return PartPatternFormatter.create().toString();
	}

}
