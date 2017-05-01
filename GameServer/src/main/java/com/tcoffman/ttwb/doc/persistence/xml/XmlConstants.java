package com.tcoffman.ttwb.doc.persistence.xml;

import javax.xml.namespace.QName;

public final class XmlConstants {
	public static final String DOC_NS = "urn:com.tcoffman.ttwb.doc/v/1.0";
	public static final QName DOC_ELEMENT_QNAME = new QName(DOC_NS, "documentation");

	public static final QName DOC_ELEMENT_QNAME_MODEL = new QName(DOC_NS, "model");
	public static final QName DOC_ELEMENT_QNAME_NAME = new QName(DOC_NS, "name");
	public static final QName DOC_ELEMENT_QNAME_DESCRIPTION = new QName(DOC_NS, "description");
	public static final QName DOC_ELEMENT_QNAME_STAGE = new QName(DOC_NS, "stage");
	public static final QName DOC_ELEMENT_QNAME_RULE = new QName(DOC_NS, "rule");
	public static final QName DOC_ELEMENT_QNAME_PROTOTYPE = new QName(DOC_NS, "prototype");
	public static final QName DOC_ELEMENT_QNAME_ROLE = new QName(DOC_NS, "role");

	public static final String DOC_ATTR_NAME_LANGUAGE = "lang";
	public static final String DOC_ATTR_NAME_ID = "id";
	public static final String DOC_ATTR_NAME_FORMAT = "format";
}