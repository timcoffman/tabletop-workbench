package com.tcoffman.ttwb.component.persistence.xml;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;
import static com.tcoffman.ttwb.plugin.CorePlugins.GRID;

import java.io.InputStream;
import java.util.Optional;

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

public final class BundleHelper {

	public static InputStream getResourceAsStream(String name) {
		return BundleHelper.class.getResourceAsStream(name);
	}

	private static final BundleHelper INSTANCE = new BundleHelper();

	public static BundleHelper instance() {
		return INSTANCE;
	}

	private final DefaultPluginFactory m_pluginFactory;
	private final StandardGameDocumentationParser m_documentationParser;

	private BundleHelper() {
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
			} catch (GameComponentBuilderException | XMLStreamException ex) {
				ex.printStackTrace();
				return Optional.empty();
			}
		}

		@Override
		public Optional<String> lookupId(GameModel component) {
			return Optional.empty();
		}

	}

	public PluginFactory getPluginFactory() {
		return m_pluginFactory;
	}

	public Bundle getBundle(String name) throws GameComponentBuilderException, XMLStreamException {
		return getBundle(name, "en-us");
	}

	public Bundle getBundle(String name, String documentationLang) throws GameComponentBuilderException, XMLStreamException {
		return new BundleImpl(name, documentationLang);
	}

	public interface Bundle {

		PluginSet getPluginSet();

		StandardModelDocumentation getDocumentation();

		DocumentationRefResolver getDocumentationResolver();

		ModelRefResolver getModelResolver();

		GameModel getModel();

	}

	private class BundleImpl implements Bundle {
		private final String m_name;
		private final String m_documentationLang;
		private final PluginSet m_pluginSet;
		private final StandardModelDocumentation m_documentation;
		private final DocumentationRefResolver m_documentationRefResolver;
		private final GameModel m_model;
		private final ModelRefResolver m_modelRefResolver;

		public BundleImpl(String name, String documentationLang) throws GameComponentBuilderException, XMLStreamException {
			m_name = name;
			m_documentationLang = documentationLang;
			m_pluginSet = new PluginSet(m_pluginFactory);
			m_documentation = m_documentationParser.parse(getResourceAsStream(m_name + "-model/lang/" + m_documentationLang + "/doc.xml"), m_name);
			m_documentationRefResolver = m_documentationParser.createResolver(m_name);
			final StandardGameModelParser modelParser = new StandardGameModelParser(m_pluginSet, new ImportedModelRefResolver(documentationLang),
					m_documentationRefResolver);
			m_model = modelParser.parse(getResourceAsStream(m_name + "-model/model.xml"), m_name);
			m_modelRefResolver = modelParser.createResolver(m_name);
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
		public ModelRefResolver getModelResolver() {
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
