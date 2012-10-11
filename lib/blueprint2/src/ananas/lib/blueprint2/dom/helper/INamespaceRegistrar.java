package ananas.lib.blueprint2.dom.helper;

public interface INamespaceRegistrar {

	IImplementation getImplementation();

	INamespace getNamespace(String nsURI);

	void registerNamespace(INamespace ns);

	INamespace loadNamespace(INamespaceLoader loader);

	INamespace loadNamespace(Class<?> loaderClass);

	INamespace loadNamespace(String loaderClassPath);

	void setDefaultNamespace(INamespace ns);

	INamespace getDefaultNamespace();

}
