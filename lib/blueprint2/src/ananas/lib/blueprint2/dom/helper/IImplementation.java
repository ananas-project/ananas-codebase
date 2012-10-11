package ananas.lib.blueprint2.dom.helper;

import ananas.lib.blueprint2.dom.IDocument;

public interface IImplementation {

	IClassRegistrar getClassRegistrar();

	INamespaceRegistrar getNamespaceRegistrar();

	IDocument newDocument(String docURI);

	INamespace newNamespace(String nsURI, String defaultPrefix);

}
