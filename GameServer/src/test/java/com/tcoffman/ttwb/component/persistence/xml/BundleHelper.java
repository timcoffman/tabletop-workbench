package com.tcoffman.ttwb.component.persistence.xml;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;
import static com.tcoffman.ttwb.plugin.CorePlugins.GRID;

import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.doc.StandardModelDocumentation;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefResolver;
import com.tcoffman.ttwb.doc.persistence.xml.StandardGameDocumentationParser;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.model.persistance.xml.StandardGameModelParser;
import com.tcoffman.ttwb.plugin.DefaultPluginFactory;
import com.tcoffman.ttwb.plugin.PluginFactory;

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

		PluginFactory getPluginFactory();

		StandardModelDocumentation getDocumentation();

		DocumentationRefResolver getDocumentationResolver();

		ModelRefResolver getModelResolver();

		GameModel getModel();

	}

	private class BundleImpl implements Bundle {
		private final String m_name;
		private final String m_documentationLang;
		private final StandardModelDocumentation m_documentation;
		private final DocumentationRefResolver m_documentationRefResolver;
		private final GameModel m_model;
		private final ModelRefResolver m_modelRefResolver;

		public BundleImpl(String name, String documentationLang) throws GameComponentBuilderException, XMLStreamException {
			m_name = name;
			m_documentationLang = documentationLang;
			m_documentation = m_documentationParser.parse(getResourceAsStream(m_name + "-model/lang/" + m_documentationLang + "/doc.xml"), m_name);
			m_documentationRefResolver = m_documentationParser.createResolver(m_name);
			final StandardGameModelParser modelParser = new StandardGameModelParser(m_pluginFactory, m_documentationRefResolver);
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
		public PluginFactory getPluginFactory() {
			return m_pluginFactory;
		}
	}

}
