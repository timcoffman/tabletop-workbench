package com.tcoffman.ttwb.web;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.PrintStream;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.tcoffman.ttwb.component.persistence.RespositoryBasedModelProvider;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.part.GamePartPattern;
import com.tcoffman.ttwb.model.pattern.part.StandardAnyPartPattern;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.model.pattern.place.StandardAnyPlacePattern;
import com.tcoffman.ttwb.model.pattern.quantity.GameQuantityPattern;
import com.tcoffman.ttwb.model.pattern.quantity.StandardAnyQuantityPattern;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePlace;
import com.tcoffman.ttwb.state.GameRunner;
import com.tcoffman.ttwb.state.GameState;
import com.tcoffman.ttwb.state.mutation.GameStateLogEntry;
import com.tcoffman.ttwb.state.persistence.ModelProvider;

public class GameRunnerTest {

	private GameModelFileRepository m_modelRepository;
	private GameStateFileRepository m_stateRepository;
	private Function<String, ModelProvider> m_modelProvider;

	@Before
	public void setupRepositories() {
		m_modelRepository = new GameModelFileRepository();
		m_stateRepository = new GameStateFileRepository();
		m_modelProvider = (modelId) -> new RespositoryBasedModelProvider(modelId, () -> m_modelRepository);
	}

	private static class StateWriter {
		private final PrintStream m_os;

		public StateWriter(PrintStream os) {
			m_os = os;
		}

		public StateWriter write(GameState gameState) {
			gameState.parts().forEach(this::write);
			return this;
		}

		public StateWriter write(GamePart gamePart) {
			m_os.println(gamePart.getPrototype().get().getDocumentation().getName(GameComponentDocumentation.Format.SHORT));
			gamePart.places().forEach(this::write);
			return this;
		}

		private String desc(GamePartPrototype partPrototype) {
			return partPrototype.getDocumentation().getName(GameComponentDocumentation.Format.SHORT);
		}

		public StateWriter write(GamePlace gamePlace) {
			m_os.print(" \\-");
			m_os.println(" " + gamePlace.getPrototype().get().getDocumentation().getName(GameComponentDocumentation.Format.SHORT));
			gamePlace.outgoingRelationships().forEach((rel) -> {
				m_os.print("     \\-");
				m_os.print(" >> " + rel.getType().get().getLocalName() + "{" + rel.getType().get().getDeclaringPlugin().getQualifiedName() + "}");
				m_os.println(" >> " + rel.getDestination().get().getPrototype().get().getDocumentation().getName(GameComponentDocumentation.Format.SHORT)
						+ " - " + desc(rel.getDestination().get().getOwner().getPrototype().get()));
			});
			gamePlace.incomingRelationships().forEach((rel) -> {
				m_os.print("     \\-");
				m_os.println(" >> " + rel.getSource().get().getPrototype().get().getDocumentation().getName(GameComponentDocumentation.Format.SHORT) + " - "
						+ rel.getSource().get().getOwner().getPrototype().get().getDocumentation().getName(GameComponentDocumentation.Format.SHORT));
			});
			return this;
		}
	}

	private class PatternDumper {
		private final PrintStream m_ps;
		private final GameState m_state;

		public PatternDumper(GameState state, PrintStream ps) {
			m_state = state;
			m_ps = ps;
		}

		public void dump(GamePartPattern p) {
			m_ps.print("PART PATTERN: ");
			m_ps.println(p);
			m_ps.print("RESULT -> ");
			m_ps.println(m_state.find(p).collect(Collectors.toList()));
		}

		public void dump(GamePlacePattern p) {
			m_ps.print("PLACE PATTERN: ");
			m_ps.println(p);
			m_ps.print("RESULT -> ");
			m_ps.println(m_state.find(p).collect(Collectors.toList()));
		}

		public void dump(GameQuantityPattern p) {
			m_ps.print("QUANTITY PATTERN: ");
			m_ps.println(p);
		}

		public void dump(GameOperationPattern p) {
			p.getSubjectPlacePattern().ifPresent(this::dump);
			p.getSubjectPattern().ifPresent(this::dump);
			p.getTargetPlacePattern().ifPresent(this::dump);
			p.getTargetPattern().ifPresent(this::dump);
		}
	}

	@Test
	public void test() throws PluginException {
		final GameModelFileRepository.Bundle modelBundle = m_modelRepository.getBundle("complete");
		final GameStateFileRepository.Bundle stateBundle = m_stateRepository.create(modelBundle.getModel(), modelBundle.getModelId(),
				modelBundle.getPluginSet(), modelBundle.getModelRefResolver());
		final PatternDumper pd = new PatternDumper(stateBundle.getState(), System.out);

		pd.dump(StandardAnyPartPattern.create().done());
		pd.dump(StandardAnyPartPattern.create().setQuantityPattern(StandardAnyQuantityPattern.create().done()).done());
		pd.dump(StandardAnyPlacePattern.create().done());
		pd.dump(StandardAnyPlacePattern.create().setQuantityPattern(StandardAnyQuantityPattern.create().done()).done());

		modelBundle.getModel().getInitialStage().get().get().rules().findFirst().get().operationPatterns().forEach((op) -> {
			System.out.println("---");
			pd.dump(op);
		});
	}

	@Test
	public void canAutoAdvance() throws PluginException {
		final GameModelFileRepository.Bundle modelBundle = m_modelRepository.getBundle("complete");
		final GameStateFileRepository.Bundle stateBundle = m_stateRepository.create(modelBundle.getModel(), modelBundle.getModelId(),
				modelBundle.getPluginSet(), modelBundle.getModelRefResolver());
		final GameRunner runner = new GameRunner(stateBundle.getState());
		new StateWriter(System.out).write(stateBundle.getState());
		final Optional<GameStateLogEntry> autoAdvanceLog = runner.autoAdvance();
		assertThat(autoAdvanceLog.isPresent(), equalTo(true));
		stateBundle.store(m_modelProvider);
		System.out.println(autoAdvanceLog.get());
		new StateWriter(System.out).write(stateBundle.getState());
	}

}
