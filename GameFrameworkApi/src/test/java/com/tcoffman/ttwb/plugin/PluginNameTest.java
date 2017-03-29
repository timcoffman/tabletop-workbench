package com.tcoffman.ttwb.plugin;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PluginNameTest {

	@Test
	public void canIdentifyIdenticalNames() {
		assertThat(new PluginName("a.b.c", "1.0"), equalTo(new PluginName("a.b.c", "1.0")));
	}

	@Test
	public void canIdentifyNamesThatAreDifferentByQualifiedName() {
		assertThat(new PluginName("a.b.c", "1.0"), not(equalTo(new PluginName("a.b.x", "1.0"))));
	}

	@Test
	public void canIdentifyNamesThatAreDifferentByVersion() {
		assertThat(new PluginName("a.b.c", "1.0"), not(equalTo(new PluginName("a.b.c", "1.1"))));
	}

}
