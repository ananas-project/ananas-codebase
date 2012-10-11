package ananas.lib.blueprint2.impl;

import ananas.lib.blueprint2.dom.helper.IClass;

final class ImplClass implements IClass {

	private final String mNsURI;
	private final String mLocalName;
	private final Class<?> mWrapperClass;
	private final Class<?> mTargetClass;

	public ImplClass(String uri, String localName, Class<?> wrapperClass,
			Class<?> targetClass) {

		this.mNsURI = uri;
		this.mLocalName = localName;
		this.mWrapperClass = wrapperClass;
		this.mTargetClass = targetClass;
	}

	@Override
	public String getNamespaceURI() {
		return this.mNsURI;
	}

	@Override
	public String getLocalName() {
		return this.mLocalName;
	}

	@Override
	public Class<?> getWrapperClass() {
		return this.mWrapperClass;
	}

	@Override
	public Class<?> getTargetClass() {
		return this.mTargetClass;
	}

}
