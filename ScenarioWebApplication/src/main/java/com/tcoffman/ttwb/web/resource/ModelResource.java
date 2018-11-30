package com.tcoffman.ttwb.web.resource;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.tcoffman.ttwb.dao.ModelDao;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@Api("Models")
@SwaggerDefinition(info = @Info(description = "Model Management", version = "1.0.0", title = "Game Model Server API"))
@Path("model")
public class ModelResource {

	@Inject
	ModelDao modelDao;

	private Model modelFrom(com.tcoffman.ttwb.dao.data.Model model) {
		return new Model(model.getModelUrl());
	}

	@ApiOperation("get all models")
	@GET
	public List<Model> getModels() {
		return modelDao.all().stream().map(this::modelFrom).collect(toList());
	}

}
