package ananas.lib.blueprint2.dom.helper;

public interface IClass {

	INamespace getNamespace();

	String getNamespaceURI();

	String getLocalName();

	Class<?> getWrapperClass();

	Class<?> getTargetClass();

}
