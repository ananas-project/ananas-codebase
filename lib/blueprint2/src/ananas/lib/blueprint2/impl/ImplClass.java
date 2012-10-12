package ananas.lib.blueprint2.impl;

import ananas.lib.blueprint2.dom.helper.IClass;
import ananas.lib.blueprint2.dom.helper.INamespace;

final class ImplClass implements IClass {

	private final String mNsURI;
	private final String mLocalName;
	private final Class<?> mWrapperClass;
	private final Class<?> mTargetClass;
	private final INamespace mNS;

	public ImplClass(String uri, String localName, Class<?> wrapperClass,
			Class<?> targetClass, INamespace ns) {

		this.mNsURI = uri;
		this.mLocalName = localName;
		this.mWrapperClass = wrapperClass;
		this.mTargetClass = targetClass;
		this.mNS = ns;
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

	@Override
	public INamespace getNamespace() {
		return this.mNS;
	}

}
