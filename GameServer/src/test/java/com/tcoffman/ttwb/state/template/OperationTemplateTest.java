package com.tcoffman.ttwb.state.template;

import org.junit.Test;

import com.tcoffman.ttwb.component.GameComponentBuilderException;
import com.tcoffman.ttwb.component.GameComponentRef;
import com.tcoffman.ttwb.doc.GameComponentDocumentation;
import com.tcoffman.ttwb.doc.StandardComponentDocumentation;
import com.tcoffman.ttwb.model.pattern.operation.GameOperationPattern;
import com.tcoffman.ttwb.model.pattern.operation.StandardGameOperationPattern;
import com.tcoffman.ttwb.state.mutation.GameOperation;

public class OperationTemplateTest {

	private GameComponentRef<GameComponentDocumentation> doc(String desc) throws GameComponentBuilderException {
		return GameComponentRef.wrap(StandardComponentDocumentation.create().setDescription(desc).done());
	}

	@Test
	public void testSignalTemplate() throws GameComponentBuilderException {
		final GameOperationPattern opPattern = StandardGameOperationPattern.create().setDocumentation(doc("opPattern1")).setType(GameOperation.Type.SIGNAL)
				.done();
		new OperationTemplate(opPattern);
	}

	@Test
	public void testMoveTemplate() throws GameComponentBuilderException {
		final GameOperationPattern opPattern = StandardGameOperationPattern.create().setDocumentation(doc("opPattern1")).setType(GameOperation.Type.MOVE)
				.done();
		new OperationTemplate(opPattern);
	}

}
