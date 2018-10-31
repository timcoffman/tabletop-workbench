package com.tcoffman.ttwb.web.resource;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.net.URI;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.GameComponentRefManager;
import com.tcoffman.ttwb.component.persistence.GameComponentRefResolver;
import com.tcoffman.ttwb.component.persistence.RespositoryBasedModelProvider;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.state.GamePart;
import com.tcoffman.ttwb.state.GameState;
import com.tcoffman.ttwb.state.persistence.xml.StandardGameStateParser;
import com.tcoffman.ttwb.web.GameModelFileRepository;
import com.tcoffman.ttwb.web.GameStateFileRepository;
import com.tcoffman.ttwb.web.UnrecognizedValueException;
import com.tcoffman.ttwb.web.resource.ResourceMetaData.Builder;

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
	protected GameModelFileRepository m_modelRepository;
	@Inject
	protected GameStateFileRepository m_stateRepository;

	public AbstractResource() {
	}

	protected UriBuilder baseUriBuilder() {
		return m_uriInfo.getBaseUriBuilder();
	}

	@GET
	@Path("/meta")
	public final ResourceMetaData getMeta() {
		return metaDataBuilder().build();
	}

	protected Builder metaDataBuilder() {
		return ResourceMetaData.forResource(this);
	}

	protected <T extends AbstractResource> T subresource(T resource) {
		return m_resources.initResource(resource);
	}

	protected StandardGameStateParser.ModelProvider getModelProvider(String modelId) {
		return new RespositoryBasedModelProvider(modelId, () -> m_modelRepository);
	}

	public GameComponentBuilderException missingModelComponent() {
		return new GameComponentBuilderException(CORE, "missing model component");
	}

	public GameComponentBuilderException missingStateComponent() {
		return new GameComponentBuilderException(CORE, "missing state component");
	}

	/* Plugin lookups */

	protected UnrecognizedValueException missingPlaceType(GameModelFileRepository.Bundle modelBundle) {
		return new UnrecognizedValueException(Stream.empty());
	}

	protected UnrecognizedValueException missingRelationshipType(GameModelFileRepository.Bundle modelBundle) {
		return new UnrecognizedValueException(Stream.empty());
	}

	protected GameComponentRef<GamePlaceType> lookupPlaceType(GameModelFileRepository.Bundle modelBundle, String placeTypeId)
			throws UnrecognizedValueException {
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

	protected GameComponentRef<GamePartRelationshipType> lookupRelationshipType(GameModelFileRepository.Bundle modelBundle, String relationshipTypeId)
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

	protected UnrecognizedValueException missingRole(GameModelFileRepository.Bundle modelBundle) {
		final GameModel model = modelBundle.getModel();
		final GameComponentRefResolver<GameRole> roleResolver = modelBundle.getModelRefResolver().getRoleResolver();
		final Stream<?> availableRoleIds = model.roles().map(roleResolver::lookupId).filter(Optional::isPresent).map(Optional::get);
		return new UnrecognizedValueException(availableRoleIds);
	}

	protected UnrecognizedValueException missingStage(GameModelFileRepository.Bundle modelBundle) {
		final GameModel model = modelBundle.getModel();
		final GameComponentRefResolver<GameStage> stageResolver = modelBundle.getModelRefResolver().getStageResolver();
		final Stream<?> availableStageIds = model.stages().map(stageResolver::lookupId).filter(Optional::isPresent).map(Optional::get);
		return new UnrecognizedValueException(availableStageIds);
	}

	protected UnrecognizedValueException missingPartPrototype(GameModelFileRepository.Bundle modelBundle) {
		final GameModel model = modelBundle.getModel();
		final GameComponentRefResolver<GamePartPrototype> prototypeResolver = modelBundle.getModelRefResolver().getPartPrototypeResolver();
		final Stream<?> availablePartPrototypeIds = model.prototypes().map(prototypeResolver::lookupId).filter(Optional::isPresent).map(Optional::get);
		return new UnrecognizedValueException(availablePartPrototypeIds);
	}

	protected GameComponentRef<GameRole> lookupRole(GameModelFileRepository.Bundle modelBundle, String roleId) throws UnrecognizedValueException {
		final GameComponentRefResolver<GameRole> roleResolver = modelBundle.getModelRefResolver().getRoleResolver();
		return roleResolver.lookup(roleId).orElseThrow(() -> missingRole(modelBundle));
	}

	protected GameComponentRef<GameStage> lookupStage(GameModelFileRepository.Bundle modelBundle, String stageId) throws UnrecognizedValueException {
		final GameComponentRefResolver<GameStage> stageResolver = modelBundle.getModelRefResolver().getStageResolver();
		return stageResolver.lookup(stageId).orElseThrow(() -> missingStage(modelBundle));
	}

	protected GameComponentRef<GamePartPrototype> lookupPartPrototype(GameModelFileRepository.Bundle modelBundle, String prototypeId)
			throws UnrecognizedValueException {
		final GameComponentRefResolver<GamePartPrototype> prototypeResolver = modelBundle.getModelRefResolver().getPartPrototypeResolver();
		return prototypeResolver.lookup(prototypeId).orElseThrow(() -> missingPartPrototype(modelBundle));
	}

	protected String lookupRoleId(GameModelFileRepository.Bundle modelBundle, GameComponentRef<GameRole> role) throws GameComponentBuilderException {
		return modelBundle.getModelRefResolver().getRoleResolver().lookupId(role).orElseThrow(this::missingModelComponent);
	}

	protected String lookupStageId(GameModelFileRepository.Bundle modelBundle, GameComponentRef<GameStage> stage) throws GameComponentBuilderException {
		return modelBundle.getModelRefResolver().getStageResolver().lookupId(stage).orElseThrow(this::missingModelComponent);
	}

	protected String lookupPartPrototypeId(GameModelFileRepository.Bundle modelBundle, GameComponentRef<GamePartPrototype> prototype)
			throws GameComponentBuilderException {
		return modelBundle.getModelRefResolver().getPartPrototypeResolver().lookupId(prototype).orElseThrow(this::missingModelComponent);
	}

	protected String lookupId(GameModelFileRepository.Bundle modelBundle, GameRole role) throws GameComponentBuilderException {
		return modelBundle.getModelRefResolver().getRoleResolver().lookupId(role).orElseThrow(this::missingModelComponent);
	}

	protected String lookupId(GameModelFileRepository.Bundle modelBundle, GameStage stage) throws GameComponentBuilderException {
		return modelBundle.getModelRefResolver().getStageResolver().lookupId(stage).orElseThrow(this::missingModelComponent);
	}

	protected String lookupId(GameModelFileRepository.Bundle modelBundle, GamePartPrototype prototype) throws GameComponentBuilderException {
		return modelBundle.getModelRefResolver().getPartPrototypeResolver().lookupId(prototype).orElseThrow(this::missingModelComponent);
	}

	/* State lookups */

	protected UnrecognizedValueException missingPart(GameStateFileRepository.Bundle stateBundle) {
		final GameState state = stateBundle.getState();
		final GameComponentRefResolver<GamePart> partResolver = stateBundle.getStateRefResolver().getPartResolver();
		final Stream<?> availablePartIds = state.parts().map(partResolver::lookupId).filter(Optional::isPresent).map(Optional::get);
		return new UnrecognizedValueException(availablePartIds);
	}

	protected GameComponentRef<GamePart> lookupPart(GameStateFileRepository.Bundle stateBundle, String partId) throws UnrecognizedValueException {
		final GameComponentRefResolver<GamePart> partResolver = stateBundle.getStateRefResolver().getPartResolver();
		return partResolver.lookup(partId).orElseThrow(() -> missingPart(stateBundle));
	}

	protected String requirePartId(GameStateFileRepository.Bundle stateBundle, GameComponentRef<GamePart> part) throws GameComponentBuilderException {
		return requireId(stateBundle, part.get());
	}

	protected String requireId(GameStateFileRepository.Bundle stateBundle, GamePart part) throws GameComponentBuilderException {
		final GameComponentRefManager<GamePart> partManager = stateBundle.getStateRefManager().getPartManager();
		return partManager.lookupId(part).orElseGet(() -> {
			final String id = partManager.nextId();
			partManager.register(part, id);
			return id;
		});
	}
}
