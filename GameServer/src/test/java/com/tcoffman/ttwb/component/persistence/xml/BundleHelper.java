package com.tcoffman.ttwb.component.persistence.xml;

import java.io.InputStream;

public final class BundleHelper {

	public static InputStream getResourceAsStream(String name) {
		return BundleHelper.class.getResourceAsStream(name);
	}

}
