package com.tcoffman.ttwb.component.persistence.gv;

import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.xml.stream.XMLStreamException;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.ExportException;
import org.jgrapht.ext.IntegerComponentNameProvider;
import org.jgrapht.graph.DefaultDirectedGraph;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.state.GameAuthorizationManager;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GamePartRelationship;
import com.tcoffman.ttwb.state.GameState;
import com.tcoffman.ttwb.state.persistence.ModelProvider;
import com.tcoffman.ttwb.state.persistence.StateRefManager;

public class StandardGameStateGraphWriter {

	private final Function<String, ModelProvider> m_modelProviderFactory;
	private final GameAuthorizationManager m_authorizationManager;

	public StandardGameStateGraphWriter(GameAuthorizationManager authorizationManager, Function<String, ModelProvider> modelProviderFactory) {
		m_modelProviderFactory = modelProviderFactory;
		m_authorizationManager = authorizationManager;
	}

	private final Map<String, StateRefManager> m_refManagers = new HashMap<>();

	public void registerManager(String stateId, StateRefManager stateRefManager) {
		if (m_refManagers.containsKey(stateId))
			throw new IllegalStateException("cannot replace external reference manager for state \"" + stateId + "\"");
		m_refManagers.put(stateId, stateRefManager);
	}

	public static class MapBuilderFactory<T extends Map<?, ?>> {
		private final Supplier<? extends T> m_mapFactory;

		public MapBuilderFactory(Supplier<? extends T> mapFactory) {
			m_mapFactory = mapFactory;
		}

		private static final MapBuilderFactory<HashMap<?, ?>> s_defaultMapBuilderFactory = new MapBuilderFactory<>(HashMap::new);

		public static MapBuilderFactory<HashMap<?, ?>> getDefault() {
			return s_defaultMapBuilderFactory;
		}

		public interface MapBuilder<K, V> {
			MapBuilder<K, V> put(K key, V value);

			Map<K, V> build();
		}

		public <K, V> MapBuilder<K, V> create() {
			return new MapBuilderImpl<>();
		}

		public <K, V> MapBuilder<K, V> create(Class<K> keyClass, Class<V> valueClass) {
			return new MapBuilderImpl<>();
		}

		private class MapBuilderImpl<K, V> implements MapBuilder<K, V> {

			@SuppressWarnings("unchecked")
			private final Map<K, V> m_map = (Map<K, V>) m_mapFactory.get();

			@Override
			public MapBuilder<K, V> put(K key, V value) {
				m_map.put(key, value);
				return this;
			}

			@Override
			public Map<K, V> build() {
				return m_map;
			}

		}
	}

	public void write(GameState state, OutputStream os, String modelId) throws GameComponentBuilderException, XMLStreamException, ExportException {
		final ModelProvider modelProvider = m_modelProviderFactory.apply(modelId);
		modelProvider.getModelRefResolver();

		final Graph<GamePart, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
		state.parts().forEach(graph::addVertex);

		// state.parts().flatMap(GamePart::places).forEach(graph::addVertex);

		state.relationships().forEach((rel) -> graph.addEdge(rel.getSource().get().getOwner(), rel.getDestination().get().getOwner(), new DefaultEdge(rel)));

		final DOTExporter<GamePart, DefaultEdge> exporter = new DOTExporter<>(new IntegerComponentNameProvider<>(), GamePart::toString,
				(e) -> ((GamePartRelationship) e.getUserObject()).getType().get().getLocalName(), (n) -> Collections.emptyMap(),
				(e) -> MapBuilderFactory.getDefault().create(String.class, String.class)
						.put("taillabel", ((GamePartRelationship) e.getUserObject()).getSource().get().getPrototype().get().getType().get().getLocalName())
						.put("headlabel", ((GamePartRelationship) e.getUserObject()).getDestination().get().getPrototype().get().getType().get().getLocalName())
						.build());
		exporter.exportGraph(graph, os);
	}

}
