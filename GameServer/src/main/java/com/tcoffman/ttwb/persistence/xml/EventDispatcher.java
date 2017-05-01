package com.tcoffman.ttwb.persistence.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class EventDispatcher<E extends Throwable> {
	private final String m_elementNamespaceURI;
	private final Iterator<Attribute> m_attributes;
	private final XMLEventReader m_eventReader;
	private boolean m_completed = false;

	public boolean completed() {
		return m_completed;
	}

	public interface EventConsumer<T extends XMLEvent, E extends Throwable> {
		void accept(T event, EventDispatcher<E> dispatcher) throws XMLStreamException, E;
	}

	public interface AttributeConsumer<E extends Throwable> {
		void accept(QName qualifiedName, String value) throws XMLStreamException, E;
	}

	public interface AttributeValueConsumer<E extends Throwable> {
		void accept(String value) throws XMLStreamException, E;
	}

	private EventConsumer<Attribute, E> eventConsumer(AttributeConsumer<E> consumer) {
		return (e, d) -> consumer.accept(e.getName(), e.getValue());
	}

	private AttributeConsumer<E> attributeConsumer(AttributeValueConsumer<E> consumer) {
		return (qn, v) -> consumer.accept(v);
	}

	private class EventHandler<T extends XMLEvent> {
		private final Predicate<T> m_predicate;
		private final EventConsumer<T, E> m_consumer;

		public EventHandler(Predicate<T> predicate, EventConsumer<T, E> consumer) {
			m_predicate = predicate;
			m_consumer = consumer;
		}

		boolean handle(T event) throws XMLStreamException, E {
			final boolean handled = m_predicate.test(event);
			if (handled)
				accept(m_consumer, event);
			return handled;
		}
	}

	private <T extends XMLEvent> void accept(EventConsumer<T, E> consumer, T event) throws XMLStreamException, E {
		if (event.isAttribute())
			consumer.accept(event, this);
		else if (event.isStartElement()) {
			final String namespaceURI = event.asStartElement().getName().getNamespaceURI();
			@SuppressWarnings("unchecked")
			final Iterator<Attribute> attributes = event.asStartElement().getAttributes();
			final EventDispatcher<E> dispatcher = new EventDispatcher<E>(namespaceURI, attributes, m_eventReader);
			consumer.accept(event, dispatcher);
			if (!dispatcher.completed())
				throw new IllegalStateException("handler failed to fully process event " + event);

		} else
			throw new IllegalStateException("handler cannot consume " + event);
	}

	private <T extends XMLEvent> EventHandler<T> createEventHandler(Predicate<T> predicate, EventConsumer<T, E> consumer) {
		return new EventHandler<T>(predicate, consumer);
	}

	private final List<EventHandler<StartElement>> m_startElementHandlers = new ArrayList<EventHandler<StartElement>>();
	private final List<EventHandler<Attribute>> m_attributeHandlers = new ArrayList<EventHandler<Attribute>>();
	private Optional<EventConsumer<StartElement, E>> m_unhandledStartElementConsumer = Optional.empty();
	private Optional<EventConsumer<Attribute, E>> m_unhandledAttributeConsumer = Optional.empty();

	public static <E extends Throwable> EventDispatcher<E> from(XMLEventReader eventReader, Class<E> exceptionClass) {
		return new EventDispatcher<E>(eventReader);
	}

	public EventDispatcher(XMLEventReader eventReader) {
		this(null, Collections.emptyIterator(), eventReader);
	}

	public EventDispatcher(String elementNamespaceURI, Iterator<Attribute> attributes, XMLEventReader eventReader) {
		m_eventReader = eventReader;
		m_elementNamespaceURI = elementNamespaceURI;
		m_attributes = attributes;
	}

	public EventDispatcher<E> other(EventConsumer<StartElement, E> consumer) {
		m_unhandledStartElementConsumer = Optional.of(consumer);
		return this;
	}

	public EventDispatcher<E> on(QName qname, EventConsumer<StartElement, E> consumer) {
		return on((e) -> qname.equals(e.getName()), consumer);
	}

	public EventDispatcher<E> on(String localName, EventConsumer<StartElement, E> consumer) {
		return on(new QName(localName), consumer);
	}

	public EventDispatcher<E> on(Predicate<StartElement> predicate, EventConsumer<StartElement, E> consumer) {
		m_startElementHandlers.add(createEventHandler(predicate, consumer));
		return this;
	}

	public EventDispatcher<E> attr(AttributeConsumer<E> consumer) {
		m_unhandledAttributeConsumer = Optional.of(eventConsumer(consumer));
		return this;
	}

	public EventDispatcher<E> attr(QName qname, AttributeConsumer<E> consumer) {
		return attr((e) -> qname.equals(e.getName()), consumer);
	}

	public EventDispatcher<E> attr(QName qname, AttributeValueConsumer<E> consumer) {
		return attr(qname, attributeConsumer(consumer));
	}

	public EventDispatcher<E> attr(String localName, AttributeConsumer<E> consumer) {
		return attr(new QName(localName), consumer);
	}

	public EventDispatcher<E> attr(String localName, AttributeValueConsumer<E> consumer) {
		return attr(localName, attributeConsumer(consumer));
	}

	public EventDispatcher<E> attr(Predicate<Attribute> predicate, AttributeConsumer<E> consumer) {
		m_attributeHandlers.add(createEventHandler(predicate, (e, d) -> consumer.accept(e.getName(), e.getValue())));
		return this;
	}

	public interface Producer<T, E extends Throwable> {
		T produce() throws E;
	}

	public String contents() throws XMLStreamException {
		final StringBuffer sb = new StringBuffer();
		skip(sb::append);
		return sb.toString();
	}

	public void skip() throws XMLStreamException {
		skip((c) -> {
		});
	}

	public void skip(Consumer<String> characterConsumer) throws XMLStreamException {
		int depth = 1;
		while (m_eventReader.hasNext() && !m_completed) {
			final XMLEvent event = m_eventReader.nextEvent();
			switch (event.getEventType()) {
			case XMLEvent.SPACE:
			case XMLEvent.CHARACTERS:
			case XMLEvent.CDATA:
				characterConsumer.accept(event.asCharacters().getData());
				break;
			case XMLEvent.START_DOCUMENT:
			case XMLEvent.START_ELEMENT:
				++depth;
				break;
			case XMLEvent.END_DOCUMENT:
			case XMLEvent.END_ELEMENT:
				if (--depth == 0) {
					m_completed = true;
					return;
				}
				break;
			}
		}
		throw new IllegalStateException("xml stream ended without end-of-document or end-of-element");
	}

	public void read() throws XMLStreamException, E {
		produce(() -> null);
	}

	public <T> T produce(Producer<T, E> producer) throws XMLStreamException, E {
		while (m_attributes.hasNext())
			handle(m_attributes.next());

		while (m_eventReader.hasNext() && !m_completed) {
			final XMLEvent event = m_eventReader.nextEvent();
			// System.out.println("*** " + event.getEventType() + ": " +
			// event.toString());
			switch (event.getEventType()) {
			case XMLEvent.END_DOCUMENT:
			case XMLEvent.END_ELEMENT:
				m_completed = true;
				return producer.produce();
			case XMLEvent.START_DOCUMENT:
				/* OK */
				break;
			case XMLEvent.START_ELEMENT:
				handle((StartElement) event);
				break;
			}
		}
		throw new IllegalStateException("xml stream ended without end-of-document");
	}

	private void handle(StartElement startElement) throws XMLStreamException, E {
		for (final EventHandler<StartElement> handler : m_startElementHandlers)
			if (handler.handle(startElement))
				return;

		if (m_unhandledStartElementConsumer.isPresent()) {
			accept(m_unhandledStartElementConsumer.get(), startElement);
			return;
		}

		throw new IllegalStateException("no handler for " + startElement + " at " + startElement.getLocation().getLineNumber());
	}

	private void handle(Attribute attribute) throws XMLStreamException, E {
		for (final EventHandler<Attribute> handler : m_attributeHandlers)
			if (handler.handle(attribute))
				return;

		if (m_unhandledAttributeConsumer.isPresent()) {
			accept(m_unhandledAttributeConsumer.get(), attribute);
			return;
		}

		/* only throw if the attribute is in the namespace of its element */
		if (attribute.getName().getNamespaceURI().equals(m_elementNamespaceURI))
			throw new IllegalStateException("no handler for " + attribute + " at " + m_eventReader.peek().getLocation().getLineNumber());
	}
}
