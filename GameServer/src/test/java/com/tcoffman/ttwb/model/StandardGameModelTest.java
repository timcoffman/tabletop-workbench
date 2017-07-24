package com.tcoffman.ttwb.model;

import static com.tcoffman.ttwb.component.TestComponentRef.emptyRef;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;

public class StandardGameModelTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void cannotConfigureFurtherAfterCompletingConfiguration() throws GameComponentBuilderException {
		final StandardGameModel.Editor editor = StandardGameModel.create();
		editor.setDocumentation(GameComponentRef.wrap(mock(GameComponentDocumentation.class)));
		editor.setInitialStage(emptyRef());
		editor.addImportedModel(mock(GameModel.class));
		editor.done();

		thrown.expect(IllegalStateException.class);
		editor.done();
	}

	@Test
	public void canConfigure() throws GameComponentBuilderException {
		final StandardGameModel.Editor editor = StandardGameModel.create();
		editor.setDocumentation(GameComponentRef.wrap(mock(GameComponentDocumentation.class)));
		editor.setInitialStage(emptyRef());
		editor.addImportedModel(mock(GameModel.class));
		final GameModel model = editor.done();

		assertThat(model, notNullValue());
	}

	@Test
	public void canConfigureModelName() throws GameComponentBuilderException {
		final StandardGameModel.Editor editor = StandardGameModel.create();
		editor.setDocumentation(GameComponentRef.wrap(mock(GameComponentDocumentation.class)));
		editor.setInitialStage(emptyRef());
		editor.addImportedModel(mock(GameModel.class));
		editor.done();

	}
}
