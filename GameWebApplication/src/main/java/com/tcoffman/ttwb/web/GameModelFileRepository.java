package com.tcoffman.ttwb.web;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;
import static com.tcoffman.ttwb.plugin.CorePlugins.GRID;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.xml.stream.XMLStreamException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.persistence.GameModelRepository;
import com.tcoffman.ttwb.component.persistence.GameRootModelRepository;
import com.tcoffman.ttwb.doc.GameModelDocumentation;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefResolver;
import com.tcoffman.ttwb.doc.persistence.xml.StandardGameDocumentationParser;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.model.persistance.xml.StandardGameModelParser;
import com.tcoffman.ttwb.plugin.DefaultPluginFactory;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.plugin.PluginSet;

public class GameModelFileRepository implements GameModelRepository {

	private final Path m_base;
	private final DefaultPluginFactory m_pluginFactory;
	private final StandardGameDocumentationParser m_documentationParser;
	private final Map<String, Reference<GameModelRepository.Bundle>> m_cache = new HashMap<>();
	private final Map<String, Supplier<InputStream>> m_streamSources = new HashMap<>();

	private final GameRootModelRepository m_rootRepo;

	public GameModelFileRepository() {
		m_base = Paths.get("/Users/coffman/Development/tabletop-workbench/GameWebApplication/repo/model");

		m_pluginFactory = new DefaultPluginFactory();
		m_pluginFactory.install(CORE, com.tcoffman.ttwb.core.Core.class);
		m_pluginFactory.install(GRID, com.tcoffman.ttwb.core.Grid.class);

		m_documentationParser = new StandardGameDocumentationParser();

		m_rootRepo = new GameRootModelRepository(m_pluginFactory);

	}

	public Stream<? extends String> modelIds() {
		try {
			return Files.list(m_base).filter(Files::isDirectory).map(Path::getFileName).map(Path::toString).filter((s) -> s.endsWith("-model"))
					.map((s) -> s.replaceAll("-model$", ""));
		} catch (final IOException ex) {
			ex.printStackTrace();
			return Stream.empty();
		} finally {
		}
	}

	public PluginFactory getPluginFactory() {
		return m_pluginFactory;
	}

	@Override
	public Bundle getBundle(String name) throws GameComponentBuilderException {
		return getBundle(name, "en-us");
	}

	public void installModel(String id, Supplier<InputStream> streamSource) {
		final String key = "model" + ":" + id;
		m_streamSources.put(key, streamSource);
	}

	public void installDocumentation(String id, String documentationLang, Supplier<InputStream> streamSource) {
		final String key = "doc" + ":" + id + ":" + documentationLang;
		m_streamSources.put(key, streamSource);

	}

	@Override
	public GameModelRepository.Bundle getBundle(String name, String documentationLang) throws GameComponentBuilderException {
		final String cacheKey = name + ":" + documentationLang;
		final Reference<Bundle> bundleRef = m_cache.get(cacheKey);
		if (null != bundleRef && null != bundleRef.get())
			return bundleRef.get();

		Bundle bundle;
		try {
			if ("root".equals(name))
				bundle = m_rootRepo.getBundle(name, documentationLang);
			else
				bundle = new BundleImpl(name, documentationLang);
		} catch (final XMLStreamException ex) {
			throw new GameComponentBuilderException(CORE, ex);
		}

		m_cache.put(cacheKey, new SoftReference<>(bundle));
		return bundle;
	}

	@Override
	public GameModelRepository.Bundle getBundle(GameModel model) throws GameComponentBuilderException {
		return m_cache.entrySet().stream().filter((e) -> e.getValue().get() != null).filter((e) -> e.getValue().get().getModel() == model)
				.map(Map.Entry::getValue).map(Reference::get).findAny().orElseThrow(() -> new GameComponentBuilderException(CORE, "unknown model"));
	}

	private InputStream getDocumentationResourcesAsStream(String modelId, String documentationLang) throws GameComponentBuilderException {
		final String key = "doc" + ":" + modelId + ":" + documentationLang;
		final Supplier<InputStream> streamSource = m_streamSources.get(key);
		if (null != streamSource)
			return streamSource.get();
		return getResourceAsStream(modelId + "-model/lang/" + documentationLang + "/doc.xml");
	}

	private InputStream getModelResourcesAsStream(String modelId) throws GameComponentBuilderException {
		final String key = "model" + ":" + modelId;
		final Supplier<InputStream> streamSource = m_streamSources.get(key);
		if (null != streamSource)
			return streamSource.get();
		return getResourceAsStream(modelId + "-model/model.xml");
	}

	private InputStream getResourceAsStream(String modelId) throws GameComponentBuilderException {
		try {
			final Path path = m_base.resolve(modelId);
			return new FileInputStream(path.toFile());
		} catch (final FileNotFoundException ex) {
			throw new GameComponentBuilderException(CORE, ex);
		}
	}

	private class BundleImpl implements Bundle {
		private final String m_id;
		private final String m_documentationLang;
		private final GameModelDocumentation m_documentation;
		private final DocumentationRefResolver m_documentationRefResolver;
		private final GameModel m_model;
		private final ModelRefResolver m_modelRefResolver;
		private final PluginSet m_pluginSet;

		public BundleImpl(String id, String documentationLang) throws GameComponentBuilderException, XMLStreamException {
			m_id = id;
			m_documentationLang = documentationLang;
			m_pluginSet = new PluginSet(m_rootRepo.getBundle("root").getPluginSet(), m_pluginFactory);
			try (InputStream s = getDocumentationResourcesAsStream(m_id, m_documentationLang)) {
				m_documentation = m_documentationParser.parse(s, m_id);
				m_documentationRefResolver = m_documentationParser.createResolver(m_id);
			} catch (final IOException ex) {
				throw new RuntimeException(ex);
			}

			final StandardGameModelParser modelParser = new StandardGameModelParser(m_pluginSet, GameModelFileRepository.this, m_documentationRefResolver);
			try (InputStream s = getModelResourcesAsStream(m_id)) {
				m_model = modelParser.parse(s, m_id);
				m_modelRefResolver = modelParser.createResolver(m_id);
			} catch (final IOException ex) {
				throw new RuntimeException(ex);
			}
		}

		@Override
		public String getModelId() {
			return m_id;
		}

		@Override
		public GameModelDocumentation getDocumentation() {
			return m_documentation;
		}

		@Override
		public GameModel getModel() {
			return m_model;
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
