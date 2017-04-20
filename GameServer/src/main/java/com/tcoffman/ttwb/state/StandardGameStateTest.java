package com.tcoffman.ttwb.state;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.tcoffman.ttwb.core.Core;
import com.tcoffman.ttwb.model.GameComponentRef;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.StandardGameModelBuilder;
import com.tcoffman.ttwb.model.StandardGamePartPrototype;
import com.tcoffman.ttwb.model.StandardGameStage;
import com.tcoffman.ttwb.model.pattern.GameOperationPatternSet;
import com.tcoffman.ttwb.plugin.DefaultPluginFactory;
import com.tcoffman.ttwb.plugin.ModelPlugin;
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
		@Override
		public PluginName getName() {
			return PLUGIN_NAME;
		}

		@Override
		public void setName(PluginName name) {
		}
	}

	@Test
	public void canInstantiateGameState() throws PluginException {
		final PluginFactory pluginFactory = mock(PluginFactory.class);
		when(pluginFactory.create(PLUGIN_NAME)).thenReturn(new TestPlugin());

		final GameModel model = mock(GameModel.class);
		when(model.getRequiredPlugins()).thenReturn(Arrays.asList(PLUGIN_NAME));
		when(model.parts()).thenReturn(Stream.empty());

		final StandardGameState state = new StandardGameState(model, pluginFactory);
		assertThat(state, notNullValue());
	}

	private DefaultPluginFactory m_pluginFactory;
	private StandardGameStage m_epilogueStage;
	private StandardGameStage m_prologueStage;
	private StandardGamePartPrototype m_prototype;
	private GameModel m_model;

	@Before
	public void setupModel() throws PluginException {
		m_pluginFactory = new DefaultPluginFactory();
		m_pluginFactory.install(CORE, Core.class);

		final PluginSet pluginSet = new PluginSet(m_pluginFactory);
		final ModelPlugin core = (ModelPlugin) pluginSet.requirePlugin(CORE);

		final GameComponentRef<GamePlaceType> placeTypeTop = core.getPlaceType(Core.PLACE_PHYSICAL_TOP);
		final GameComponentRef<GamePlaceType> placeTypeBottom = core.getPlaceType(Core.PLACE_PHYSICAL_BOTTOM);

		final Consumer<StandardGameStage> captureEpilogue = (s) -> m_epilogueStage = s;
		final Consumer<StandardGameStage> capturePrologue = (s) -> m_prologueStage = s;
		final Consumer<StandardGamePartPrototype> capturePrototype = (p) -> m_prototype = p;

		m_model = new StandardGameModelBuilder(m_pluginFactory).addRequiredPlugin(CORE).setName(MODEL_NAME)

				.createRole((r) -> {
				}).createPrototype(CORE, (proto) -> {

					proto.completed(capturePrototype);

			proto.createPlace((place) -> {

						place.setType(placeTypeTop);

			});

			proto.createPlace((place) -> {

				place.setType(placeTypeBottom);

					});

				}).createStage((s) -> {
					s.completed(captureEpilogue);

					s.setTerminal(true);
				})

				.createStage((s) -> {
					s.completed(capturePrologue);

					s.setTerminal(false);
					s.createRule(CORE, (r) -> {

						r.setResult(() -> m_epilogueStage);
						r.setType(GameOperation.Type.SIGNAL);

						r.createOperationPattern((p) -> {

							p.setType(GameOperation.Type.SIGNAL);
							p.setRolePattern(StandardGameAnyRolePattern.create().done());

						});

					});

				})

				.setInitialStage(() -> m_prologueStage)

				.build();
	}

	@Test
	public void modelRequiresCore() throws PluginException {
		assertThat(m_model.getRequiredPlugins(), hasItems(CORE));
	}

	@Test
	public void canSpecifyInitialStage() throws PluginException {
		assertThat(m_model.getInitialStage().get(), equalTo(m_prologueStage));
	}

	@Test
	public void canMutateGameState() throws PluginException {

		final StandardGameState state = new StandardGameState(m_model, m_pluginFactory);

		final List<StandardGameParticipant> participants = m_model.roles().map(state::assignRole).collect(Collectors.toList());

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

		assertThat(state.getCurrentStage().get(), equalTo(m_epilogueStage));

	}

}
