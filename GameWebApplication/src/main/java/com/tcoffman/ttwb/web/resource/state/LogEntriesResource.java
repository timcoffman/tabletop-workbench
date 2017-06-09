package com.tcoffman.ttwb.web.resource.state;

import java.beans.IntrospectionException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.stream.XMLStreamException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.pattern.place.GamePlacePattern;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.state.GameRunner;
import com.tcoffman.ttwb.state.StandardGameMoveOperation;
import com.tcoffman.ttwb.state.StandardGameOperation;
import com.tcoffman.ttwb.state.StandardGameOperationSet;
import com.tcoffman.ttwb.state.StandardGameSignalOperation;
import com.tcoffman.ttwb.state.mutation.GameOperation;
import com.tcoffman.ttwb.state.mutation.GameStateLogEntry;
import com.tcoffman.ttwb.state.mutation.ResolvedOperationSet;
import com.tcoffman.ttwb.web.GameStateRepository;
import com.tcoffman.ttwb.web.UnrecognizedValueException;
import com.tcoffman.ttwb.web.resource.state.pattern.PatternUtils.PlacePatternForm;

public class LogEntriesResource extends AbstractStateSubresource {

	public LogEntriesResource(GameStateRepository.Bundle stateBundle) {
		super(stateBundle);
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return StatesResource.pathTo(uriBuilder).path(StateResource.class, "getLog").path(LogEntriesResource.class, "getEntry");
	}

	public URI getResource() {
		return StatesResource.pathTo(baseUriBuilder()).path(StateResource.class, "getLog").build(stateBundle().getStateId());
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<LogEntryResource> getLogEntries() {
		final List<LogEntryResource> logEntries = new ArrayList<LogEntryResource>();
		foreachWithIndex(stateBundle().getState().log(), (n, le) -> logEntries.add(createLogEntryResource(n, le)));
		return logEntries;
	}

	public static class StateMutationForm {
		private String m_role;
		private List<StateOperationForm> m_ops;
		private String m_result;

		public String getRole() {
			return m_role;
		}

		public void setRole(String roleId) {
			m_role = roleId;
		}

		public String getResult() {
			return m_result;
		}

		public void setResult(String result) {
			m_result = result;
		}

		public List<StateOperationForm> getOperations() {
			return m_ops;
		}

		public void setOperations(List<StateOperationForm> operationSet) {
			this.m_ops = operationSet;
		}

	}

	public static class StateOperationForm {
		private String m_type;
		private PlacePatternForm m_subject;
		private PlacePatternForm m_target;

		public String getType() {
			return m_type;
		}

		public void setType(String typeId) {
			m_type = typeId;
		}

		public PlacePatternForm getTarget() {
			return m_target;
		}

		public void setTarget(PlacePatternForm target) {
			m_target = target;
		}

		public PlacePatternForm getSubject() {
			return m_subject;
		}

		public void setSubject(PlacePatternForm subject) {
			m_subject = subject;
		}

	}

	public Map<String, Object> getCreate() throws IntrospectionException {
		return beanWritablePropertyMap(StateMutationForm.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public LogEntryResource mutate(StateMutationForm stateMutationForm) throws XMLStreamException, UnrecognizedValueException, PluginException {

		final StandardGameOperationSet operationSet = createOperationSet(stateMutationForm);

		final GameRunner runner = new GameRunner(stateBundle().getState());
		final ResolvedOperationSet resolvedOperationSet = runner.resolve(operationSet);
		System.out.println(resolvedOperationSet);

		final GameStateLogEntry logEntry = runner.advance(resolvedOperationSet);
		final long logEntryIndex = -1;
		stateBundle().store(this::getModelProvider);
		return createLogEntryResource(logEntryIndex, logEntry);
	}

	private StandardGameOperationSet createOperationSet(StateMutationForm stateMutationForm) throws GameComponentBuilderException, UnrecognizedValueException {
		final GameComponentRef<GameStage> resultStage = lookupStage(modelBundle(), stateMutationForm.getResult());
		final GameRole role = lookupRole(stateMutationForm.getRole()).get();
		final StandardGameOperationSet operationSet = new StandardGameOperationSet(resultStage);
		for (final StateOperationForm operationForm : stateMutationForm.getOperations()) {
			final StandardGameOperation op = createOperation(role, operationForm);
			operationSet.add(op);
		}
		return operationSet;
	}

	private StandardGameOperation createOperation(final GameRole role, final StateOperationForm operationForm) throws GameComponentBuilderException,
			UnrecognizedValueException {
		final GameOperation.Type type = GameOperation.Type.valueOf(operationForm.getType());
		switch (type) {
		case SIGNAL:
			return new StandardGameSignalOperation(role);
		case MOVE:
			return new StandardGameMoveOperation(role, createPlacePattern(operationForm.getSubject()).get(), createPlacePattern(operationForm.getTarget())
					.get());
		default:
			throw new UnrecognizedValueException(operationForm.getType() + " not recognized", Stream.of(GameOperation.Type.values()));
		}
	}

	private Optional<GamePlacePattern> createPlacePattern(PlacePatternForm patternForm) throws UnrecognizedValueException, GameComponentBuilderException {
		return patternUtils().createPlacePattern(patternForm);
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/{logEntryIndex}")
	public LogEntryResource getEntry(@PathParam("logEntryIndex") long logEntryIndex) throws GameComponentBuilderException, XMLStreamException {
		final GameStateLogEntry logEntry = nthElement(logEntryIndex, stateBundle().getState().log()).orElseThrow(this::missingModelComponent);
		return createLogEntryResource(logEntryIndex, logEntry);
	}

	private LogEntryResource createLogEntryResource(long logEntryIndex, GameStateLogEntry logEntry) {
		return subresource(new LogEntryResource(stateBundle(), logEntryIndex, logEntry));
	}

}