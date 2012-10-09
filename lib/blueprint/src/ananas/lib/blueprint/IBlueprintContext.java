package ananas.lib.blueprint;

import ananas.lib.io.IConnector;

public interface IBlueprintContext {

	IConnector getConnector();

	IImplementation getImplementation();

	IDocumentBuilderFactory getDocumentBuilderFactory();

}
