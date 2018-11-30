package com.tcoffman.ttwb.component.persistence;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.doc.GameModelDocumentation;
import com.tcoffman.ttwb.doc.StandardComponentDocumentation;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefManager;
import com.tcoffman.ttwb.doc.persistence.DocumentationRefResolver;
import com.tcoffman.ttwb.doc.persistence.StandardDocumentationRefManager;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.StandardRootGameModel;
import com.tcoffman.ttwb.model.persistance.GameModelRepository;
import com.tcoffman.ttwb.model.persistance.ModelRefManager;
import com.tcoffman.ttwb.model.persistance.ModelRefResolver;
import com.tcoffman.ttwb.model.persistance.StandardModelRefManager;
import com.tcoffman.ttwb.plugin.Plugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.plugin.PluginSet;

public class GameRootModelRepository implements GameModelRepository {

	private final Bundle m_rootBundle;
	private final PluginFactory m_pluginFactory;

	public GameRootModelRepository(PluginFactory pluginFactory) {
		m_pluginFactory = pluginFactory;
		try {

			final GameComponentDocumentation rootModelDocs = StandardComponentDocumentation.create().setName(GameComponentDocumentation.Format.SHORT, "Root")
					.done();
			final GameComponentDocumentation rootPartPrototypeDocs = StandardComponentDocumentation.create()
					.setName(GameComponentDocumentation.Format.SHORT, "Root").done();
			final GameComponentDocumentation systemRoleDocs = StandardComponentDocumentation.create().setName(GameComponentDocumentation.Format.SHORT, "System")
					.done();

			final PluginSet rootPluginSet = new PluginSet(m_pluginFactory);

			final GameModelDocumentation rootDocs = new GameModelDocumentation() {

				@Override
				public GameComponentDocumentation model() {
					return rootModelDocs;
				}

				@Override
				public Stream<? extends GameComponentDocumentation> prototypes() {
					return Stream.of(rootPartPrototypeDocs);
				}

				@Override
				public Stream<? extends GameComponentDocumentation> stages() {
					return Stream.empty();
				}

				@Override
				public Stream<? extends GameComponentDocumentation> roles() {
					return Stream.of(systemRoleDocs);
				}

				@Override
				public Stream<? extends GameComponentDocumentation> rules() {
					return Stream.empty();
				}

				@Override
				public Stream<? extends GameComponentDocumentation> operations() {
					return Stream.empty();
				}

			};

			final Plugin corePlugin = rootPluginSet.requirePlugin(CORE);
			final GameModel rootModel = StandardRootGameModel.create().setCorePlugin(corePlugin).setDocumentation(GameComponentRef.wrap(rootModelDocs)).done();

			final ModelRefManager modelRefMgr = new StandardModelRefManager(rootPluginSet, this);

			modelRefMgr.getPartPrototypeManager().register(rootModel.effectiveRootPrototype().get(), "root");
			modelRefMgr.getRoleManager().register(rootModel.effectiveSystemRole(), "system");

			final DocumentationRefManager docRefMgr = new StandardDocumentationRefManager();

			docRefMgr.getPrototypeManager().register(rootModel.effectiveRootPrototype().get().getDocumentation(), "root");
			docRefMgr.getRoleManager().register(rootModel.effectiveSystemRole().getDocumentation(), "system");

			m_rootBundle = new Bundle() {

				@Override
				public PluginSet getPluginSet() {
					return rootPluginSet;
				}

				@Override
				public String getModelId() {
					return "root";
				}

				@Override
				public GameModelDocumentation getDocumentation() {
					return rootDocs;
				}

				@Override
				public DocumentationRefResolver getDocumentationResolver() {
					return docRefMgr;
				}

				@Override
				public ModelRefResolver getModelRefResolver() {
					return modelRefMgr;
				}

				@Override
				public GameModel getModel() {
					return rootModel;
				}

			};
		} catch (final GameComponentBuilderException ex) {
			throw new RuntimeException(ex);
		} catch (final PluginException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Bundle getBundle(String name, String documentationLang) throws GameComponentBuilderException {
		if ("root".equals(name))
			return m_rootBundle;
		return null;
	}

	@Override
	public Bundle getBundle(String name) throws GameComponentBuilderException {
		if ("root".equals(name))
			return m_rootBundle;
		return null;
	}

	@Override
	public Bundle getBundle(GameModel model) throws GameComponentBuilderException {
		if (model == m_rootBundle.getModel())
			return m_rootBundle;
		return null;
	}

}
