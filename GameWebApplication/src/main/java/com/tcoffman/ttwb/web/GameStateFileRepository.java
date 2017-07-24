package com.tcoffman.ttwb.web;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.persistence.GameStateBundle;
import com.tcoffman.ttwb.component.persistence.GameStateRepository;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.plugin.PluginSet;
import com.tcoffman.ttwb.state.DefaultAuthorizationManager;
import com.tcoffman.ttwb.state.GameAuthorizationManager;
import com.tcoffman.ttwb.state.GameState;
import com.tcoffman.ttwb.state.StandardGameState;
import com.tcoffman.ttwb.state.persistence.StandardStateRefManager;
import com.tcoffman.ttwb.state.persistence.StateRefManager;
import com.tcoffman.ttwb.state.persistence.StateRefResolver;
import com.tcoffman.ttwb.state.persistence.xml.StandardGameStateParser;
import com.tcoffman.ttwb.state.persistence.xml.StandardGameStateParser.ModelProvider;

public class GameStateFileRepository implements GameStateRepository {

	private final Path m_base;
	// private final DefaultPluginFactory m_pluginFactory;
	private final GameAuthorizationManager m_authMgr;
	private final Map<String, Reference<Bundle>> m_cache = new HashMap<>();

	public GameStateFileRepository() {
		m_base = Paths.get("/Users/coffman/Development/tabletop-workbench/GameWebApplication/repo/state");

		// m_pluginFactory = new DefaultPluginFactory();
		// m_pluginFactory.install(CORE, com.tcoffman.ttwb.core.Core.class);
		// m_pluginFactory.install(GRID, com.tcoffman.ttwb.core.Grid.class);

		m_authMgr = new DefaultAuthorizationManager();
	}

	public Stream<? extends String> stateIds() {
		try {
			return Files.list(m_base).filter(Files::isRegularFile).map(Path::getFileName).map(Path::toString).filter((s) -> s.endsWith("-state.xml"))
					.map((s) -> s.replaceAll("-state.xml$", ""));
		} catch (final IOException ex) {
			ex.printStackTrace();
			return Stream.empty();
		} finally {
		}
	}

	public interface Bundle extends GameStateBundle {
		StateRefManager getStateRefManager();

		void store(Function<String, ModelProvider> modelProviderFactory) throws GameComponentBuilderException;

		void remove() throws GameComponentBuilderException;
	}

	private static final String FORMAT_STATE_ID = "state-%1$d";

	private String generateNewId() {
		final List<? extends String> stateIds = stateIds().collect(Collectors.toList());
		int index = 0;
		String stateId;
		do
			stateId = String.format(FORMAT_STATE_ID, ++index);
		while (stateIds.contains(stateId));
		return stateId;
	}

	private InputStream getResourceAsStream(String stateId) throws GameComponentBuilderException {
		try {
			final Path path = m_base.resolve(stateId);
			return new FileInputStream(path.toFile());
		} catch (final FileNotFoundException ex) {
			throw new GameComponentBuilderException(CORE, ex);
		}
	}

	private OutputStream openResourceAsStream(String stateId) throws GameComponentBuilderException {
		try {
			final Path path = m_base.resolve(stateId);
			final Path bufferFile = Files.createTempFile(path.getFileName().toString(), "-tmp");

			final FileOutputStream os = new FileOutputStream(bufferFile.toFile());
			return new OutputStream() {

				@Override
				public void write(int b) throws IOException {
					os.write(b);

				}

				@Override
				public void write(byte[] b) throws IOException {
					os.write(b);
				}

				@Override
				public void write(byte[] b, int off, int len) throws IOException {
					os.write(b, off, len);
				}

				@Override
				public void flush() throws IOException {
					os.flush();
				}

				@Override
				public void close() throws IOException {
					try {
						os.close();
					} finally {
						Files.move(bufferFile, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
					}
				}

			};
		} catch (final IOException ex) {
			throw new GameComponentBuilderException(CORE, ex);
		}
	}

	private void deleteResource(String stateId) throws GameComponentBuilderException {
		try {
			final Path path = m_base.resolve(stateId);
			Files.delete(path);
		} catch (final IOException ex) {
			throw new GameComponentBuilderException(CORE, ex);
		}
	}

	public Bundle create(GameModel model, String modelId, PluginSet pluginSet, ModelRefResolver modelRefResolver) throws GameComponentBuilderException {
		final StandardGameStateParser parser = new StandardGameStateParser(m_authMgr, (id) -> new StandardGameStateParser.ModelProvider() {

			@Override
			public GameModel getModel() {
				return model;
			}

			@Override
			public PluginSet getPluginSet() {
				return pluginSet;
			}

			@Override
			public ModelRefResolver getModelRefResolver() {
				return modelRefResolver;
			}

		});

		final StandardGameState state = new StandardGameState(model, pluginSet);
		final String stateId = generateNewId();
		try (OutputStream os = openResourceAsStream(stateId + "-state.xml")) {
			parser.write(state, os, modelId);
		} catch (final IOException ex) {
			throw new GameComponentBuilderException(CORE, ex);
		} catch (final TransformerException ex) {
			throw new GameComponentBuilderException(CORE, ex);
		} catch (final XMLStreamException ex) {
			throw new GameComponentBuilderException(CORE, ex);
		}
		final StateRefManager stateRefManager = new StandardStateRefManager(pluginSet);

		final Bundle bundle = new BundleImpl(stateId, state, modelId, stateRefManager, pluginSet);
		return bundle;
	}

	private static class ModelProviderFactoryListener {
		private final Function<String, StandardGameStateParser.ModelProvider> m_modelProviderFactory;
		private String m_modelId = null;
		private ModelProvider m_modelProvider;

		public ModelProviderFactoryListener(Function<String, StandardGameStateParser.ModelProvider> modelProviderFactory) {
			m_modelProviderFactory = modelProviderFactory;
		}

		public String getModelId() {
			return m_modelId;
		}

		public ModelProvider getModelProvider() {
			return m_modelProvider;
		}

		public StandardGameStateParser.ModelProvider intercept(String modelId) {
			m_modelId = modelId;
			m_modelProvider = m_modelProviderFactory.apply(modelId);
			return m_modelProvider;
		}
	}

	@Override
	public Bundle getBundle(String stateId, Function<String, StandardGameStateParser.ModelProvider> modelProviderFactory) throws GameComponentBuilderException {
		final Reference<Bundle> bundleRef = m_cache.get(stateId);
		if (null != bundleRef && null != bundleRef.get())
			return bundleRef.get();

		final ModelProviderFactoryListener listener = new ModelProviderFactoryListener(modelProviderFactory);
		final StandardGameStateParser stateParser = new StandardGameStateParser(m_authMgr, listener::intercept);
		Bundle bundle;
		try {
			final GameState state = stateParser.parse(getResourceAsStream(stateId + "-state.xml"), stateId);
			final StateRefManager stateRefManager = stateParser.createManager(stateId);
			bundle = new BundleImpl(stateId, state, listener.getModelId(), stateRefManager, listener.getModelProvider().getPluginSet());
		} catch (final XMLStreamException ex) {
			throw new GameComponentBuilderException(CORE, ex);
		}
		m_cache.put(stateId, new SoftReference<>(bundle));
		return bundle;
	}

	public void storeBundle(Bundle bundle, Function<String, StandardGameStateParser.ModelProvider> modelProviderFactory) throws GameComponentBuilderException {
		final StandardGameStateParser parser = new StandardGameStateParser(m_authMgr, modelProviderFactory);
		parser.registerManager(bundle.getStateId(), bundle.getStateRefManager());
		try (OutputStream os = openResourceAsStream(bundle.getStateId() + "-state.xml")) {
			parser.write(bundle.getState(), os, bundle.getModelId());
			m_cache.put(bundle.getStateId(), new SoftReference<>(bundle));
		} catch (final IOException ex) {
			throw new GameComponentBuilderException(CORE, ex);
		} catch (final TransformerException ex) {
			throw new GameComponentBuilderException(CORE, ex);
		} catch (final XMLStreamException ex) {
			throw new GameComponentBuilderException(CORE, ex);
		}
	}

	public void removeBundle(Bundle bundle) throws GameComponentBuilderException {
		deleteResource(bundle.getStateId() + "-state.xml");
	}

	private class BundleImpl implements Bundle {
		private final String m_id;
		private final String m_modelId;
		private final GameState m_state;
		private final StateRefManager m_stateRefManager;
		private final PluginSet m_pluginSet;

		public BundleImpl(String id, GameState state, String modelId, StateRefManager stateRefManager, PluginSet pluginSet) {
			m_id = id;
			m_state = state;
			m_modelId = modelId;
			m_pluginSet = pluginSet;
			m_stateRefManager = stateRefManager;
		}

		@Override
		public String getStateId() {
			return m_id;
		}

		@Override
		public GameState getState() {
			return m_state;
		}

		@Override
		public PluginSet getPluginSet() {
			return m_pluginSet;
		}

		@Override
		public StateRefResolver getStateRefResolver() {
			return m_stateRefManager;
		}

		@Override
		public StateRefManager getStateRefManager() {
			return m_stateRefManager;
		}

		@Override
		public String getModelId() {
			return m_modelId;
		}

		@Override
		public void store(Function<String, ModelProvider> modelProviderFactory) throws GameComponentBuilderException {
			storeBundle(this, modelProviderFactory);
		}

		@Override
		public void remove() throws GameComponentBuilderException {
			removeBundle(this);
		}

	}

}
