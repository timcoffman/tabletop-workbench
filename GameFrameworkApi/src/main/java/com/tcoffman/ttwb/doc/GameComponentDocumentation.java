package com.tcoffman.ttwb.doc;

import com.tcoffman.ttwb.component.GameComponent;

public interface GameComponentDocumentation extends GameComponent {

	public enum Format {
		SHORT, LONG
	};

	String getName(Format format);

	String getDescription();

}
