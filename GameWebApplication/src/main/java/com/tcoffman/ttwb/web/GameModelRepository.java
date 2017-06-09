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
import java.util.Optional;
import java.util.stream.Stream;

import javax.xml.stream.XMLStreamException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.GameComponentRefResolver;
import com.tcoffman.ttwb.doc.StandardModelDocumentation;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefResolver;
import com.tcoffman.ttwb.doc.persistence.xml.StandardGameDocumentationParser;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.model.persistance.xml.StandardGameModelParser;
import com.tcoffman.ttwb.plugin.DefaultPluginFactory;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.plugin.PluginSet;

public class GameModelRepository {

	private final Path m_base;
	private final DefaultPluginFactory m_pluginFactory;
	private final StandardGameDocumentationParser m_documentationParser;
	private final Map<String, Reference<Bundle>> m_cache = new HashMap<String, Reference<Bundle>>();

	public GameModelRepository() {
		m_base = Paths.get("/Users/coffman/Development/tabletop-workbench/GameWebApplication/repo/model");

		m_pluginFactory = new DefaultPluginFactory();
		m_pluginFactory.install(CORE, com.tcoffman.ttwb.core.Core.class);
		m_pluginFactory.install(GRID, com.tcoffman.ttwb.core.Grid.class);

		m_documentationParser = new StandardGameDocumentationParser();
	}

	private class ImportedModelRefResolver implements GameComponentRefResolver<GameModel> {

		private final String m_documentationLang;

		public ImportedModelRefResolver(String documentationLang) {
			m_documentationLang = documentationLang;
		}

		@Override
		public Optional<GameComponentRef<GameModel>> lookup(String id) {
			try {
				return Optional.of(getBundle(id, m_documentationLang)).map(Bundle::getModel).map(GameComponentRef::wrap);
			} catch (final GameComponentBuilderException ex) {
				ex.printStackTrace();
				return Optional.empty();
			}
		}

		@Override
		public Optional<String> lookupId(GameModel importedModel) {
			return m_cache.entrySet().stream().filter((e) -> e.getValue() == importedModel).map(Map.Entry::getKey).findAny();
		}

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

	public Bundle getBundle(String name) throws GameComponentBuilderException {
		return getBundle(name, "en-us");
	}

	public Bundle getBundle(String name, String documentationLang) throws GameComponentBuilderException {
		final String cacheKey = name + ":" + documentationLang;
		final Reference<Bundle> bundleRef = m_cache.get(cacheKey);
		if (null != bundleRef && null != bundleRef.get())
			return bundleRef.get();

		BundleImpl bundle;
		try {
			bundle = new BundleImpl(name, documentationLang);
		} catch (final XMLStreamException ex) {
			throw new GameComponentBuilderException(CORE, ex);
		}

		m_cache.put(cacheKey, new SoftReference<Bundle>(bundle));
		return bundle;
	}

	public interface Bundle {

		PluginSet getPluginSet();

		String getModelId();

		StandardModelDocumentation getDocumentation();

		DocumentationRefResolver getDocumentationResolver();

		ModelRefResolver getModelRefResolver();

		GameModel getModel();

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
		private final StandardModelDocumentation m_documentation;
		private final DocumentationRefResolver m_documentationRefResolver;
		private final GameModel m_model;
		private final ModelRefResolver m_modelRefResolver;
		private final PluginSet m_pluginSet;

		public BundleImpl(String id, String documentationLang) throws GameComponentBuilderException, XMLStreamException {
			m_id = id;
			m_documentationLang = documentationLang;
			m_pluginSet = new PluginSet(m_pluginFactory);
			m_documentation = m_documentationParser.parse(getResourceAsStream(m_id + "-model/lang/" + m_documentationLang + "/doc.xml"), m_id);
			m_documentationRefResolver = m_documentationParser.createResolver(m_id);
			final StandardGameModelParser modelParser = new StandardGameModelParser(m_pluginSet, new ImportedModelRefResolver(m_documentationLang),
					m_documentationRefResolver);
			m_model = modelParser.parse(getResourceAsStream(m_id + "-model/model.xml"), m_id);
			m_modelRefResolver = modelParser.createResolver(m_id);
		}

		@Override
		public String getModelId() {
			return m_id;
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
