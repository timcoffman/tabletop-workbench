package com.tcoffman.ttwb.doc.persistence;

import com.tcoffman.ttwb.component.persistence.GameComponentRefManager;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;

public interface DocumentationRefManager extends DocumentationRefResolver {

	GameComponentRefManager<GameComponentDocumentation> getPrototypeManager();

	GameComponentRefManager<GameComponentDocumentation> getRuleManager();

	GameComponentRefManager<GameComponentDocumentation> getRoleManager();

	GameComponentRefManager<GameComponentDocumentation> getStageManager();

}
