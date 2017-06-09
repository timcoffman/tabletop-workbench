package com.tcoffman.ttwb.web.resource.state.pattern;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPatternSet;
import com.tcoffman.ttwb.web.GameModelRepository;
import com.tcoffman.ttwb.web.GameStateRepository;
import com.tcoffman.ttwb.web.resource.model.StagesResource;
import com.tcoffman.ttwb.web.resource.model.pattern.OperationPatternResource;
import com.tcoffman.ttwb.web.resource.state.AbstractStateSubresource;

public class OperationPatternSetResource extends AbstractStateSubresource {

	private final GameOperationPatternSet m_opPatternSet;

	public OperationPatternSetResource(GameStateRepository.Bundle stateBundle, GameOperationPatternSet opPatternSet) {
		super(stateBundle);
		m_opPatternSet = opPatternSet;
	}

	// @GET
	// @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	// public OperationPatternSetResource get() {
	// return this;
	// }

	public String getLabel() {
		return "Operation Pattern Set";
	}

	public String getResultLabel() throws GameComponentBuilderException {
		return m_opPatternSet.getResult().get().getDocumentation().getName(GameComponentDocumentation.Format.SHORT);
	}

	public URI getResultResource() throws GameComponentBuilderException {
		return StagesResource.pathTo(baseUriBuilder()).build(stateBundle().getModelId(), lookupStageId(m_opPatternSet.getResult()));
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<OperationPatternResource> getOperationPatterns() throws GameComponentBuilderException {
		final GameModelRepository.Bundle modelBundle = m_modelRepository.getBundle(stateBundle().getModelId());
		return m_opPatternSet.operations().map((p) -> createOperationPatternResource(modelBundle, p)).collect(Collectors.toList());
	}

	private OperationPatternResource createOperationPatternResource(GameModelRepository.Bundle modelBundle, GameOperationPattern opPattern) {
		return subresource(new OperationPatternResource(modelBundle, opPattern));
	}

}
