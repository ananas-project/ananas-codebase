package ananas.lib.blueprint2.impl;

import ananas.lib.blueprint2.BlueprintException;
import ananas.lib.blueprint2.dom.helper.IClass;
import ananas.lib.blueprint2.dom.helper.IClassRegistrar;
import ananas.lib.blueprint2.dom.helper.IImplementation;
import ananas.lib.blueprint2.dom.helper.INamespace;

final class ImplNamespace implements INamespace {

	private final String mDefPrefix;
	private final String mURI;
	private final IImplementation mImpl;
	private final IClassRegistrar mClassReg;

	public ImplNamespace(String nsURI, String defaultPrefix,
			IImplementation impl) {
		this.mURI = nsURI;
		this.mDefPrefix = defaultPrefix;
		this.mImpl = impl;
		this.mClassReg = new ImplClassRegistrar();
	}

	@Override
	public String getNamespaceURI() {
		return this.mURI;
	}

	@Override
	public String getDefaultPrefix() {
		return this.mDefPrefix;
	}

	@Override
	public IImplementation getImplementation() {
		return this.mImpl;
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
	public IClass registerClass(String uri, String localName,
			Class<?> wrapperClass, Class<?> targetClass) {

		IClass cls = new ImplClass(uri, localName, wrapperClass, targetClass,
				this);
		this.registerClass(cls);
		return cls;
	}

	@Override
	public IClass registerClass(String uri, String localName,
			String wrapperClass, String targetClass) {

		Class<?> clsWrapper = this._safe_forName(wrapperClass);
		Class<?> clsTarget = this._safe_forName(targetClass);
		return this.registerClass(uri, localName, clsWrapper, clsTarget);
	}

	@Override
	public IClass findClass(String uri, String localName) {
		return this.mClassReg.findClass(uri, localName);
	}

	@Override
	public IClass findClass(Class<?> aClass) {
		return this.mClassReg.findClass(aClass);
	}

	@Override
	public IClass findClass(Object obj) {
		return this.mClassReg.findClass(obj);
	}

	@Override
	public void registerClass(IClass aClass) {
		this.mClassReg.registerClass(aClass);
	}

	@Override
	public IClass getDefaultClass() {
		return this.mClassReg.getDefaultClass();
	}

	@Override
	public void setDefaultClass(IClass defaultClass) {
		this.mClassReg.setDefaultClass(defaultClass);
	}
}
