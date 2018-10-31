package com.tcoffman.ttwb.state.template;

import static com.tcoffman.ttwb.component.GameComponentRef.wrap;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.doc.StandardComponentDocumentation;
import com.tcoffman.ttwb.model.GameStage;
import com.tcoffman.ttwb.model.StandardGameRule;
import com.tcoffman.ttwb.state.mutation.GameOperation;

public class RuleTemplateTest {

	private GameComponentRef<GameComponentDocumentation> doc(String desc) throws GameComponentBuilderException {
		return wrap(StandardComponentDocumentation.create().setDescription(desc).done());
	}

	@Test
	public void testSimpleSignalTemplate() throws GameComponentBuilderException {
		StandardGameRule.create().createOperationPattern((e) -> {
			e.setType(GameOperation.Type.SIGNAL);
			e.setDocumentation(doc("abc"));
		}).setDocumentation(doc("xyz")).setResult(wrap(mock(GameStage.class))).done();
		new RuleTemplate();
	}

	@Test
	public void testSimpleMoveTemplate() throws GameComponentBuilderException {
		StandardGameRule.create().createOperationPattern((e) -> {
			e.setType(GameOperation.Type.MOVE);
			e.setDocumentation(doc("abc"));
		}).setDocumentation(doc("xyz")).setResult(wrap(mock(GameStage.class))).done();
		new RuleTemplate();
	}

	@Test
	public void testCompoundTemplate() throws GameComponentBuilderException {
		StandardGameRule.create().createOperationPattern((e) -> {
			e.setType(GameOperation.Type.MOVE);
			e.setDocumentation(doc("abc"));
		}).setDocumentation(doc("xyz")).setResult(wrap(mock(GameStage.class))).done();
		new RuleTemplate();
	}

}
