package com.tcoffman.ttwb.state;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.AbstractEditor.Initializer;
import com.tcoffman.ttwb.component.GameComponent;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.xml.ModelGenerator;
import com.tcoffman.ttwb.doc.StandardComponentDocumentation;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.StandardGameRule;
import com.tcoffman.ttwb.model.pattern.part.StandardFilterPartPattern;
import com.tcoffman.ttwb.model.pattern.place.StandardAnyPlacePattern;
import com.tcoffman.ttwb.model.pattern.role.StandardGameAnyRolePattern;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.state.mutation.GameOperation;
import com.tcoffman.ttwb.state.mutation.GameOperationSet;
import com.tcoffman.ttwb.state.mutation.ResolvedOperationSet;

public class GameRunnerTest {

	private static final ModelGenerator MODEL_GENERATOR = ModelGenerator.instance();

	private interface Customizer<E extends AbstractEditor<? extends GameComponent>> {
		void configure(ModelGenerator.Result modelGenerationResults, E editor) throws GameComponentBuilderException;
	}

	private ModelGenerator.Result createModel(final Customizer<StandardGameRule.Editor> prologueRuleInitializer) throws PluginException {
		return MODEL_GENERATOR.createModel(new ModelGenerator.GeneratedModelCustomizer() {

			@Override
			public PluginName getDeclaringPlugin() {
				return CORE;
			}

			@Override
			public Initializer<StandardGameRule.Editor> getPrologueRuleInitializer(ModelGenerator.Result modelGenerationResults) {
				return (editor) -> prologueRuleInitializer.configure(modelGenerationResults, editor);
			}

		});
	}

	private void createAndAdvanceModel(StrategyOperator.Strategy strategy, final Customizer<StandardGameRule.Editor> prologueRuleInitializer)
			throws PluginException {
		final ModelGenerator.Result modelGenerationResults = createModel(prologueRuleInitializer);
		final GameState state = new StandardGameState(modelGenerationResults.getModel(), modelGenerationResults.getPluginSet());
		final GameParticipant participant = state.createParticipant(modelGenerationResults.getRole(), "AUTHORIZATION");
		final StrategyOperator randomOperator = new StrategyOperator(participant, strategy);
		final GameRunner runner = new GameRunner(state);
		assertThat(state.getCurrentStage().get(), equalTo(modelGenerationResults.getPrologue()));
		final GameOperationSet operationSet = randomOperator.calculateOperations(state);
		final ResolvedOperationSet resolvedOperationSet = runner.resolve(operationSet);
		runner.advance(resolvedOperationSet);
		assertThat(state.getCurrentStage().get(), equalTo(modelGenerationResults.getEpilogue()));
	}

	@Test
	public void canAutoRunSignal() throws XMLStreamException, PluginException {
		createAndAdvanceModel(new RandomOperatorStrategy(), (modelGenerationResults, prologue) -> {
			prologue.createOperationPattern((op) -> {
				op.setRolePattern(StandardGameAnyRolePattern.create().done());
				op.setType(GameOperation.Type.SIGNAL);
				op.setDocumentation(GameComponentRef.wrap(StandardComponentDocumentation.create().setDescription("TEST SIGNAL").done()));
			});
		});
	}

	@Test
	public void canAutoRunMove() throws XMLStreamException, PluginException {
		createAndAdvanceModel(
				new RandomOperatorStrategy(),
				(modelGenerationResults, prologue) -> {
					prologue.createOperationPattern((op) -> {
						op.setRolePattern(StandardGameAnyRolePattern.create().done());
						op.setType(GameOperation.Type.MOVE);
						op.setSubjectPlacePattern(StandardAnyPlacePattern
								.create()
								.setPartPattern(
										StandardFilterPartPattern.create()
												.setMatchPrototype(modelGenerationResults.getPartPrototypeA().self(GamePartPrototype.class)).done()).done());
						op.setTargetPlacePattern(StandardAnyPlacePattern
								.create()
								.setPartPattern(
										StandardFilterPartPattern.create()
												.setMatchPrototype(modelGenerationResults.getPartPrototypeB().self(GamePartPrototype.class)).done()).done());
						op.setDocumentation(GameComponentRef.wrap(StandardComponentDocumentation.create().setDescription("TEST MOVE").done()));
					});
				});
	}

}
