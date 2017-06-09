package com.tcoffman.ttwb.state.persistence.xml;

import javax.xml.namespace.QName;

public final class XmlConstants {
	public static final String STATE_NS = "urn:com.tcoffman.ttwb.state/v/1.0";
	public static final QName STATE_ELEMENT_QNAME = new QName(STATE_NS, "state");
	public static final QName STATE_ELEMENT_QNAME_PARTICIPANT = new QName(STATE_NS, "participant");
	public static final QName STATE_ELEMENT_QNAME_AUTHORIZATION = new QName(STATE_NS, "auth");
	public static final QName STATE_ELEMENT_QNAME_CURRENT_STAGE = new QName(STATE_NS, "current-stage");
	public static final QName STATE_ELEMENT_QNAME_PARTS = new QName(STATE_NS, "parts");
	public static final QName STATE_ELEMENT_QNAME_PART = new QName(STATE_NS, "part");
	public static final QName STATE_ELEMENT_QNAME_RELATIONSHIPS = new QName(STATE_NS, "relationships");
	public static final QName STATE_ELEMENT_QNAME_RELATIONSHIP = new QName(STATE_NS, "rel");
	public static final QName STATE_ELEMENT_QNAME_LOG = new QName(STATE_NS, "log");
	public static final QName STATE_ELEMENT_QNAME_LOG_ENTRY = new QName(STATE_NS, "entry");
	public static final QName STATE_ELEMENT_QNAME_LOG_FORWARD_MUTATIONS = new QName(STATE_NS, "forward");
	public static final QName STATE_ELEMENT_QNAME_LOG_ROLLBACK_MUTATIONS = new QName(STATE_NS, "rollback");
	public static final QName STATE_ELEMENT_QNAME_LOG_MUTATION_REL_ADD = new QName(STATE_NS, "add-relationship");
	public static final QName STATE_ELEMENT_QNAME_LOG_MUTATION_REL_REM = new QName(STATE_NS, "remove-relationship");

	public static final String STATE_ATTR_NAME_MODEL = "model";
	public static final String STATE_ATTR_NAME_REF = "ref";
	public static final String STATE_ATTR_NAME_STAGE_REF = "ref";
	public static final String STATE_ATTR_NAME_PROTOTYPE_REF = "prototype-ref";
	public static final String STATE_ATTR_NAME_ID = "id";
	public static final String STATE_ATTR_NAME_BINDING = "binding";
	public static final String STATE_ATTR_NAME_REL_SRC = "src";
	public static final String STATE_ATTR_NAME_REL_DST = "dst";
	public static final String STATE_ATTR_NAME_REL_TYPE = "type";
	public static final String STATE_ATTR_NAME_LOG_FORWARD_RESULT = "forward";
	public static final String STATE_ATTR_NAME_LOG_ROLLBACK_RESULT = "rollback";
	public static final String STATE_ATTR_NAME_LOG_ROLE = "role";
	public static final String STATE_ATTR_NAME_LOG_OPERATION_TYPE = "operation";
	public static final String STATE_ATTR_NAME_LOG_REL_TYPE = "type";
	public static final String STATE_ATTR_NAME_LOG_MUTATION_REL_SRC = "src";
	public static final String STATE_ATTR_NAME_LOG_MUTATION_REL_DST = "dst";
}