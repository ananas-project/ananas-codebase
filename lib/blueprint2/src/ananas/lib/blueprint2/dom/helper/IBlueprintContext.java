package ananas.lib.blueprint2.dom.helper;

import ananas.lib.io.IConnector;

public interface IBlueprintContext {

	IConnector getConnector();

	IImplementation getImplementation();

	IDocumentBuilderFactory getDocumentBuilderFactory();

	IDocumentSerializerFactory getDocumentSerializerFactory();

}
