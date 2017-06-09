package com.tcoffman.ttwb.state;

import static com.tcoffman.ttwb.doc.GameComponentDocumentation.Format.LONG;
import static com.tcoffman.ttwb.doc.GameComponentDocumentation.Format.SHORT;
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

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.core.Core;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.doc.StandardComponentDocumentation;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GamePartInstance;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.StandardGameModelBuilder;
import com.tcoffman.ttwb.model.StandardGamePartPrototype;
import com.tcoffman.ttwb.model.StandardGameStage;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPatternSet;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.part.StandardFilterPartPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.model.pattern.place.StandardFilterPlacePattern;
import com.tcoffman.ttwb.model.pattern.role.StandardGameAnyRolePattern;
import com.tcoffman.ttwb.plugin.DefaultPluginFactory;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.Plugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.PluginSet;
import com.tcoffman.ttwb.state.mutation.GameOperation;
import com.tcoffman.ttwb.state.mutation.ResolvedOperationSet;

public class StandardGameStateTest {

	private static final PluginName PLUGIN_NAME = new PluginName("a.b.c", "1.0");

	private PluginSet m_pluginSet;
	private StandardGameStage m_epilogueStage;
	private StandardGameStage m_sagaStage;
	private StandardGameStage m_prologueStage;
	private StandardGamePartPrototype m_prototype;
	private GameModel m_model;

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
		final PluginSet pluginSet = mock(PluginSet.class);
		when(pluginSet.requirePlugin(PLUGIN_NAME)).thenReturn(new TestPlugin());

		final GameModel model = mock(GameModel.class);
		when(model.getRequiredPlugins()).thenReturn(Arrays.asList(PLUGIN_NAME));
		when(model.parts()).thenReturn(Stream.empty());

		final StandardGameState state = new StandardGameState(model, pluginSet);
		assertThat(state, notNullValue());
	}

	private class DocumentationGenerator {
		public GameComponentRef<GameComponentDocumentation> gen(String shortName) throws GameComponentBuilderException {
			return gen(shortName, shortName);
		}

		public GameComponentRef<GameComponentDocumentation> gen(String shortName, String longName) throws GameComponentBuilderException {
			return gen(shortName, longName, longName);
		}

		public GameComponentRef<GameComponentDocumentation> gen(String shortName, String longName, String description) throws GameComponentBuilderException {
			final StandardComponentDocumentation doc = StandardComponentDocumentation.create().setName(SHORT, shortName).setName(LONG, longName)
					.setDescription(description).done();
			return doc.self(GameComponentDocumentation.class);
		}
	}

	@Before
	public void setupModel() throws PluginException {
		final DefaultPluginFactory m_pluginFactory = new DefaultPluginFactory();
		m_pluginFactory.install(CORE, Core.class);

		m_pluginSet = new PluginSet(m_pluginFactory);
		final ModelPlugin core = (ModelPlugin) m_pluginSet.requirePlugin(CORE);

		final GameComponentRef<GamePlaceType> placeTypeTop = core.getPlaceType(Core.PLACE_PHYSICAL_TOP);
		final GameComponentRef<GamePlaceType> placeTypeBottom = core.getPlaceType(Core.PLACE_PHYSICAL_BOTTOM);

		final Consumer<StandardGameStage> captureEpilogue = (s) -> m_epilogueStage = s;
		final Consumer<StandardGameStage> captureSaga = (s) -> m_sagaStage = s;
		final Consumer<StandardGameStage> capturePrologue = (s) -> m_prologueStage = s;
		final Consumer<StandardGamePartPrototype> capturePrototype = (p) -> m_prototype = p;

		final DocumentationGenerator doc = new DocumentationGenerator();

		m_model = new StandardGameModelBuilder(m_pluginSet)
				.addRequiredPlugin(CORE)
				.setDocumentation(doc.gen("MODEL"))

				.createRole((r) -> {
					r.setDocumentation(doc.gen("ROLE"));
				})
				.createPrototype(CORE, (proto) -> {

					proto.completed(capturePrototype);
					proto.setDocumentation(doc.gen("PROTOTYPE"));

					proto.createPlace((place) -> {
						place.setDocumentation(doc.gen("TOP"));

						place.setType(placeTypeTop);

					});

					proto.createPlace((place) -> {
						place.setDocumentation(doc.gen("BOTTOM"));

						place.setType(placeTypeBottom);

					});

				})

				.createPart((p) -> {
					p.setPrototype(GameComponentRef.deferred(() -> m_prototype));
				})
				.createPart((p) -> {
					p.setPrototype(GameComponentRef.deferred(() -> m_prototype));
				})

				.createStage((s) -> {
					s.completed(capturePrologue);
					s.setDocumentation(doc.gen("PROLOGUE"));

					s.setTerminal(false);
					s.createRule(CORE, (r) -> {
						r.setDocumentation(doc.gen("RULE"));

						r.setResult(GameComponentRef.deferred(() -> m_sagaStage));

						r.createOperationPattern((p) -> {

							p.setType(GameOperation.Type.SIGNAL);
							p.setRolePattern(StandardGameAnyRolePattern.create().done());
							p.setDocumentation(doc.gen("TEST SIGNAL"));

						});

					});

				})

				.createStage(
						(s) -> {
							s.completed(captureSaga);
							s.setDocumentation(doc.gen("SAGA"));

							s.setTerminal(false);
							s.createRule(
									CORE,
									(r) -> {
										r.setDocumentation(doc.gen("RULE"));

										r.setResult(GameComponentRef.deferred(() -> m_epilogueStage));

										r.createOperationPattern((p) -> {

											p.setType(GameOperation.Type.MOVE);

											p.setRolePattern(StandardGameAnyRolePattern.create().done());

											final GamePartPattern subjectPartPattern = StandardFilterPartPattern.create()
											.setMatchPrototype(GameComponentRef.deferred(() -> m_prototype)).done();
											final GamePlacePattern subjectPlacePattern = StandardFilterPlacePattern.create().setPartPattern(subjectPartPattern)
													.setMatchType(placeTypeBottom).done();
											p.setSubjectPlacePattern(subjectPlacePattern);

											final GamePartPattern targetPartPattern = StandardFilterPartPattern.create()
											.setMatchPrototype(GameComponentRef.deferred(() -> m_prototype)).done();
											final GamePlacePattern targetPlacePattern = StandardFilterPlacePattern.create().setPartPattern(targetPartPattern)
													.setMatchType(placeTypeTop).done();
											p.setTargetPlacePattern(targetPlacePattern);

											p.setDocumentation(doc.gen("TEST MOVE"));
										});

									});

						})

				.createStage((s) -> {
					s.completed(captureEpilogue);
					s.setDocumentation(doc.gen("EPILOGUE"));

					s.setTerminal(true);
				})

				.setInitialStage(GameComponentRef.deferred(() -> m_prologueStage))

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
	public void canSpecifyParts() throws PluginException {
		assertThat(m_model.parts().collect(Collectors.counting()), equalTo(2L));
		assertThat(m_model.parts().map(GamePartInstance::getPrototype).map(GameComponentRef::get).collect(Collectors.toSet()), hasItems(m_prototype));
	}

	@Test
	public void canMutateGameState() throws PluginException {

		final StandardGameState state = new StandardGameState(m_model, m_pluginSet);

		final List<? extends GameParticipant> participants = m_model.roles().map((p) -> state.createParticipant(p, "AUTHORIZATION"))
				.collect(Collectors.toList());

		final GameRunner runner = new GameRunner(state);
		while (!state.getCurrentStage().get().isTerminal()) {
			final GameOperationPatternSet opPatternSet = state.allowedOperations().findAny().orElseThrow(state::makeDeadEndException);
			final StandardGameOperationSet opSet = fulfill(state, opPatternSet, participants);
			final ResolvedOperationSet resolvedOpSet = runner.resolve(opSet);
			runner.advance(resolvedOpSet);
		}

		assertThat(state.getCurrentStage().get(), equalTo(m_epilogueStage));

	}

	private StandardGameOperationSet fulfill(final StandardGameState state, GameOperationPatternSet opPatternSet,
			final List<? extends GameParticipant> participants) throws GameStateException {
		final GameRole opRole = participants.stream().map(GameParticipant::getRole).findFirst().orElseThrow(state::makeDeadEndException);

		final StandardGameOperationSet opSet = new StandardGameOperationSet(opPatternSet.getResult());
		opPatternSet.operations().forEach((opPattern) -> {
			final StandardGameOperation op = fulfill(state, opPattern, opRole);
			opSet.add(op);
		});
		return opSet;
	}

	private StandardGameOperation fulfill(final StandardGameState state, GameOperationPattern opPattern, final GameRole opRole) {
		switch (opPattern.getType()) {
		case SIGNAL:
			return fulfillSignal(state, opPattern, opRole);
		case MOVE:
			return fulfillMove(state, opPattern, opRole);
		default:
			throw new UnsupportedOperationException("cannot fulfill a \"" + opPattern.getType() + "\" operation pattern");
		}

	}

	private StandardGameOperation fulfillSignal(final StandardGameState state, GameOperationPattern opPattern, final GameRole opRole) {
		return new StandardGameSignalOperation(opRole);
	}

	private StandardGameOperation fulfillMove(final StateView view, GameOperationPattern opPattern, final GameRole opRole) {
		final GamePlacePattern subjectPlacePattern = opPattern.getSubjectPlacePattern().orElseThrow(
				() -> new IllegalStateException("move op cannot be fulfilled: no subject"));
		final GamePlacePattern targetPlacePattern = opPattern.getTargetPlacePattern().orElseThrow(
				() -> new IllegalStateException("move op cannot be fulfilled: no target"));

		return new StandardGameMoveOperation(opRole, subjectPlacePattern, targetPlacePattern);
	}
}
