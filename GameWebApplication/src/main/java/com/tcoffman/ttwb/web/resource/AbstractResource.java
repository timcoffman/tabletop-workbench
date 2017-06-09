package com.tcoffman.ttwb.web.resource;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.stream.XMLStreamException;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.GameComponentRefManager;
import com.tcoffman.ttwb.component.persistence.GameComponentRefResolver;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.PluginSet;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GameState;
import com.tcoffman.ttwb.state.persistence.xml.StandardGameStateParser;
import com.tcoffman.ttwb.web.GameModelRepository;
import com.tcoffman.ttwb.web.GameStateRepository;
import com.tcoffman.ttwb.web.UnrecognizedValueException;

public abstract class AbstractResource {

	protected static <T> void foreachWithIndex(Stream<T> stream, BiConsumer<Long, T> consumer) {
		long i = 0;
		final Iterator<T> ri = stream.sequential().iterator();
		while (ri.hasNext())
			consumer.accept(i++, ri.next());
	}

	protected static <T> Optional<T> nthElement(long n, Stream<T> stream) {
		return stream.sequential().limit(n + 1).reduce((a, b) -> {
			return b;
		});
	}

	@Context
	UriInfo m_uriInfo;
	@Context
	ResourceContext m_resources;
	@Context
	Request m_request;

	@Inject
	protected GameModelRepository m_modelRepository;
	@Inject
	protected GameStateRepository m_stateRepository;

	public AbstractResource() {
	}

	protected UriBuilder baseUriBuilder() {
		return m_uriInfo.getBaseUriBuilder();
	}

	protected <T extends AbstractResource> T subresource(T resource) {
		return m_resources.initResource(resource);
	}

	private final class ModelProvider implements StandardGameStateParser.ModelProvider {
		private final String m_modelId;
		private GameModelRepository.Bundle m_modelBundle = null;

		public ModelProvider(String modelId) {
			m_modelId = modelId;
		}

		private GameModelRepository.Bundle getModelBundle() throws GameComponentBuilderException, XMLStreamException {
			if (null == m_modelBundle)
				m_modelBundle = m_modelRepository.getBundle(m_modelId);
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

	protected StandardGameStateParser.ModelProvider getModelProvider(String modelId) {
		return new ModelProvider(modelId);
	}

	public GameComponentBuilderException missingModelComponent() {
		return new GameComponentBuilderException(CORE, "missing model component");
	}

	public GameComponentBuilderException missingStateComponent() {
		return new GameComponentBuilderException(CORE, "missing state component");
	}

	private static ThreadLocal<Stack<Class<?>>> tl_beanWritablePropertyStack = new ThreadLocal<Stack<Class<?>>>() {
		@Override
		protected Stack<Class<?>> initialValue() {
			return new Stack<Class<?>>();
		}
	};

	protected Map<String, Object> beanWritablePropertyMap(Class<?> beanClass) {
		if (tl_beanWritablePropertyStack.get().contains(beanClass))
			return Collections.singletonMap("see", beanClass.getSimpleName());

		tl_beanWritablePropertyStack.get().push(beanClass);
		try {
			final PropertyDescriptor[] propertyDescriptors = java.beans.Introspector.getBeanInfo(beanClass).getPropertyDescriptors();
			return Stream.of(propertyDescriptors).filter((pd) -> null != pd.getWriteMethod())
					.collect(Collectors.toMap((pd) -> pd.getName(), this::beanPropertyDescription));
		} catch (final IntrospectionException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			tl_beanWritablePropertyStack.get().pop();
		}
	}

	private Object beanPropertyDescription(java.beans.PropertyDescriptor propertyDescriptor) {
		final Class<?> type = propertyDescriptor.getPropertyType();
		if (type.isPrimitive() || String.class.isAssignableFrom(type))
			return type.getSimpleName().toLowerCase();
		else if (Iterable.class.isAssignableFrom(type)) {
			final ParameterizedType genericType = (ParameterizedType) propertyDescriptor.getWriteMethod().getGenericParameterTypes()[0];
			return Collections.singletonMap("list", beanWritablePropertyMap((Class<?>) genericType.getActualTypeArguments()[0]));
		} else
			return beanWritablePropertyMap(type);
	}

	/* Plugin lookups */

	protected UnrecognizedValueException missingPlaceType(GameModelRepository.Bundle modelBundle) {
		return new UnrecognizedValueException(Stream.empty());
	}

	protected UnrecognizedValueException missingRelationshipType(GameModelRepository.Bundle modelBundle) {
		return new UnrecognizedValueException(Stream.empty());
	}

	protected GameComponentRef<GamePlaceType> lookupPlaceType(GameModelRepository.Bundle modelBundle, String placeTypeId) throws UnrecognizedValueException {
		final int delimiterPos = placeTypeId.lastIndexOf('/');
		final PluginName pluginName = PluginName.create(URI.create(placeTypeId.substring(0, delimiterPos)));
		final String localName = placeTypeId.substring(delimiterPos + 1);
		try {
			final ModelPlugin plugin = modelBundle.getModelRefResolver().requireModelPlugin(pluginName);
			return plugin.getPlaceType(localName);
		} catch (final PluginException ex) {
			throw missingPlaceType(modelBundle);
		}
	}

	protected GameComponentRef<GamePartRelationshipType> lookupRelationshipType(GameModelRepository.Bundle modelBundle, String relationshipTypeId)
			throws UnrecognizedValueException {
		final int delimiterPos = relationshipTypeId.lastIndexOf('/');
		final PluginName pluginName = PluginName.create(URI.create(relationshipTypeId.substring(0, delimiterPos)));
		final String localName = relationshipTypeId.substring(delimiterPos + 1);
		try {
			final ModelPlugin plugin = modelBundle.getModelRefResolver().requireModelPlugin(pluginName);
			return plugin.getRelationshipType(localName);
		} catch (final PluginException ex) {
			throw missingRelationshipType(modelBundle);
		}
	}

	/* Model lookups */

	protected UnrecognizedValueException missingRole(GameModelRepository.Bundle modelBundle) {
		final GameModel model = modelBundle.getModel();
		final GameComponentRefResolver<GameRole> roleResolver = modelBundle.getModelRefResolver().getRoleResolver();
		final Stream<?> availableRoleIds = model.roles().map(roleResolver::lookupId).filter(Optional::isPresent).map(Optional::get);
		return new UnrecognizedValueException(availableRoleIds);
	}

	protected UnrecognizedValueException missingStage(GameModelRepository.Bundle modelBundle) {
		final GameModel model = modelBundle.getModel();
		final GameComponentRefResolver<GameStage> stageResolver = modelBundle.getModelRefResolver().getStageResolver();
		final Stream<?> availableStageIds = model.stages().map(stageResolver::lookupId).filter(Optional::isPresent).map(Optional::get);
		return new UnrecognizedValueException(availableStageIds);
	}

	protected UnrecognizedValueException missingPartPrototype(GameModelRepository.Bundle modelBundle) {
		final GameModel model = modelBundle.getModel();
		final GameComponentRefResolver<GamePartPrototype> prototypeResolver = modelBundle.getModelRefResolver().getPartPrototypeResolver();
		final Stream<?> availablePartPrototypeIds = model.prototypes().map(prototypeResolver::lookupId).filter(Optional::isPresent).map(Optional::get);
		return new UnrecognizedValueException(availablePartPrototypeIds);
	}

	protected GameComponentRef<GameRole> lookupRole(GameModelRepository.Bundle modelBundle, String roleId) throws UnrecognizedValueException {
		final GameComponentRefResolver<GameRole> roleResolver = modelBundle.getModelRefResolver().getRoleResolver();
		return roleResolver.lookup(roleId).orElseThrow(() -> missingRole(modelBundle));
	}

	protected GameComponentRef<GameStage> lookupStage(GameModelRepository.Bundle modelBundle, String stageId) throws UnrecognizedValueException {
		final GameComponentRefResolver<GameStage> stageResolver = modelBundle.getModelRefResolver().getStageResolver();
		return stageResolver.lookup(stageId).orElseThrow(() -> missingStage(modelBundle));
	}

	protected GameComponentRef<GamePartPrototype> lookupPartPrototype(GameModelRepository.Bundle modelBundle, String prototypeId)
			throws UnrecognizedValueException {
		final GameComponentRefResolver<GamePartPrototype> prototypeResolver = modelBundle.getModelRefResolver().getPartPrototypeResolver();
		return prototypeResolver.lookup(prototypeId).orElseThrow(() -> missingPartPrototype(modelBundle));
	}

	protected String lookupRoleId(GameModelRepository.Bundle modelBundle, GameComponentRef<GameRole> role) throws GameComponentBuilderException {
		return modelBundle.getModelRefResolver().getRoleResolver().lookupId(role).orElseThrow(this::missingModelComponent);
	}

	protected String lookupStageId(GameModelRepository.Bundle modelBundle, GameComponentRef<GameStage> stage) throws GameComponentBuilderException {
		return modelBundle.getModelRefResolver().getStageResolver().lookupId(stage).orElseThrow(this::missingModelComponent);
	}

	protected String lookupPartPrototypeId(GameModelRepository.Bundle modelBundle, GameComponentRef<GamePartPrototype> prototype)
			throws GameComponentBuilderException {
		return modelBundle.getModelRefResolver().getPartPrototypeResolver().lookupId(prototype).orElseThrow(this::missingModelComponent);
	}

	protected String lookupId(GameModelRepository.Bundle modelBundle, GameRole role) throws GameComponentBuilderException {
		return modelBundle.getModelRefResolver().getRoleResolver().lookupId(role).orElseThrow(this::missingModelComponent);
	}

	protected String lookupId(GameModelRepository.Bundle modelBundle, GameStage stage) throws GameComponentBuilderException {
		return modelBundle.getModelRefResolver().getStageResolver().lookupId(stage).orElseThrow(this::missingModelComponent);
	}

	protected String lookupId(GameModelRepository.Bundle modelBundle, GamePartPrototype prototype) throws GameComponentBuilderException {
		return modelBundle.getModelRefResolver().getPartPrototypeResolver().lookupId(prototype).orElseThrow(this::missingModelComponent);
	}

	/* State lookups */

	protected UnrecognizedValueException missingPart(GameStateRepository.Bundle stateBundle) {
		final GameState state = stateBundle.getState();
		final GameComponentRefResolver<GamePart> partResolver = stateBundle.getStateRefResolver().getPartResolver();
		final Stream<?> availablePartIds = state.parts().map(partResolver::lookupId).filter(Optional::isPresent).map(Optional::get);
		return new UnrecognizedValueException(availablePartIds);
	}

	protected GameComponentRef<GamePart> lookupPart(GameStateRepository.Bundle stateBundle, String partId) throws UnrecognizedValueException {
		final GameComponentRefResolver<GamePart> partResolver = stateBundle.getStateRefResolver().getPartResolver();
		return partResolver.lookup(partId).orElseThrow(() -> missingPart(stateBundle));
	}

	protected String requirePartId(GameStateRepository.Bundle stateBundle, GameComponentRef<GamePart> part) throws GameComponentBuilderException {
		return requireId(stateBundle, part.get());
	}

	protected String requireId(GameStateRepository.Bundle stateBundle, GamePart part) throws GameComponentBuilderException {
		final GameComponentRefManager<GamePart> partManager = stateBundle.getStateRefManager().getPartManager();
		return partManager.lookupId(part).orElseGet(() -> {
			final String id = partManager.nextId();
			partManager.register(part, id);
			return id;
		});
	}
}
