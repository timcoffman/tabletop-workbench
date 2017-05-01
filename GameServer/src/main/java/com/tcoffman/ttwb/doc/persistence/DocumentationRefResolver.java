package com.tcoffman.ttwb.doc.persistence;

import com.tcoffman.ttwb.component.persistence.GameComponentRefResolver;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;

public interface DocumentationRefResolver {

	GameComponentRefResolver<GameComponentDocumentation> getPrototypeResolver();

	GameComponentRefResolver<GameComponentDocumentation> getRuleResolver();

	GameComponentRefResolver<GameComponentDocumentation> getRoleResolver();

	GameComponentRefResolver<GameComponentDocumentation> getStageResolver();

}
