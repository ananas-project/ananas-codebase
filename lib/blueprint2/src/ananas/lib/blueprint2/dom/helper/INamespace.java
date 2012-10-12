package ananas.lib.blueprint2.dom.helper;

public interface INamespace extends IClassRegistrar {

	IImplementation getImplementation();

	String getNamespaceURI();

	String getDefaultPrefix();

	IClass registerClass(String uri, String localName, Class<?> wrapperClass,
			Class<?> targetClass);

	IClass registerClass(String uri, String localName, String wrapperClass,
			String targetClass);

}
