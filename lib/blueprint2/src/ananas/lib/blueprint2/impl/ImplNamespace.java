package ananas.lib.blueprint2.impl;

import ananas.lib.blueprint2.dom.helper.IClassRegistrar;
import ananas.lib.blueprint2.dom.helper.IImplementation;
import ananas.lib.blueprint2.dom.helper.INamespace;

final class ImplNamespace implements INamespace {

	private final String mDefPrefix;
	private final String mURI;
	private final IImplementation mImpl;
	private final IClassRegistrar mElementClassReg;
	private final IClassRegistrar mAttrClassReg;

	public ImplNamespace(String nsURI, String defaultPrefix,
			IImplementation impl) {
		this.mURI = nsURI;
		this.mDefPrefix = defaultPrefix;
		this.mImpl = impl;
		this.mElementClassReg = new ImplClassRegistrar(impl);
		this.mAttrClassReg = new ImplClassRegistrar(impl);
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

	@Override
	public IClassRegistrar getElementClassRegistrar() {
		return this.mElementClassReg;
	}

	@Override
	public IClassRegistrar getAttributeClassRegistrar() {
		return this.mAttrClassReg;
	}

}
