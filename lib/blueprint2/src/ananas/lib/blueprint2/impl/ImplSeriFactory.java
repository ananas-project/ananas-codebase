package ananas.lib.blueprint2.impl;

import ananas.lib.blueprint2.dom.helper.IBlueprintContext;
import ananas.lib.blueprint2.dom.helper.IDocumentSerializer;
import ananas.lib.blueprint2.dom.helper.IDocumentSerializerFactory;

final class ImplSeriFactory implements IDocumentSerializerFactory {

	@Override
	public IDocumentSerializer createDocumentSerializer(
			IBlueprintContext context) {

		return new ImplSeri(context);
	}

}
