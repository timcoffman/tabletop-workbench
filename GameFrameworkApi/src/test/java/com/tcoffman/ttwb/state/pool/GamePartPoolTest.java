package com.tcoffman.ttwb.state.pool;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GamePartPoolTest {

	@Test
	public void emptyPoolHasNoResults() {
		final GamePartPool pool = new GamePartPool(emptyList());
		final GamePartSet all = pool.all();
		assertThat(all.isEmpty(), equalTo(true));
	}

	@Test
	public void canRetrieveItemFromPool() {
		final GamePartPool pool = new GamePartPool();

		pool.add();

		final GamePartSet all = pool.all();
		assertThat(all.isEmpty(), equalTo(false));
	}

}
