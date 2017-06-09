package com.tcoffman.ttwb.doc.persistence;

import com.tcoffman.ttwb.component.persistence.GameComponentRefManager;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;

public interface DocumentationRefManager extends DocumentationRefResolver {

	void setModel(GameComponentDocumentation documentation);

	GameComponentRefManager<GameComponentDocumentation> getPrototypeManager();

	GameComponentRefManager<GameComponentDocumentation> getRuleManager();

	GameComponentRefManager<GameComponentDocumentation> getOperationManager();

	GameComponentRefManager<GameComponentDocumentation> getRoleManager();

	GameComponentRefManager<GameComponentDocumentation> getStageManager();

}
