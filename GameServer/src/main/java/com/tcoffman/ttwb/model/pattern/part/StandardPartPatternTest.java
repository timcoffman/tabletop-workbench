package com.tcoffman.ttwb.model.pattern.part;

import static java.util.Collections.emptyList;

import org.junit.Test;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.state.pool.GamePartStaticPool;

public class StandardPartPatternTest {

	@Test
	public void canApplyAnyPartPattern() throws GameComponentBuilderException {
		StandardAnyPartPattern.create().done();

		new GamePartStaticPool(emptyList());

	}

}
