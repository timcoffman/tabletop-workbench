package com.tcoffman.ttwb.doc;

import static com.tcoffman.ttwb.plugin.CorePlugins.CORE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.StandardComponent;

public class StandardComponentDocumentation extends StandardComponent implements GameComponentDocumentation {

	private final Map<Format, String> m_names = new HashMap<Format, String>();
	private String m_description;

	private StandardComponentDocumentation() {
	}

	@Override
	public String getName(Format format) {
		return m_names.get(format);
	}

	@Override
	public String getDescription() {
		return m_description;
	}

	@Override
	public String toString() {
		return getName(Format.SHORT);
	}

	public static Editor create() {
		return new StandardComponentDocumentation().edit();
	}

	private Editor edit() {
		return new Editor();
	}

	public class Editor extends StandardComponent.Editor<StandardComponentDocumentation> {

		@Override
		protected void validate() throws GameComponentBuilderException {
			super.validate();
			final List<Supplier<String>> getters = Arrays.asList(() -> m_names.get(Format.SHORT), () -> m_names.get(Format.LONG), () -> m_description);
			final List<Consumer<String>> setters = Arrays.asList((v) -> m_names.put(Format.SHORT, v), (v) -> m_names.put(Format.LONG, v),
					(v) -> m_description = v);

			String defaultValue = getters.stream().map(Supplier::get).filter(Objects::nonNull).findFirst()
					.orElseThrow(() -> new GameComponentBuilderException(CORE, "missing all names and description"));
			for (int i = 0; i < getters.size(); ++i) {
				final String value = getters.get(i).get();
				if (null == value)
					setters.get(i).accept(defaultValue);
				else
					defaultValue = value;
			}
			requirePresent(CORE, "short name", m_names.get(Format.SHORT));
			requirePresent(CORE, "long name", m_names.get(Format.LONG));
			requirePresent(CORE, "description", m_description);
		}

		public Editor setName(Format format, String name) {
			requireNotDone();
			m_names.put(format, name);
			return this;
		}

		public Editor setDescription(String description) {
			requireNotDone();
			m_description = description;
			return this;
		}

	}

}
