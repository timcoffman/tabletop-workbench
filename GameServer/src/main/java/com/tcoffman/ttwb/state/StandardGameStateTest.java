package com.tcoffman.ttwb.state;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.junit.Test;

import com.tcoffman.ttwb.core.Core;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.StandardGameModelBuilder;
import com.tcoffman.ttwb.model.StandardGameRule;
import com.tcoffman.ttwb.model.StandardGameStage;
import com.tcoffman.ttwb.model.pattern.GameOperationPatternSet;
import com.tcoffman.ttwb.model.pattern.StandardGameOperationPattern;
import com.tcoffman.ttwb.model.persistance.xml.StandardGameModelParser;
import com.tcoffman.ttwb.plugin.DefaultPluginFactory;
import com.tcoffman.ttwb.plugin.Plugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.PluginSet;
import com.tcoffman.ttwb.state.pattern.StandardGameAnyRolePattern;

public class StandardGameStateTest {

	private static final String MODEL_NAME = "MODEL_NAME";
	private static final PluginName PLUGIN_NAME = new PluginName("a.b.c", "1.0");

	private static class TestPlugin implements Plugin {

	}

	@Test
	public void canInstantiateGameState() throws PluginException {
		final PluginFactory pluginFactory = mock(PluginFactory.class);
		when(pluginFactory.create(PLUGIN_NAME)).thenReturn(new TestPlugin());

		final GameModel model = mock(GameModel.class);
		when(model.getRequiredPlugins()).thenReturn(Arrays.asList(PLUGIN_NAME));

		final StandardGameState state = new StandardGameState(model, pluginFactory);
		assertThat(state, notNullValue());
	}

	@Test
	public void canMutateGameState() throws PluginException {
		final DefaultPluginFactory pluginFactory = new DefaultPluginFactory();
		pluginFactory.install(CORE, Core.class);

		new PluginSet(pluginFactory);

		final StandardGameModelBuilder builder = new StandardGameModelBuilder(pluginFactory);
		builder.addRequiredPlugin(CORE);
		builder.setName(MODEL_NAME);

		builder.createRole().done();

		final StandardGameStage.Editor epilogStageEditor = builder.createStage();
		epilogStageEditor.setTerminal(true);
		final StandardGameStage epilogStage = epilogStageEditor.done();

		final StandardGameStage.Editor prologStageEditor = builder.createStage();
		prologStageEditor.setTerminal(false);
		final StandardGameRule.Editor ruleEditor = prologStageEditor.createRule(CORE);
		ruleEditor.setResult(() -> epilogStage);
		ruleEditor.setType(GameOperation.Type.SIGNAL);

		final StandardGameOperationPattern.Editor opPatternEditor = ruleEditor.createOperationPattern();
		opPatternEditor.setType(GameOperation.Type.SIGNAL);
		opPatternEditor.setRolePattern(StandardGameAnyRolePattern.create().done());
		opPatternEditor.done();

		ruleEditor.done();

		final StandardGameStage prologStage = prologStageEditor.done();
		builder.setInitialStage(() -> prologStage);

		final GameModel model = builder.build();

		assertThat(model.getRequiredPlugins(), hasItems(CORE));
		assertThat(model.getInitialStage().get(), equalTo(prologStage));

		final StandardGameModelParser m_standardGameModelParser = new StandardGameModelParser(pluginFactory);
		try {
			m_standardGameModelParser.write(model, new java.io.FileOutputStream(new java.io.File(
					"src/test/resources/com/tcoffman/ttwb/model/persistence/xml/test.xml")));
		} catch (final TransformerException | FileNotFoundException ex) {
			ex.printStackTrace();
		}

		final StandardGameState state = new StandardGameState(model, pluginFactory);

		final List<StandardGameParticipant> participants = model.roles().map(state::assignRole).collect(Collectors.toList());

		final GameRunner runner = new GameRunner();
		while (!state.getCurrentStage().get().isTerminal()) {
			final GameOperationPatternSet opPatternSet = state.allowedOperations().findAny().orElseThrow(state::makeDeadEndException);
			opPatternSet.operations().findAny().orElseThrow(state::makeDeadEndException);
			final GameRole opRole = participants.stream().map(GameParticipant::getRole).findFirst().orElseThrow(state::makeDeadEndException);

			final StandardGameOperation op = new StandardGameSignalOperation(opRole);
			final StandardGameOperationSet opSet = new StandardGameOperationSet(opPatternSet.getResult());
			opSet.add(op);
			runner.mutate(state, opSet);
		}

		assertThat(state.getCurrentStage().get(), equalTo(epilogStage));

	}

	@Test
	public void canMutateGameState2() throws PluginException {

		final PluginFactory pluginFactory = mock(PluginFactory.class);
		final StandardGameModelParser parser = new StandardGameModelParser(pluginFactory);
		GameModel model;
		try {
			model = parser.parse(new java.io.FileInputStream(new java.io.File("src/test/resources/com/tcoffman/ttwb/model/persistence/xml/test.xml")));
		} catch (FileNotFoundException | XMLStreamException ex) {
			throw new RuntimeException(ex);
		}

		final GameStage epilogStage = model.stages().filter(GameStage::isTerminal).findAny().orElseThrow(() -> new IllegalStateException("no terminal stage"));

		final StandardGameState state = new StandardGameState(model, pluginFactory);

		final List<StandardGameParticipant> participants = model.roles().map(state::assignRole).collect(Collectors.toList());

		final GameRunner runner = new GameRunner();
		while (!state.getCurrentStage().get().isTerminal()) {
			final GameOperationPatternSet opPatternSet = state.allowedOperations().findAny().orElseThrow(state::makeDeadEndException);
			opPatternSet.operations().findAny().orElseThrow(state::makeDeadEndException);
			final GameRole opRole = participants.stream().map(GameParticipant::getRole).findFirst().orElseThrow(state::makeDeadEndException);

			final StandardGameOperation op = new StandardGameSignalOperation(opRole);
			final StandardGameOperationSet opSet = new StandardGameOperationSet(opPatternSet.getResult());
			opSet.add(op);
			runner.mutate(state, opSet);
		}

		assertThat(state.getCurrentStage().get(), equalTo(epilogStage));

	}
}
