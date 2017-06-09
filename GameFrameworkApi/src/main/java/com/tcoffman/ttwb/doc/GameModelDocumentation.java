package com.tcoffman.ttwb.doc;

import java.util.stream.Stream;

public interface GameModelDocumentation {

	GameComponentDocumentation model();

	Stream<? extends GameComponentDocumentation> prototypes();

	Stream<? extends GameComponentDocumentation> stages();

	Stream<? extends GameComponentDocumentation> roles();

	Stream<? extends GameComponentDocumentation> rules();

	Stream<? extends GameComponentDocumentation> operations();

}
