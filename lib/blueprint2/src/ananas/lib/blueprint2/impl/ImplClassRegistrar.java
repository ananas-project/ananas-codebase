package ananas.lib.blueprint2.impl;

import java.util.HashMap;
import java.util.Map;

import ananas.lib.blueprint2.dom.helper.IClass;
import ananas.lib.blueprint2.dom.helper.IClassRegistrar;

final class ImplClassRegistrar implements IClassRegistrar {

	private IClass mDefaultClass;
	private final Map<Object, IClass> mTable;

	public ImplClassRegistrar() {
		this.mTable = new HashMap<Object, IClass>();
	}

	@Override
	public IClass findClass(String uri, String localName) {
		return this.mTable.get(localName);
	}

	@Override
	public IClass findClass(Class<?> aClass) {
		return this.mTable.get(aClass);
	}

	@Override
	public IClass findClass(Object obj) {
		return this.mTable.get(obj.getClass());
	}

	@Override
	public IClass getDefaultClass() {
		return this.mDefaultClass;
	}

	@Override
	public void setDefaultClass(IClass defaultClass) {
		this.mDefaultClass = defaultClass;
	}

	@Override
	public void registerClass(IClass aClass) {
		IClass cls = aClass;
		String localName = cls.getLocalName();
		Class<?> wrapperClass = cls.getWrapperClass();
		Class<?> targetClass = cls.getTargetClass();
		this.mTable.put(localName, cls);
		this.mTable.put(wrapperClass, cls);
		this.mTable.put(targetClass, cls);
	}

}
