package com.tcoffman.ttwb.web.resource.state;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import javax.ws.rs.container.ResourceContext;
import javax.xml.stream.XMLStreamException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.state.GameRunner;
import com.tcoffman.ttwb.web.GameModelRepository;
import com.tcoffman.ttwb.web.GameStateRepository;
import com.tcoffman.ttwb.web.resource.state.StatesResource.StateCreationForm;

public class StatesResourceTest {

	private ResourceContext m_resourceContext;
	private GameModelRepository m_modelRepository;
	private GameStateRepository m_stateRepository;

	@Before
	public void configureContext() {
		m_resourceContext = null;
	}

	@Before
	public void configureModelRepository() {
		m_modelRepository = new GameModelRepository();
	}

	@Before
	public void configureStateRepository() {
		m_stateRepository = new GameStateRepository();
	}

	@Test
	public void test() throws JSONException, XMLStreamException, PluginException {
		final StatesResource resource = new StatesResource();
		final StateCreationForm stateCreationForm = new StatesResource.StateCreationForm();
		stateCreationForm.setModel("complete");
		final StateResource result = resource.createState(stateCreationForm);
		assertThat(result.stateBundle().getModelId(), equalTo("complete"));
	}

	@Test
	public void test2() throws JSONException, XMLStreamException, PluginException {
		final GameModelRepository.Bundle modelBundle = m_modelRepository.getBundle("complete");
		final GameStateRepository.Bundle stateBundle = m_stateRepository.create(modelBundle.getModel(), modelBundle.getModelId(), modelBundle.getPluginSet(),
				modelBundle.getModelRefResolver());

		final GameRunner runner = new GameRunner(stateBundle.getState());
		runner.autoAdvance();
	}
}
