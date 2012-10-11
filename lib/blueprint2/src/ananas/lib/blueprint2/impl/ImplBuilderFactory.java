package ananas.lib.blueprint2.impl;

import ananas.lib.blueprint2.dom.helper.IBlueprintContext;
import ananas.lib.blueprint2.dom.helper.IDocumentBuilder;
import ananas.lib.blueprint2.dom.helper.IDocumentBuilderFactory;

final class ImplBuilderFactory implements IDocumentBuilderFactory {

	@Override
	public IDocumentBuilder createDocumentBuilder(IBlueprintContext context) {
		return new ImplBuilder(context);
	}

}
