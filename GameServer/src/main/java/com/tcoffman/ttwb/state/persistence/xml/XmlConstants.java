package com.tcoffman.ttwb.state.persistence.xml;

import javax.xml.namespace.QName;

public final class XmlConstants {
	public static final String STATE_NS = "urn:com.tcoffman.ttwb.state/v/1.0";
	public static final QName STATE_ELEMENT_QNAME = new QName(STATE_NS, "state");
	public static final QName STATE_ELEMENT_QNAME_PARTICIPANT = new QName(STATE_NS, "participant");
	public static final QName STATE_ELEMENT_QNAME_PARTS = new QName(STATE_NS, "parts");
	public static final QName STATE_ELEMENT_QNAME_PART = new QName(STATE_NS, "part");
	public static final QName STATE_ELEMENT_QNAME_RELATIONSHIPS = new QName(STATE_NS, "relationships");
	public static final QName STATE_ELEMENT_QNAME_RELATIONSHIP = new QName(STATE_NS, "rel");
	public static final QName STATE_ELEMENT_QNAME_CURRENT_STAGE = new QName(STATE_NS, "current-stage");

	public static final String STATE_ATTR_NAME_MODEL = "model";
	public static final String STATE_ATTR_NAME_REF = "ref";
	public static final String STATE_ATTR_NAME_STAGE_REF = "ref";
	public static final String STATE_ATTR_NAME_PROTOTYPE_REF = "prototype-ref";
	public static final String STATE_ATTR_NAME_ID = "id";
	public static final String STATE_ATTR_NAME_BINDING = "binding";
	public static final String STATE_ATTR_NAME_SRC = "src";
	public static final String STATE_ATTR_NAME_DST = "dst";
	public static final String STATE_ATTR_NAME_TYPE = "type";
}