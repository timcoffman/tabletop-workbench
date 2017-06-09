package com.tcoffman.ttwb.model.persistance.xml;

import javax.xml.namespace.QName;

public final class XmlConstants {
	public static final String MODEL_NS = "urn:com.tcoffman.ttwb.model/v/1.0";
	public static final QName MODEL_ELEMENT_QNAME = new QName(MODEL_NS, "model");

	public static final QName MODEL_ELEMENT_QNAME_NAME = new QName(MODEL_NS, "name");
	public static final QName MODEL_ELEMENT_QNAME_STAGE = new QName(MODEL_NS, "stage");
	public static final QName MODEL_ELEMENT_QNAME_INITIAL_STAGE = new QName(MODEL_NS, "initial-stage");
	public static final QName MODEL_ELEMENT_QNAME_RULE = new QName(MODEL_NS, "rule");
	public static final QName MODEL_ELEMENT_QNAME_IMPORT = new QName(MODEL_NS, "import");
	public static final QName MODEL_ELEMENT_QNAME_ROLE = new QName(MODEL_NS, "role");
	public static final QName MODEL_ELEMENT_QNAME_PROTOTYPE = new QName(MODEL_NS, "prototype");
	public static final QName MODEL_ELEMENT_QNAME_PARTS = new QName(MODEL_NS, "parts");
	public static final QName MODEL_ELEMENT_QNAME_PART = new QName(MODEL_NS, "part");
	public static final QName MODEL_ELEMENT_QNAME_PLACE = new QName(MODEL_NS, "place");
	public static final QName MODEL_ELEMENT_QNAME_OP_SIGNAL = new QName(MODEL_NS, "signal");
	public static final QName MODEL_ELEMENT_QNAME_OP_MOVE = new QName(MODEL_NS, "move");
	public static final QName MODEL_ELEMENT_QNAME_OP_ORIENT = new QName(MODEL_NS, "orient");
	public static final QName MODEL_ELEMENT_QNAME_OP_JOIN = new QName(MODEL_NS, "join");
	public static final QName MODEL_ELEMENT_QNAME_OP_SPLIT = new QName(MODEL_NS, "split");

	public static final QName MODEL_ELEMENT_QNAME_PATTERN_ROLE = new QName(MODEL_NS, "role");
	public static final QName MODEL_ELEMENT_QNAME_PATTERN_SUBJECT = new QName(MODEL_NS, "subject");
	public static final QName MODEL_ELEMENT_QNAME_PATTERN_TARGET = new QName(MODEL_NS, "target");
	public static final QName MODEL_ELEMENT_QNAME_PATTERN_QUANTITY = new QName(MODEL_NS, "quantity");
	public static final QName MODEL_ELEMENT_QNAME_PATTERN_FILTER = new QName(MODEL_NS, "filter");
	public static final QName MODEL_ELEMENT_QNAME_PATTERN_RELATIONHIP = new QName(MODEL_NS, "rel");
	public static final QName MODEL_ELEMENT_QNAME_PATTERN_INTERSECTION = new QName(MODEL_NS, "all");
	public static final QName MODEL_ELEMENT_QNAME_PATTERN_INVERSION = new QName(MODEL_NS, "not");
	public static final QName MODEL_ELEMENT_QNAME_PATTERN_ANY = new QName(MODEL_NS, "any");
	public static final QName MODEL_ELEMENT_QNAME_PATTERN_ROOT = new QName(MODEL_NS, "root");
	public static final QName MODEL_ELEMENT_QNAME_PATTERN_VARIABLE = new QName(MODEL_NS, "var");
	public static final QName MODEL_ELEMENT_QNAME_PATTERN_PART = new QName(MODEL_NS, "part");
	public static final QName MODEL_ELEMENT_QNAME_PATTERN_RELATED = new QName(MODEL_NS, "related");

	public static final String MODEL_ATTR_NAME_MODEL = "model";
	public static final String MODEL_ATTR_NAME_IS_TERMINAL = "terminal";
	public static final String MODEL_ATTR_NAME_RESULT = "to-stage";
	public static final String MODEL_ATTR_NAME_ID = "id";
	public static final String MODEL_ATTR_NAME_ABSTRACT = "abstract";
	public static final String MODEL_ATTR_NAME_PATTERN_TOKEN = "token";
	public static final String MODEL_ATTR_NAME_DOC = "doc";
	public static final String MODEL_ATTR_NAME_TYPE = "type";
	public static final String MODEL_ATTR_NAME_REF = "ref";
	public static final String MODEL_ATTR_NAME_STAGE_REF = "ref";
	public static final String MODEL_ATTR_NAME_PROTOTYPE_REF = "prototype-ref";
	public static final String MODEL_ATTR_NAME_EXTENDS = "extends";
	public static final String MODEL_ATTR_NAME_BINDING = "binding";
}