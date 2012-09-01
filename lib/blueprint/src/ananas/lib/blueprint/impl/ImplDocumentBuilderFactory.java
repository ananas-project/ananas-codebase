package ananas.lib.blueprint.impl;

import ananas.lib.blueprint.IBlueprintContext;
import ananas.lib.blueprint.IDocumentBuilder;
import ananas.lib.blueprint.IDocumentBuilderFactory;
import ananas.lib.blueprint.INamespaceRegistrar;

class ImplDocumentBuilderFactory implements IDocumentBuilderFactory {

	@Override
	public IDocumentBuilder createDocumentBuilder(IBlueprintContext context) {

		INamespaceRegistrar reg = context.getImplementation()
				.getNamespaceRegistrar();

		reg.loadNamespace(ananas.lib.blueprint.elements.base.NamespaceLoader.class);
		reg.loadNamespace(ananas.lib.blueprint.elements.reflect.NamespaceLoader.class);

		return new ImplDocumentBuilder(context);
	}

}
