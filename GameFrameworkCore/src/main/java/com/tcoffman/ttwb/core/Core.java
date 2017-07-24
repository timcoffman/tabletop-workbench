package com.tcoffman.ttwb.core;

import java.util.Optional;
import java.util.stream.Stream;

import com.tcoffman.ttwb.component.GameComponent;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.model.GameModelComponent;
import com.tcoffman.ttwb.model.GameModelProperty;
import com.tcoffman.ttwb.model.GamePartPrototype;
import com.tcoffman.ttwb.model.GamePartRelationshipType;
import com.tcoffman.ttwb.model.GamePlacePrototype;
import com.tcoffman.ttwb.model.GamePlaceType;
import com.tcoffman.ttwb.model.GameRole;
import com.tcoffman.ttwb.plugin.AbstractGeneralPlugin;
import com.tcoffman.ttwb.plugin.PluginException;

public class Core extends AbstractGeneralPlugin {

	public static final String PART_ROOT = "root";

	public static final String PLACE_PHYSICAL_TOP = "top";
	public static final String PLACE_PHYSICAL_BOTTOM = "bottom";

	public static final String RELATIONSHIP_PHYSICAL = "location";

	public static final String TOKEN_ROOT = "root";
	public static final String TOKEN_SUBJECT = "subject";
	public static final String TOKEN_TARGET = "target";
	public static final String TOKEN_SYSTEM = "system";

	@Override
	public GameComponentRef<GamePlaceType> getPlaceType(String localName) throws PluginException {
		if (PLACE_PHYSICAL_TOP.equals(localName))
			return getPlaceTypePhysicalTop();
		else if (PLACE_PHYSICAL_BOTTOM.equals(localName))
			return getPlaceTypePhysicalBottom();
		else
			return super.getPlaceType(localName);
	}

	@Override
	public GameComponentRef<GamePartRelationshipType> getRelationshipType(String localName) throws PluginException {
		if (RELATIONSHIP_PHYSICAL.equals(localName))
			return getRelationshipTypePhysical();
		else
			return super.getRelationshipType(localName);
	}

	private GamePartPrototype m_root = null;

	private final class CoreGamePartPrototype extends PluginComponent implements GamePartPrototype {

		private final GamePlacePrototype m_top;

		public CoreGamePartPrototype(String localName) {
			super(localName);
			m_top = new RootGamePlacePrototype();
		}

		@Override
		public GameComponentDocumentation getDocumentation() {
			throw new UnsupportedOperationException("not yet implemented");
		}

		@Override
		public Stream<? extends GamePlacePrototype> effectivePlaces() {
			return places();
		}

		@Override
		public Stream<? extends GamePlacePrototype> places() {
			return Stream.of(m_top);
		}

		@Override
		public Optional<GameComponentRef<GamePartPrototype>> getExtends() {
			return Optional.empty();
		}

		@Override
		public Optional<GameComponentRef<GameRole>> effectiveRoleBinding() {
			return getRoleBinding();
		}

		@Override
		public Optional<GameComponentRef<GameRole>> getRoleBinding() {
			return Optional.empty();
		}

		private final class RootGamePlacePrototype implements GamePlacePrototype {

			public RootGamePlacePrototype() {
			}

			@Override
			public <T extends GameComponent> GameComponentRef<T> self(Class<T> asType) {
				return GameComponentRef.wrap(asType.cast(this));
			}

			@Override
			public GameComponentDocumentation getDocumentation() {
				throw new UnsupportedOperationException("not yet implemented");
			}

			@Override
			public GamePartPrototype getOwner() {
				return CoreGamePartPrototype.this;
			}

			@Override
			public GameComponentRef<GamePlaceType> getType() {
				return getPlaceTypePhysicalTop();
			}

			@Override
			public Optional<GameComponentRef<GameRole>> getRoleBinding() {
				return Optional.empty();
			}

			@Override
			public Stream<? extends GameModelProperty> properties() {
				return Stream.empty();
			}

			@Override
			public Stream<? extends GameModelComponent> components() {
				return Stream.empty();
			}
		}
	}

	private GameComponentRef<GamePartPrototype> getPartPrototypeRoot() {
		if (null == m_root)
			m_root = new CoreGamePartPrototype(PART_ROOT);
		return createRef(m_root);
	}

	private GamePartRelationshipType m_location = null;

	private final class CoreGameRelationshipType extends PluginComponent implements GamePartRelationshipType {
		public CoreGameRelationshipType(String localName) {
			super(localName);
		}
	}

	private class CoreGamePlaceType extends PluginComponent implements GamePlaceType {
		public CoreGamePlaceType(String localName) {
			super(localName);
		}
	}

	private GameComponentRef<GamePartRelationshipType> getRelationshipTypePhysical() {
		if (null == m_location)
			m_location = new CoreGameRelationshipType(RELATIONSHIP_PHYSICAL);
		return createRef(m_location);
	}

	private GamePlaceType m_physicalTop = null;
	private GamePlaceType m_physicalBottom = null;

	private GameComponentRef<GamePlaceType> getPlaceTypePhysicalTop() {
		if (null == m_physicalTop)
			m_physicalTop = new CoreGamePlaceType(PLACE_PHYSICAL_TOP);
		return createRef(m_physicalTop);
	}

	private GameComponentRef<GamePlaceType> getPlaceTypePhysicalBottom() {
		if (null == m_physicalBottom)
			m_physicalBottom = new CoreGamePlaceType(PLACE_PHYSICAL_BOTTOM);
		return createRef(m_physicalBottom);
	}
}
