package ananas.lib.blueprint2.impl;

import java.util.HashMap;
import java.util.Map;

import ananas.lib.blueprint2.BlueprintException;
import ananas.lib.blueprint2.dom.helper.IClass;
import ananas.lib.blueprint2.dom.helper.IClassRegistrar;
import ananas.lib.blueprint2.dom.helper.IImplementation;

final class ImplClassRegistrar implements IClassRegistrar {

	private IClass mDefaultClass;
	private final IImplementation mImpl;

	private final Map<Object, IClass> mTable;

	public ImplClassRegistrar(IImplementation impl) {
		this.mImpl = impl;
		this.mTable = new HashMap<Object, IClass>();
	}

	@Override
	public IImplementation getImplementation() {
		return this.mImpl;
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
	public IClass registerClass(String uri, String localName,
			Class<?> wrapperClass, Class<?> targetClass) {

		IClass cls = new ImplClass(uri, localName, wrapperClass, targetClass);
		this.mTable.put(localName, cls);
		this.mTable.put(wrapperClass, cls);
		this.mTable.put(targetClass, cls);
		return cls;
	}

	@Override
	public IClass registerClass(String uri, String localName,
			String wrapperClass, String targetClass) {

		Class<?> clsWrapper = this._safe_forName(wrapperClass);
		Class<?> clsTarget = this._safe_forName(targetClass);
		return this.registerClass(uri, localName, clsWrapper, clsTarget);
	}

	private Class<?> _safe_forName(String className) {
		try {
			Class<?> cls = Class.forName(className);
			return cls;
		} catch (ClassNotFoundException e) {
			throw new BlueprintException(e);
		}
	}

	@Override
	public IClass getDefaultClass() {
		return this.mDefaultClass;
	}

	@Override
	public void setDefaultClass(IClass defaultClass) {
		this.mDefaultClass = defaultClass;
	}

}
