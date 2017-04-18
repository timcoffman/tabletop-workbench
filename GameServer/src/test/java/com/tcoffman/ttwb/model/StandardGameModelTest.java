package com.tcoffman.ttwb.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StandardGameModelTest {

	private static final String MODEL_NAME = "MODEL_NAME";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void cannotConfigureFurtherAfterCompletingConfiguration() throws GameModelBuilderException {
		final StandardGameModel.Editor editor = StandardGameModel.create();
		editor.setInitialStage(() -> null);
		editor.done();

		thrown.expect(IllegalStateException.class);
		editor.done();
	}

	@Test
	public void canConfigure() throws GameModelBuilderException {
		final StandardGameModel.Editor editor = StandardGameModel.create();
		editor.setInitialStage(() -> null);
		final GameModel model = editor.done();

		assertThat(model, notNullValue());
	}

	@Test
	public void canConfigureModelName() throws GameModelBuilderException {
		final StandardGameModel.Editor editor = StandardGameModel.create();
		editor.setName(MODEL_NAME);
		editor.setInitialStage(() -> null);
		final GameModel model = editor.done();

		assertThat(model.getName(), equalTo(MODEL_NAME));
	}
}
