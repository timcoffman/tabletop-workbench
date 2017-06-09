package com.tcoffman.ttwb.web.resource.state;

import java.beans.IntrospectionException;
import java.net.URI;
import java.util.List;
import java.util.Map;
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
import com.tcoffman.ttwb.web.GameModelRepository;
import com.tcoffman.ttwb.web.GameStateRepository;
import com.tcoffman.ttwb.web.GameStateRepository.Bundle;
import com.tcoffman.ttwb.web.resource.AbstractRootResource;

@Path("/states")
public class StatesResource extends AbstractRootResource {

	public StatesResource() {
		super();
	}

	public static UriBuilder pathTo(UriBuilder uriBuilder) {
		return uriBuilder.path(StatesResource.class).path(StatesResource.class, "getState");
	}

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

	public Map<String, Object> getCreate() throws IntrospectionException {
		return beanWritablePropertyMap(StateCreationForm.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public StateResource createState(StateCreationForm stateCreationForm) throws JSONException, XMLStreamException, PluginException {
		final String modelId = stateCreationForm.getModel();
		final GameModelRepository.Bundle modelBundle = m_modelRepository.getBundle(modelId);
		final GameStateRepository.Bundle stateBundle = m_stateRepository.create(modelBundle.getModel(), modelBundle.getModelId(), modelBundle.getPluginSet(),
				modelBundle.getModelRefResolver());

		final GameRunner runner = new GameRunner(stateBundle.getState());
		runner.autoAdvance();

		return createStateResource(stateBundle);
	}

	private StateResource createStateResource(final GameStateRepository.Bundle stateBundle) {
		return subresource(new StateResource(stateBundle));
	}

}
