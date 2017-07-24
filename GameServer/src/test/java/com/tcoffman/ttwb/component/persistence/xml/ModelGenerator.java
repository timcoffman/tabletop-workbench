package com.tcoffman.ttwb.component.persistence.xml;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;
import static com.tcoffman.ttwb.plugin.CorePlugins.GRID;

import com.tcoffman.ttwb.component.AbstractEditor.Initializer;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.core.Core;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.doc.StandardComponentDocumentation;
import com.tcoffman.ttwb.model.GameModel;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePlacePrototype;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.StandardGameModel;
import com.tcoffman.ttwb.model.StandardGameRule;
import com.tcoffman.ttwb.model.StandardRootGameModel;
import com.tcoffman.ttwb.plugin.DefaultPluginFactory;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginFactory;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.PluginSet;

/**
 *
 * creates a GameModel of the form
 *
 * <pre>
 * Model
 * - Role
 * - Prototype A
 * -- Place (bottom)
 * - Prototype B
 * -- Place (top)
 * - Part Instance (Prototype A)
 * - Part Instance (Prototype B)
 * - Prologue Stage
 * -- Rule (-> Epilogue)
 * --- (customized operation patterns)
 * - Epilogue Stage
 * </pre>
 */
public final class ModelGenerator {

	private static final ModelGenerator INSTANCE = new ModelGenerator();

	public static ModelGenerator instance() {
		return INSTANCE;
	}

	private final DefaultPluginFactory m_pluginFactory;

	private ModelGenerator() {
		m_pluginFactory = new DefaultPluginFactory();
		m_pluginFactory.install(CORE, com.tcoffman.ttwb.core.Core.class);
		m_pluginFactory.install(GRID, com.tcoffman.ttwb.core.Grid.class);
	}

	public PluginFactory getPluginFactory() {
		return m_pluginFactory;
	}

	public interface GeneratedModelCustomizer {
		PluginName getDeclaringPlugin();

		Initializer<StandardGameRule.Editor> getPrologueRuleInitializer(ModelGenerator.Result modelGenerationResults);
	}

	public interface Result {
		GameModel getModel();

		ModelPlugin requireModelPlugin(PluginName pluginName) throws PluginException;

		PluginSet getPluginSet();

		GameModel getRootModel();

		GameRole getRole();

		GamePartPrototype getPartPrototypeA();

		GamePartPrototype getPartPrototypeB();

		GamePlacePrototype getPartPrototypeAPlacePrototype();

		GamePlacePrototype getPartPrototypeBPlacePrototype();

		GameStage getPrologue();

		GameStage getEpilogue();

		PluginFactory getPluginFactory();

	}

	private final class ResultImpl implements Result {
		private GameModel m_model;
		private GameRole m_role;
		private GamePartPrototype m_partPrototypeA;
		private GamePartPrototype m_partPrototypeB;
		private GamePlacePrototype m_partPrototypeAPlacePrototype;
		private GamePlacePrototype m_partPrototypeBPlacePrototype;
		private GameStage m_prologue;
		private GameStage m_epilogue;
		private final PluginSet m_pluginSet;
		private final StandardRootGameModel m_rootModel;

		public ResultImpl(DefaultPluginFactory m_pluginFactory) {
			m_pluginSet = new PluginSet(m_pluginFactory);
			try {
				m_rootModel = StandardRootGameModel.create().setCorePlugin(m_pluginSet.requirePlugin(CORE)).setDocumentation(createGenericDocumentation("root"))
						.done();
			} catch (final PluginException ex) {
				throw new RuntimeException(ex);
			}
		}

		@Override
		public PluginFactory getPluginFactory() {
			return m_pluginFactory;
		}

		@Override
		public PluginSet getPluginSet() {
			return m_pluginSet;
		}

		@Override
		public GameModel getRootModel() {
			return m_rootModel;
		}

		@Override
		public ModelPlugin requireModelPlugin(PluginName name) throws PluginException {
			return (ModelPlugin) m_pluginSet.requirePlugin(name);
		}

		@Override
		public GameModel getModel() {
			return m_model;
		}

		public void setModel(GameModel model) {
			m_model = model;
		}

		@Override
		public GameRole getRole() {
			return m_role;
		}

		public void setRole(GameRole role) {
			m_role = role;
		}

		@Override
		public GamePartPrototype getPartPrototypeA() {
			return m_partPrototypeA;
		}

		public void setPrototypeA(GamePartPrototype prototypeA) {
			m_partPrototypeA = prototypeA;
		}

		@Override
		public GamePartPrototype getPartPrototypeB() {
			return m_partPrototypeB;
		}

		public void setPrototypeB(GamePartPrototype prototypeB) {
			m_partPrototypeB = prototypeB;
		}

		@Override
		public GamePlacePrototype getPartPrototypeAPlacePrototype() {
			return m_partPrototypeAPlacePrototype;
		}

		public void setPartPrototypeAPlace(GamePlacePrototype placePrototype) {
			m_partPrototypeAPlacePrototype = placePrototype;
		}

		@Override
		public GamePlacePrototype getPartPrototypeBPlacePrototype() {
			return m_partPrototypeBPlacePrototype;
		}

		public void setPrototypeBPlace(GamePlacePrototype placePrototype) {
			m_partPrototypeBPlacePrototype = placePrototype;
		}

		@Override
		public GameStage getPrologue() {
			return m_prologue;
		}

		public void setPrologue(GameStage prologue) {
			m_prologue = prologue;
		}

		@Override
		public GameStage getEpilogue() {
			return m_epilogue;
		}

		public void setEpilogue(GameStage epilogue) {
			m_epilogue = epilogue;
		}

	}

	private static GameComponentRef<GameComponentDocumentation> createGenericDocumentation(String name) throws GameComponentBuilderException {
		return GameComponentRef.wrap(StandardComponentDocumentation.create().setDescription(name).done());
	}

	public Result createModel(GeneratedModelCustomizer customizer) throws PluginException {
		final ResultImpl result = new ResultImpl(m_pluginFactory);
		final GameComponentRef<GamePlaceType> placeTypeBottom = result.requireModelPlugin(CORE).getPlaceType(Core.PLACE_PHYSICAL_BOTTOM);
		final GameComponentRef<GamePlaceType> placeTypeTop = result.requireModelPlugin(CORE).getPlaceType(Core.PLACE_PHYSICAL_TOP);
		final GameModel rootModel = result.getRootModel();

		final StandardGameModel.Editor model = StandardGameModel.create();
		model.addImportedModel(rootModel);
		model.setDocumentation(createGenericDocumentation("model"));
		model.createRole((role) -> {
			role.completed(result::setRole);
			role.setDocumentation(createGenericDocumentation("role"));
		});
		model.createPrototype(CORE, (prototype) -> {
			prototype.completed(result::setPrototypeA);
			prototype.setDocumentation(createGenericDocumentation("prototype-A"));
			prototype.createPlace((place) -> {
				place.completed(result::setPartPrototypeAPlace);
				place.setDocumentation(createGenericDocumentation("prototype-A-place"));
				place.setType(placeTypeBottom);
			});
		});
		model.createPrototype(CORE, (prototype) -> {
			prototype.completed(result::setPrototypeB);
			prototype.setDocumentation(createGenericDocumentation("prototype-B"));
			prototype.createPlace((place) -> {
				place.completed(result::setPrototypeBPlace);
				place.setDocumentation(createGenericDocumentation("prototype-B-place"));
				place.setType(placeTypeTop);
			});
		});
		model.createPart((part) -> {
			part.setPrototype(GameComponentRef.deferred(result::getPartPrototypeA));
		});
		model.createPart((part) -> {
			part.setPrototype(GameComponentRef.deferred(result::getPartPrototypeB));
		});
		model.createStage((stage) -> {
			stage.completed(result::setPrologue);
			stage.setDocumentation(createGenericDocumentation("prologue"));
			stage.createRule(customizer.getDeclaringPlugin(), (rule) -> {
				rule.setDocumentation(createGenericDocumentation("rule"));
				rule.setResult(GameComponentRef.deferred(result::getEpilogue));
				customizer.getPrologueRuleInitializer(result).configure(rule);
			});
		});
		model.createStage((stage) -> {
			stage.completed(result::setEpilogue);
			stage.setDocumentation(createGenericDocumentation("epilogue"));
		});
		model.setInitialStage(GameComponentRef.wrap(result.getPrologue()));
		result.setModel(model.done());
		return result;
	}
}
