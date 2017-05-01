package com.tcoffman.ttwb.doc;


public interface GameComponentDocumentation {

	public enum Format {
		SHORT, LONG
	};

	String getName(Format format);

	String getDescription();

}
