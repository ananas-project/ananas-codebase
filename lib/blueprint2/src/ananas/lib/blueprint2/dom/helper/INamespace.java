package ananas.lib.blueprint2.dom.helper;

public interface INamespace {

	IImplementation getImplementation();

	String getNamespaceURI();

	String getDefaultPrefix();

	IClassRegistrar getElementClassRegistrar();

	IClassRegistrar getAttributeClassRegistrar();

}
