package com.tcoffman.ttwb.model.persistance.xml;

import javax.xml.namespace.QName;

final class XmlConstants {
	public static final String MODEL_NS = "urn:com.tcoffman.ttwb.model/v/1.0";
	public static final QName MODEL_ELEMENT_QNAME = new QName(MODEL_NS, "model");

	public static final QName MODEL_ELEMENT_QNAME_NAME = new QName(MODEL_NS, "name");
	public static final QName MODEL_ELEMENT_QNAME_STAGE = new QName(MODEL_NS, "stage");
	public static final QName MODEL_ELEMENT_QNAME_INITIAL_STAGE = new QName(MODEL_NS, "initial-stage");
	public static final QName MODEL_ELEMENT_QNAME_RULE = new QName(MODEL_NS, "rule");
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

	public static final String MODEL_ATTR_NAME_IS_TERMINAL = "terminal";
	public static final String MODEL_ATTR_NAME_RESULT = "to-stage";
	public static final String MODEL_ATTR_NAME_ID = "id";
	public static final String MODEL_ATTR_NAME_TYPE = "type";
	public static final String MODEL_ATTR_NAME_REF = "ref";
	public static final String MODEL_ATTR_NAME_PROTOTYPE_REF = "prototype-ref";

}