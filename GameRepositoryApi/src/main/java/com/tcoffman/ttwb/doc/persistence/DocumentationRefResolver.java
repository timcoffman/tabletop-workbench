package com.tcoffman.ttwb.doc.persistence;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.GameComponentRefResolver;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;

public interface DocumentationRefResolver {

	GameComponentRef<GameComponentDocumentation> getModel();

	GameComponentRefResolver<GameComponentDocumentation> getPrototypeResolver();

	GameComponentRefResolver<GameComponentDocumentation> getRuleResolver();

	GameComponentRefResolver<GameComponentDocumentation> getOperationResolver();

	GameComponentRefResolver<GameComponentDocumentation> getRoleResolver();

	GameComponentRefResolver<GameComponentDocumentation> getStageResolver();

}
