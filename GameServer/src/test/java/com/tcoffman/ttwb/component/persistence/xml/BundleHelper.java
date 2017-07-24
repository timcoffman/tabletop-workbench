package com.tcoffman.ttwb.component.persistence.xml;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;
import static com.tcoffman.ttwb.plugin.CorePlugins.GRID;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.GameModelRepository;
import com.tcoffman.ttwb.component.persistence.RespositoryBasedModelProvider;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.doc.GameModelDocumentation;
import com.tcoffman.ttwb.doc.StandardComponentDocumentation;
import com.tcoffman.ttwb.doc.StandardModelDocumentation;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefManager;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefResolver;
import com.tcoffman.ttwb.doc.persistence.StandardDocumentationRefManager;
import com.tcoffman.ttwb.doc.persistence.xml.StandardGameDocumentationParser;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.StandardRootGameModel;
import com.tcoffman.ttwb.model.persistance.ModelRefManager;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.model.persistance.StandardModelRefManager;
import com.tcoffman.ttwb.model.persistance.xml.StandardGameModelParser;
import com.tcoffman.ttwb.plugin.DefaultPluginFactory;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.plugin.PluginSet;
import com.tcoffman.ttwb.state.persistence.xml.StandardGameStateParser.ModelProvider;

public final class BundleHelper implements GameModelRepository {

	public static InputStream getResourceAsStream(String name) {
		return BundleHelper.class.getResourceAsStream(name);
	}

	private static final BundleHelper INSTANCE = new BundleHelper();

	public static BundleHelper instance() {
		return INSTANCE;
	}

	private final DefaultPluginFactory m_pluginFactory;
	private final PluginSet m_pluginSet;
	private final StandardGameDocumentationParser m_documentationParser;
	private final Bundle m_rootBundle;
	private final Map<String, Bundle> m_cache = new HashMap<>();

	private BundleHelper() {
		m_pluginFactory = new DefaultPluginFactory();
		m_pluginFactory.install(CORE, com.tcoffman.ttwb.core.Core.class);
		m_pluginFactory.install(GRID, com.tcoffman.ttwb.core.Grid.class);

		m_pluginSet = new PluginSet(m_pluginFactory);

		m_documentationParser = new StandardGameDocumentationParser();

		try {
			final GameComponentDocumentation rootDoc = StandardComponentDocumentation.create().setName(GameComponentDocumentation.Format.SHORT, "root").done();
			final StandardRootGameModel rootModel = StandardRootGameModel.create().setCorePlugin(m_pluginSet.requirePlugin(CORE))
					.setDocumentation(GameComponentRef.wrap(rootDoc)).done();

			final DocumentationRefManager rootDocRefManager = new StandardDocumentationRefManager();
			final ModelRefManager rootModelRefManager = new StandardModelRefManager(m_pluginSet, null);

			rootModelRefManager.getPartPrototypeManager().register(rootModel.effectiveRootPrototype().get(), "root");
			rootModelRefManager.getRoleManager().register(rootModel.effectiveSystemRole(), "system");

			rootDocRefManager.getPrototypeManager().register(rootModel.effectiveRootPrototype().get().getDocumentation(), "root");
			rootDocRefManager.getRoleManager().register(rootModel.effectiveSystemRole().getDocumentation(), "system");

			m_rootBundle = new GameModelRepository.Bundle() {

				@Override
				public PluginSet getPluginSet() {
					return m_pluginSet;
				}

				@Override
				public String getModelId() {
					return "root";
				}

				@Override
				public GameModelDocumentation getDocumentation() {
					throw new UnsupportedOperationException("not yet implemented");
				}

				@Override
				public DocumentationRefResolver getDocumentationResolver() {
					return rootDocRefManager;
				}

				@Override
				public ModelRefResolver getModelRefResolver() {
					return rootModelRefManager;
				}

				@Override
				public GameModel getModel() {
					return rootModel;
				}

			};

			m_cache.put(m_rootBundle.getModelId(), m_rootBundle);

		} catch (final PluginException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public ModelProvider createModelProvider(String modelId, String documentationLanguage) {
		return new RespositoryBasedModelProvider(modelId, () -> this);
	}

	@Override
	public GameModelRepository.Bundle getBundle(GameModel model) throws GameComponentBuilderException {
		return m_cache.values().stream().filter((b) -> model == b.getModel()).findAny()
				.orElseThrow(() -> new GameComponentBuilderException(CORE, "missing bundle"));
	}

	public PluginFactory getPluginFactory() {
		return m_pluginFactory;
	}

	@Override
	public Bundle getBundle(String name) throws GameComponentBuilderException {
		return getBundle(name, "en-us");
	}

	@Override
	public Bundle getBundle(String name, String documentationLang) throws GameComponentBuilderException {
		Bundle bundle = m_cache.get(name);
		if (null == bundle) {
			bundle = new BundleImpl(name, documentationLang);
			m_cache.put(bundle.getModelId(), bundle);
		}
		return bundle;
	}

	private class BundleImpl implements Bundle {
		private final String m_name;
		private final String m_documentationLang;
		private final StandardModelDocumentation m_documentation;
		private final DocumentationRefResolver m_documentationRefResolver;
		private final GameModel m_model;
		private final ModelRefResolver m_modelRefResolver;

		public BundleImpl(String name, String documentationLang) throws GameComponentBuilderException {
			m_name = name;
			m_documentationLang = documentationLang;
			try {
				m_documentation = m_documentationParser.parse(getResourceAsStream(m_name + "-model/lang/" + m_documentationLang + "/doc.xml"), m_name);
				m_documentationRefResolver = m_documentationParser.createResolver(m_name);

				final StandardGameModelParser modelParser = new StandardGameModelParser(m_pluginSet, BundleHelper.this, m_documentationRefResolver);
				m_model = modelParser.parse(getResourceAsStream(m_name + "-model/model.xml"), m_name);
				m_modelRefResolver = modelParser.createResolver(m_name);
			} catch (final XMLStreamException ex) {
				ex.printStackTrace();
				throw new GameComponentBuilderException(CORE, ex);
			}
		}

		@Override
		public StandardModelDocumentation getDocumentation() {
			return m_documentation;
		}

		@Override
		public GameModel getModel() {
			return m_model;
		}

		@Override
		public String getModelId() {
			return m_name;
		}

		@Override
		public ModelRefResolver getModelRefResolver() {
			return m_modelRefResolver;
		}

		@Override
		public DocumentationRefResolver getDocumentationResolver() {
			return m_documentationRefResolver;
		}

		@Override
		public PluginSet getPluginSet() {
			return m_pluginSet;
		}
	}

}
