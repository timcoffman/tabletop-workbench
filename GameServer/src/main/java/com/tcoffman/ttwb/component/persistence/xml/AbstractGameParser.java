package com.tcoffman.ttwb.component.persistence.xml;

import static com.tcoffman.ttwb.plugin.CorePlugins.MODEL_PARSER_XML;

import java.net.URI;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;

import com.tcoffman.ttwb.component.AbstractEditor;
import com.tcoffman.ttwb.component.GameComponent;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.persistence.xml.EventDispatcher;
import com.tcoffman.ttwb.plugin.ModelPlugin;
import com.tcoffman.ttwb.plugin.PluginException;
import com.tcoffman.ttwb.plugin.PluginName;
import com.tcoffman.ttwb.plugin.StatePlugin;

public abstract class AbstractGameParser {

	public AbstractGameParser() {
	}

	public abstract void parse(EventDispatcher<GameComponentBuilderException> dispatcher) throws XMLStreamException, GameComponentBuilderException;

	protected final ModelPlugin requireModelPlugin(NamespaceContext nsCtx, String prefix) throws GameComponentBuilderException {
		return requireModelPlugin(nsCtx.getNamespaceURI(prefix));
	}

	protected final ModelPlugin requireModelPlugin(QName qualifiedName) throws GameComponentBuilderException {
		return requireModelPlugin(qualifiedName.getNamespaceURI());
	}

	protected final ModelPlugin requireModelPlugin(StartElement startElement) throws GameComponentBuilderException {
		return requireModelPlugin(startElement.getName());
	}

	protected final ModelPlugin requireModelPlugin(String uri) throws GameComponentBuilderException {
		final PluginName pluginName = PluginName.create(URI.create(uri));
		return requireModelPlugin(pluginName);
	}

	protected ModelPlugin requireModelPlugin(final PluginName pluginName) throws GameComponentBuilderException {
		throw new UnsupportedOperationException("state plugins not supported in " + getClass().getSimpleName());
	}

	protected final StatePlugin requireStatePlugin(NamespaceContext nsCtx, String prefix) throws GameComponentBuilderException {
		return requireStatePlugin(nsCtx.getNamespaceURI(prefix));
	}

	protected final StatePlugin requireStatePlugin(QName qualifiedName) throws GameComponentBuilderException {
		return requireStatePlugin(qualifiedName.getNamespaceURI());
	}

	protected final StatePlugin requireStatePlugin(StartElement startElement) throws GameComponentBuilderException {
		return requireStatePlugin(startElement.getName());
	}

	protected final StatePlugin requireStatePlugin(String uri) throws GameComponentBuilderException {
		final PluginName pluginName = PluginName.create(URI.create(uri));
		return requireStatePlugin(pluginName);
	}

	protected StatePlugin requireStatePlugin(final PluginName pluginName) throws GameComponentBuilderException {
		throw new UnsupportedOperationException("state plugins not supported in " + getClass().getSimpleName());
	}

	protected interface Creator<E extends AbstractEditor<?>> {
		void create(AbstractEditor.Initializer<E> initializer) throws GameComponentBuilderException;
	}

	protected interface XmlInitializer<E extends AbstractEditor<?>> {
		void initialize(E editor) throws GameComponentBuilderException, XMLStreamException;
	}

	protected final <E extends AbstractEditor<?>> void createAndInitialize(Creator<E> creator, XmlInitializer<E> initializer) throws XMLStreamException,
			GameComponentBuilderException {
		try {

			creator.create((e) -> {

				try {
					initializer.initialize(e);
				} catch (final XMLStreamException ex) {
					throw new GameComponentBuilderException(MODEL_PARSER_XML, ex);
				}

			});

		} catch (final GameComponentBuilderException ex) {
			if (ex.getCause() instanceof XMLStreamException)
				throw (XMLStreamException) ex.getCause();
			else
				throw ex;
		}
	}

	protected interface ModelPluginLookup<T extends GameComponent> {
		GameComponentRef<T> apply(ModelPlugin plugin, String localName) throws PluginException;
	}

	protected <T extends GameComponent> GameComponentRef<T> parseRef(String ref, StartElement startElement, ModelPluginLookup<T> pluginLookup)
			throws GameComponentBuilderException {
		return parseRef(ref, startElement.getName(), startElement.getNamespaceContext(), pluginLookup);
	}

	protected <T extends GameComponent> GameComponentRef<T> parseRef(String ref, QName qname, NamespaceContext nsCtx, ModelPluginLookup<T> pluginLookup)
			throws GameComponentBuilderException {
		final String[] refParts = ref.split(":", 2);
		if (refParts.length == 0)
			throw new IllegalArgumentException("missing reference");

		ModelPlugin modelPlugin;
		String name;
		if (refParts.length == 1) {
			modelPlugin = requireModelPlugin(qname);
			name = refParts[0];
		} else {
			modelPlugin = requireModelPlugin(nsCtx, refParts[0]);
			name = refParts[1];
		}
		try {
			return pluginLookup.apply(modelPlugin, name);
		} catch (final PluginException ex) {
			throw new GameComponentBuilderException(ex.getPluginName(), ex);
		}
	}

}
