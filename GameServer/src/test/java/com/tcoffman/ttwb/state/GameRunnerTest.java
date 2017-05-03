package com.tcoffman.ttwb.state;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.xml.BundleHelper;
import com.tcoffman.ttwb.component.persistence.xml.BundleHelper.Bundle;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.plugin.PluginException;

public class GameRunnerTest {

	private static final BundleHelper BUNDLE_HELPER = BundleHelper.instance();

	@Test
	public void test() throws XMLStreamException, PluginException {
		final Bundle bundle = BUNDLE_HELPER.getBundle("minimal");
		final GameComponentRef<GameRole> role = bundle.getModelResolver().getRoleResolver().lookup("only-role")
				.orElseThrow(() -> new GameComponentBuilderException(CORE, "missing role"));
		final GameComponentRef<GameStage> prologue = bundle.getModelResolver().getStageResolver().lookup("prologue")
				.orElseThrow(() -> new GameComponentBuilderException(CORE, "missing stage"));
		final GameComponentRef<GameStage> epilogue = bundle.getModelResolver().getStageResolver().lookup("epilogue")
				.orElseThrow(() -> new GameComponentBuilderException(CORE, "missing stage"));

		final GameState state = new StandardGameState(bundle.getModel(), bundle.getPluginFactory());
		final GameParticipant participant = state.createParticipant(role.get());
		final GameRunner runner = new GameRunner(state);

		final StrategyOperator operator = new StrategyOperator(participant, new RandomOperatorStrategy());

		assertThat(state.getCurrentStage().get(), equalTo(prologue.get()));
		runner.advance(operator.calculateOperations(state));
		assertThat(state.getCurrentStage().get(), equalTo(epilogue.get()));

	}
}
