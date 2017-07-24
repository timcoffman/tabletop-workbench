package com.tcoffman.ttwb.component.persistence.xml;

import static com.tcoffman.ttwb.model.persistance.xml.XmlConstants.MODEL_ATTR_NAME_ID;
import static com.tcoffman.ttwb.state.persistence.xml.XmlConstants.STATE_ATTR_NAME_ID;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.xml.namespace.QName;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tcoffman.ttwb.component.GameComponent;
import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.plugin.PluginName;

public abstract class AbstractWriter {

	private final Map<Integer, Element> m_objectElements = new HashMap<Integer, Element>();
	private final Map<Integer, String> m_objectIdentifierMap = new HashMap<Integer, String>();
	private final Map<String, PluginName> m_namespacePrefixes = new HashMap<String, PluginName>();

	protected Element createAndAppendElement(Node parentNode, Object boundObject, QName name) {
		if (m_objectElements.containsKey(boundObject.hashCode()))
			throw new IllegalStateException("object already bound to an element");

		final Element childElement = createAndAppendElement(parentNode, name);

		m_objectElements.put(boundObject.hashCode(), childElement);

		final String id = m_objectIdentifierMap.get(boundObject.hashCode());
		if (null != id)
			childElement.setAttribute(STATE_ATTR_NAME_ID, id);

		return childElement;
	}

	protected Element createAndAppendElement(Node parentNode, QName name) {
		final Document document = parentNode.getNodeType() == Node.DOCUMENT_NODE ? (Document) parentNode : parentNode.getOwnerDocument();
		final Element childElement = document.createElementNS(name.getNamespaceURI(), name.getLocalPart());
		parentNode.appendChild(childElement);

		return childElement;

	}

	private final Map<PluginName, String> m_namespaces = new HashMap<PluginName, String>();

	public abstract void write(Document document) throws GameComponentBuilderException;

	private String nextAvailablePrefix(String prefixBase) {
		String prefix = prefixBase;
		int i = 0;
		while (m_namespacePrefixes.containsKey(prefix))
			prefix = prefixBase + Integer.toString(++i, 10);
		return prefix;
	}

	protected String prefixFor(PluginName pluginName) {
		String prefix = m_namespaces.get(pluginName);
		if (null != prefix)
			return prefix;

		prefix = nextAvailablePrefix(pluginName.getQualifiedName().replaceAll(".*[^a-zA-Z]", "").toLowerCase());

		m_namespacePrefixes.put(prefix, pluginName);
		m_namespaces.put(pluginName, prefix);

		return prefix;
	}

	protected Supplier<IllegalArgumentException> missingComponent(GameComponentRef<? extends GameComponent> componentRef) {
		return () -> createMissingComponentException(componentRef);
	}

	private IllegalArgumentException createMissingComponentException(GameComponentRef<? extends GameComponent> componentRef) {
		return new IllegalArgumentException("missing reference to component " + componentRef);
	}

	protected Supplier<IllegalArgumentException> missingComponent(GameComponent component) {
		return () -> createMissingComponentException(component);
	}

	private IllegalArgumentException createMissingComponentException(GameComponent component) {
		return new IllegalArgumentException("missing reference to component " + component);
	}

	protected String idFor(Object obj, Supplier<String> idSupplier) {
		if (null == obj)
			throw new IllegalArgumentException("cannot provide an id for a missing component");
		String id = m_objectIdentifierMap.get(obj.hashCode());
		if (null == id)
			m_objectIdentifierMap.put(obj.hashCode(), id = idSupplier.get());
		return id;
	}

	protected void newIdCreatedFor(String id, Object obj) {
		final Element existingElement = m_objectElements.get(obj.hashCode());
		if (null != existingElement)
			existingElement.setAttribute(MODEL_ATTR_NAME_ID, id);
	}

}
