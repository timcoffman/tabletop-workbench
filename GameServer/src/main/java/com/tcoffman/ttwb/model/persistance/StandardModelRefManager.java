package com.tcoffman.ttwb.model.persistance;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponent;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.GameComponentRefManager;
import com.tcoffman.ttwb.component.persistence.GameComponentRefResolver;
import com.tcoffman.ttwb.model.persistance.GameModelRepository;
import com.tcoffman.ttwb.model.persistance.GameModelRepository.Bundle;
import com.tcoffman.ttwb.component.persistence.StandardComponentRefManager;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.PluginSet;

public class StandardModelRefManager implements ModelRefManager {
	private final PluginSet m_pluginSet;
	private final StandardComponentRefManager<GameStage> m_stageRefManager = new StandardComponentRefManager<>("stage");
	private final StandardComponentRefManager<GamePartPrototype> m_prototypeRefManager = new StandardComponentRefManager<>("prototype");
	private final StandardComponentRefManager<GameRole> m_roleRefManager = new StandardComponentRefManager<>("role");
	private final GameComponentRefResolver<GameModel> m_importedModelRefResolver;
	private final GameModelRepository m_importedModelRepository;

	private final Set<ModelRefResolver> m_importedModelRefResolvers = new HashSet<>();

	public StandardModelRefManager(PluginSet pluginSet, GameModelRepository importedModelRepository) {
		m_pluginSet = pluginSet;
		m_importedModelRepository = importedModelRepository;
		m_importedModelRefResolver = new ImportedModelRefResolver("en-us");
	}

	private void stashBundle(Bundle bundle) {
		m_importedModelRefResolvers.add(bundle.getModelRefResolver());
	}

	private class ImportedModelRefResolver implements GameComponentRefResolver<GameModel> {

		private final String m_documentationLang;

		public ImportedModelRefResolver(String documentationLang) {
			m_documentationLang = documentationLang;
		}

		@Override
		public Stream<Map.Entry<String, GameModel>> references() {
			throw new UnsupportedOperationException("not yet implemented");
		}

		@Override
		public Optional<GameComponentRef<GameModel>> lookup(String id) {
			try {
				final Optional<Bundle> bundle = Optional.ofNullable(m_importedModelRepository.getBundle(id, m_documentationLang));
				bundle.ifPresent(StandardModelRefManager.this::stashBundle);
				return bundle.map(GameModelRepository.Bundle::getModel).map(GameComponentRef::wrap);
			} catch (final GameComponentBuilderException ex) {
				ex.printStackTrace();
				return Optional.empty();
			}
		}

		@Override
		public Optional<String> lookupId(GameModel importedModel) {
			try {
				return Optional.ofNullable(m_importedModelRepository.getBundle(importedModel)).map(GameModelRepository.Bundle::getModelId);
			} catch (final GameComponentBuilderException ex) {
				ex.printStackTrace();
				return Optional.empty();
			}
		}

	}

	private class DelegatingGameComponentRefResolver<T extends GameComponent> implements GameComponentRefResolver<T> {

		private final Supplier<Stream<GameComponentRefResolver<T>>> m_delegatesSupplier;

		public DelegatingGameComponentRefResolver(Supplier<Stream<GameComponentRefResolver<T>>> delegatesSupplier) {
			m_delegatesSupplier = delegatesSupplier;
		}

		@Override
		public Optional<GameComponentRef<T>> lookup(String id) {
			return m_delegatesSupplier.get().map((d) -> d.lookup(id)).filter(Optional::isPresent).map(Optional::get).findFirst();
		}

		@Override
		public Optional<String> lookupId(T component) {
			return m_delegatesSupplier.get().map((d) -> d.lookupId(component)).filter(Optional::isPresent).map(Optional::get).findFirst();
		}

		@Override
		public Stream<Entry<String, T>> references() {
			return m_delegatesSupplier.get().flatMap(GameComponentRefResolver::references);
		}

	}

	private <T extends GameComponent> DelegatingGameComponentRefResolver<T> delegateTo(GameComponentRefResolver<T> delegate,
			Function<ModelRefResolver, GameComponentRefResolver<T>> importedDelegatesSupplier) {
		return new DelegatingGameComponentRefResolver<>(
				() -> Stream.concat(Stream.of(delegate), m_importedModelRefResolvers.stream().map(importedDelegatesSupplier)));
	}

	@Override
	public GameComponentRefResolver<GameModel> getImportedModelResolver() {
		return delegateTo(m_importedModelRefResolver, ModelRefResolver::getImportedModelResolver);
	}

	@Override
	public GameComponentRefResolver<GameStage> getStageResolver() {
		return delegateTo(m_stageRefManager, ModelRefResolver::getStageResolver);
	}

	@Override
	public GameComponentRefResolver<GamePartPrototype> getPartPrototypeResolver() {
		return delegateTo(m_prototypeRefManager, ModelRefResolver::getPartPrototypeResolver);
	}

	@Override
	public GameComponentRefResolver<GameRole> getRoleResolver() {
		return delegateTo(m_roleRefManager, ModelRefResolver::getRoleResolver);
	}

	@Override
	public GameComponentRefManager<GameStage> getStageManager() {
		return m_stageRefManager;
	}

	@Override
	public GameComponentRefManager<GamePartPrototype> getPartPrototypeManager() {
		return m_prototypeRefManager;
	}

	@Override
	public GameComponentRefManager<GameRole> getRoleManager() {
		return m_roleRefManager;
	}

	@Override
	public void resolveAll() {
		m_stageRefManager.resolveAll();
		m_prototypeRefManager.resolveAll();
		m_roleRefManager.resolveAll();
	}

	@Override
	public ModelPlugin requireModelPlugin(PluginName pluginName) throws PluginException {
		return (ModelPlugin) m_pluginSet.requirePlugin(pluginName);
	}

}