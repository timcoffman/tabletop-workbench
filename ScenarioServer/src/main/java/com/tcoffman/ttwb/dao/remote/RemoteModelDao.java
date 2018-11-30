package com.tcoffman.ttwb.dao.remote;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;

import com.tcoffman.ttwb.dao.ModelDao;
import com.tcoffman.ttwb.dao.data.Model;
import com.tcoffman.ttwb.service.ApiException;
import com.tcoffman.ttwb.service.api.ModelsApi;
import com.tcoffman.ttwb.service.api.data.ModelResource;

public class RemoteModelDao implements ModelDao {

	private final ModelsApi m_modelsApi = new ModelsApi();

	private static String getIdFromUrl(String modelUrl) {
		try {
			final String path = new URL(modelUrl).getPath();
			final int pos = path.indexOf("models/");
			if (pos < 0)
				throw new IllegalArgumentException("cannot parse model url \"" + modelUrl + "\"");
			return path.substring(pos + "models/".length());
		} catch (final MalformedURLException ex) {
			throw new IllegalArgumentException("cannot parse model url \"" + modelUrl + "\"", ex);
		}
	}

	@Override
	public Collection<Model> all() {
		try {
			return m_modelsApi.getModels().stream().map(this::makeModel).collect(toList());
		} catch (final ApiException ex) {
			return emptyList();
		}
	}

	@Override
	public Optional<Model> lookup(String modelUrl) {
		try {
			final ModelResource remoteModel = m_modelsApi.getModel(getIdFromUrl(modelUrl));
			final Model model = new Model();
			model.setModelUrl(remoteModel.getResource());
			return Optional.of(model);
		} catch (final ApiException ex) {
			return Optional.empty();
		}
	}

	private Model makeModel(String modelUrl) {
		final Model model = new Model();
		model.setModelUrl(modelUrl);
		return model;
	}
}
