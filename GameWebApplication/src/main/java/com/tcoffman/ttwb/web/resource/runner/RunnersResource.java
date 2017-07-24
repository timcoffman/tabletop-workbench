package com.tcoffman.ttwb.web.resource.runner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.stream.XMLStreamException;

import org.json.JSONException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.web.GameModelFileRepository;
import com.tcoffman.ttwb.web.GameStateFileRepository;
import com.tcoffman.ttwb.web.GameStateFileRepository.Bundle;
import com.tcoffman.ttwb.web.resource.AbstractRootResource;

@Path("/runners")
public class RunnersResource extends AbstractRootResource {

	public RunnersResource() {
		super();
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return uriBuilder.path(RunnersResource.class).path(RunnersResource.class, "getRunner");
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RunnerResource> getRunners() {
		final List<RunnerResource> states = new ArrayList<>();
		for (final Iterator<? extends String> i = m_stateRepository.stateIds().iterator(); i.hasNext();)
			try {
				states.add(createRunnerResource(getStateBundle(i.next())));
			} catch (final GameComponentBuilderException ex) {
				// don't add
				ex.printStackTrace();
			}
		return states;
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/{runnerId}")
	public RunnerResource getRunner(@PathParam("stateId") String stateId) throws GameComponentBuilderException, XMLStreamException {
		return createRunnerResource(getStateBundle(stateId));
	}

	private Bundle getStateBundle(String stateId) throws GameComponentBuilderException {
		return m_stateRepository.getBundle(stateId, this::getModelProvider);
	}

	public static class RunnerCreationForm {
		private String m_state;

		public String getState() {
			return m_state;
		}

		public void setModel(String state) {
			m_state = state;
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RunnerResource createRunner(RunnerCreationForm runnerCreationForm) throws JSONException, GameComponentBuilderException, XMLStreamException {
		final String modelId = runnerCreationForm.getState();
		final GameModelFileRepository.Bundle modelBundle = m_modelRepository.getBundle(modelId);
		final GameStateFileRepository.Bundle stateBundle = m_stateRepository.create(modelBundle.getModel(), modelBundle.getModelId(), modelBundle.getPluginSet(),
				modelBundle.getModelRefResolver());

		return createRunnerResource(stateBundle);
	}

	private RunnerResource createRunnerResource(final GameStateFileRepository.Bundle stateBundle) {
		return subresource(new RunnerResource(stateBundle));
	}

}
