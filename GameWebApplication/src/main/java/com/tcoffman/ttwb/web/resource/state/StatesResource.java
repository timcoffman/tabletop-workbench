package com.tcoffman.ttwb.web.resource.state;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

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
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.state.GameRunner;
import com.tcoffman.ttwb.web.GameModelFileRepository;
import com.tcoffman.ttwb.web.GameStateFileRepository;
import com.tcoffman.ttwb.web.GameStateFileRepository.Bundle;
import com.tcoffman.ttwb.web.resource.AbstractRootResource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@Api("States")
@SwaggerDefinition(info = @Info(description = "Access to view and update game states", version = "1.0.0", title = "Game States API"))
@Path("/states")
public class StatesResource extends AbstractRootResource {

	public StatesResource() {
		super();
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return uriBuilder.path(StatesResource.class).path(StatesResource.class, "getState");
	}

	@ApiOperation("List of all states")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<URI> getStates() {
		return m_stateRepository.stateIds().map(pathTo(baseUriBuilder())::build).collect(Collectors.toList());
	}

	// "sub-resource locator" (no http-method // annotations)
	@Path("/{stateId}")
	public StateResource getState(@PathParam("stateId") String stateId) throws GameComponentBuilderException, XMLStreamException {
		return createStateResource(getStateBundle(stateId));
	}

	private Bundle getStateBundle(String stateId) throws GameComponentBuilderException {
		return m_stateRepository.getBundle(stateId, this::getModelProvider);
	}

	public static class StateCreationForm {
		private String m_model;

		public String getModel() {
			return m_model;
		}

		public void setModel(String model) {
			m_model = model;
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public StateResource createState(StateCreationForm stateCreationForm) throws JSONException, XMLStreamException, PluginException {
		String modelId;
		try {
			final String path = new URL(stateCreationForm.getModel()).getPath();
			final int pos = path.indexOf("models/");
			modelId = path.substring(pos + "models/".length());
		} catch (final MalformedURLException ex) {
			modelId = stateCreationForm.getModel();
		}
		final GameModelFileRepository.Bundle modelBundle = m_modelRepository.getBundle(modelId);
		final GameStateFileRepository.Bundle stateBundle = m_stateRepository.create(modelBundle.getModel(), modelBundle.getModelId(),
				modelBundle.getPluginSet(), modelBundle.getModelRefResolver());

		final GameRunner runner = new GameRunner(stateBundle.getState());

		if (!runner.autoRun().isEmpty())
			stateBundle.store(this::getModelProvider);

		return createStateResource(stateBundle);
	}

	private StateResource createStateResource(final GameStateFileRepository.Bundle stateBundle) {
		return subresource(new StateResource(stateBundle));
	}

}
