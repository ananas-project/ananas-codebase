package ananas.lib.blueprint2.dom.helper;

public interface IClassRegistrar {

	IImplementation getImplementation();

	/**
	 * find
	 * */

	IClass findClass(String uri, String localName);

	IClass findClass(Class<?> aClass);

	IClass findClass(Object obj);

	/**
	 * register
	 * */

	IClass registerClass(String uri, String localName, Class<?> wrapperClass,
			Class<?> targetClass);

	IClass registerClass(String uri, String localName, String wrapperClass,
			String targetClass);

	/**
	 * default
	 * */
	IClass getDefaultClass();

	void setDefaultClass(IClass defaultClass);
}
