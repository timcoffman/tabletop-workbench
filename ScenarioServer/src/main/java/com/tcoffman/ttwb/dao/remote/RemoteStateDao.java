package com.tcoffman.ttwb.dao.remote;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import com.tcoffman.ttwb.dao.StateDao;
import com.tcoffman.ttwb.dao.data.Role;
import com.tcoffman.ttwb.dao.data.State;
import com.tcoffman.ttwb.service.ApiException;
import com.tcoffman.ttwb.service.api.StatesApi;
import com.tcoffman.ttwb.service.api.data.StateCreationForm;
import com.tcoffman.ttwb.service.api.data.StateResource;

public class RemoteStateDao implements StateDao {

	private final StatesApi m_statesApi = new StatesApi();

	private static String getIdFromUrl(String stateUrl) {
		try {
			final String path = new URL(stateUrl).getPath();
			final int pos = path.indexOf("states/");
			if (pos < 0)
				throw new IllegalArgumentException("cannot parse state url \"" + stateUrl + "\"");
			return path.substring(pos + "states/".length());
		} catch (final MalformedURLException ex) {
			throw new IllegalArgumentException("cannot parse state url \"" + stateUrl + "\"", ex);
		}
	}

	@Override
	public Optional<State> lookup(String stateUrl) {
		try {
			final StateResource remoteState = m_statesApi.getState(getIdFromUrl(stateUrl));
			final State state = new State();
			state.setStateUrl(remoteState.getResource());
			state.setModel(remoteState.getModel());
			state.setRemoteResource(remoteState);
			return Optional.of(state);
		} catch (final ApiException ex) {
			return Optional.empty();
		}
	}

	@Override
	public State create(String model) {
		try {
			final StateCreationForm stateCreationForm = new StateCreationForm();
			stateCreationForm.setModel(model);
			final StateResource remoteState = m_statesApi.createState(stateCreationForm);
			final State state = new State();
			state.setStateUrl(remoteState.getResource());
			state.setModel(remoteState.getModel());
			return state;
		} catch (final ApiException ex) {
			throw new IllegalArgumentException("could not create state", ex);
		}
	}

	@Override
	public void bindParticipant(State state, Role role, String authorizationForRole) {
		/* nothing to do here yet */
	}

}
