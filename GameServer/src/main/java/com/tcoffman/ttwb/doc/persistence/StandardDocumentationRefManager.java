package com.tcoffman.ttwb.doc.persistence;

import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.component.persistence.GameComponentRefManager;
import com.tcoffman.ttwb.component.persistence.GameComponentRefResolver;
import com.tcoffman.ttwb.component.persistence.StandardComponentRefManager;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;

public class StandardDocumentationRefManager implements DocumentationRefManager {
	private GameComponentDocumentation m_model;
	private final StandardComponentRefManager<GameComponentDocumentation> m_prototypeRefManager = new StandardComponentRefManager<GameComponentDocumentation>(
			"prototype");
	private final StandardComponentRefManager<GameComponentDocumentation> m_ruleRefManager = new StandardComponentRefManager<GameComponentDocumentation>("rule");
	private final StandardComponentRefManager<GameComponentDocumentation> m_roleRefManager = new StandardComponentRefManager<GameComponentDocumentation>("role");
	private final StandardComponentRefManager<GameComponentDocumentation> m_stageRefManager = new StandardComponentRefManager<GameComponentDocumentation>(
			"stage");

	@Override
	public GameComponentRef<GameComponentDocumentation> getModel() {
		return GameComponentRef.wrap(m_model);
	}

	@Override
	public void setModel(GameComponentDocumentation documentation) {
		m_model = documentation;
	}

	@Override
	public GameComponentRefResolver<GameComponentDocumentation> getPrototypeResolver() {
		return m_prototypeRefManager;
	}

	@Override
	public GameComponentRefManager<GameComponentDocumentation> getPrototypeManager() {
		return m_prototypeRefManager;
	}

	@Override
	public GameComponentRefResolver<GameComponentDocumentation> getRuleResolver() {
		return m_ruleRefManager;
	}

	@Override
	public GameComponentRefManager<GameComponentDocumentation> getRuleManager() {
		return m_ruleRefManager;
	}

	@Override
	public GameComponentRefResolver<GameComponentDocumentation> getRoleResolver() {
		return m_roleRefManager;
	}

	@Override
	public GameComponentRefManager<GameComponentDocumentation> getRoleManager() {
		return m_roleRefManager;
	}

	@Override
	public GameComponentRefResolver<GameComponentDocumentation> getStageResolver() {
		return m_stageRefManager;
	}

	@Override
	public GameComponentRefManager<GameComponentDocumentation> getStageManager() {
		return m_stageRefManager;
	}

}
