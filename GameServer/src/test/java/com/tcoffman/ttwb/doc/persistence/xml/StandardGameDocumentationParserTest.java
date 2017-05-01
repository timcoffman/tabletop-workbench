package com.tcoffman.ttwb.doc.persistence.xml;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.junit.Before;
import org.junit.Test;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.persistence.xml.BundleHelper;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.doc.StandardModelDocumentation;
import com.tcoffman.ttwb.plugin.PluginException;

public class StandardGameDocumentationParserTest {

	private StandardGameDocumentationParser m_standardGameDocumentationParser;

	@Before
	public void setup() throws PluginException {

		m_standardGameDocumentationParser = new StandardGameDocumentationParser();
	}

	@Test
	public void canParseCompleteDocs() throws GameComponentBuilderException, XMLStreamException, TransformerException {
		final StandardModelDocumentation docs = m_standardGameDocumentationParser.parse(BundleHelper.getResourceAsStream("complete-model/lang/en-us/doc.xml"),
				"complete-model-en-us");
		assertThat(docs, notNullValue());

		assertThat(docs.prototypes().collect(Collectors.counting()), equalTo(5L));

		m_standardGameDocumentationParser.write(docs, System.out, "complete-model");
	}

	@Test
	public void canParseMinimalDocs() throws GameComponentBuilderException, XMLStreamException {
		final StandardModelDocumentation docs = m_standardGameDocumentationParser.parse(BundleHelper.getResourceAsStream("minimal-model/lang/en-us/doc.xml"),
				"minimal-model-en-us");
		assertThat(docs, notNullValue());

	}

	@Test
	public void canWriteMinimalDocs() throws TransformerException, PluginException, UnsupportedEncodingException {
		final StandardModelDocumentation.Editor editor = StandardModelDocumentation.create();
		editor.createModel((d) -> {
			d.setName(GameComponentDocumentation.Format.SHORT, "minimal");
		});
		final StandardModelDocumentation docs = editor.done();

		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		m_standardGameDocumentationParser.write(docs, os, "minimal-model-en-us");
		final String xml = os.toString("UTF-8");
		assertThat(xml, containsString("minimal"));

	}
}
