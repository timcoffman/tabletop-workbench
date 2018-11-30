package com.tcoffman.ttwb.dao;

import java.util.Collection;
import java.util.Optional;

import com.tcoffman.ttwb.dao.data.Model;

public interface ModelDao {

	Collection<Model> all();

	Optional<Model> lookup(String modelUrl);
}
