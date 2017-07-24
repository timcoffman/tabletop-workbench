package com.tcoffman.ttwb.component.persistence;

import java.util.function.Supplier;

import javax.xml.stream.XMLStreamException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.plugin.PluginSet;
import com.tcoffman.ttwb.state.persistence.xml.StandardGameStateParser;

public class RespositoryBasedModelProvider implements StandardGameStateParser.ModelProvider {
	private final Supplier<GameModelRepository> m_modelRepositorySupplier;
	private final String m_modelId;
	private GameModelRepository.Bundle m_modelBundle = null;

	public RespositoryBasedModelProvider(String modelId, Supplier<GameModelRepository> modelRepositorySupplier) {
		m_modelRepositorySupplier = modelRepositorySupplier;
		m_modelId = modelId;
	}

	private GameModelRepository.Bundle getModelBundle() throws GameComponentBuilderException, XMLStreamException {
		if (null == m_modelBundle)
			m_modelBundle = m_modelRepositorySupplier.get().getBundle(m_modelId);
		return m_modelBundle;
	}

	@Override
	public PluginSet getPluginSet() throws GameComponentBuilderException, XMLStreamException {
		return getModelBundle().getPluginSet();
	}

	@Override
	public ModelRefResolver getModelRefResolver() throws GameComponentBuilderException, XMLStreamException {
		return getModelBundle().getModelRefResolver();
	}

	@Override
	public GameModel getModel() throws GameComponentBuilderException, XMLStreamException {
		return getModelBundle().getModel();
	}
}