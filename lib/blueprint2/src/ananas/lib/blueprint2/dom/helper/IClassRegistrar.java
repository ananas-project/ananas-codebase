package ananas.lib.blueprint2.dom.helper;

public interface IClassRegistrar {

	/**
	 * find
	 * */

	IClass findClass(String uri, String localName);

	IClass findClass(Class<?> aClass);

	IClass findClass(Object obj);

	/**
	 * register
	 * */

	void registerClass(IClass aClass);

	/**
	 * default
	 * */
	IClass getDefaultClass();

	void setDefaultClass(IClass defaultClass);
}
