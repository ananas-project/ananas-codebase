package ananas.lib.blueprint2.impl;

import java.util.Hashtable;
import java.util.Map;

import ananas.lib.blueprint2.BlueprintException;
import ananas.lib.blueprint2.dom.helper.IImplementation;
import ananas.lib.blueprint2.dom.helper.INamespace;
import ananas.lib.blueprint2.dom.helper.INamespaceLoader;
import ananas.lib.blueprint2.dom.helper.INamespaceRegistrar;

final class ImplNamespaceRegistrar implements INamespaceRegistrar {

	private INamespace mDefaultNS;
	private final IImplementation mImpl;

	private final Map<String, INamespace> mTable;

	public ImplNamespaceRegistrar(IImplementation impl) {
		this.mImpl = impl;
		this.mTable = new Hashtable<String, INamespace>();
	}

	@Override
	public IImplementation getImplementation() {
		return this.mImpl;
	}

	@Override
	public INamespace getNamespace(String nsURI) {
		INamespace ns = this.mTable.get(nsURI);
		if (ns == null) {
			ns = this.mDefaultNS;
		}
		return ns;
	}

	@Override
	public INamespace loadNamespace(INamespaceLoader loader) {
		INamespace ns = loader.load(this.getImplementation());
		String uri = ns.getNamespaceURI();
		this.mTable.put(uri, ns);
		return ns;
	}

	@Override
	public INamespace loadNamespace(Class<?> loaderClass) {

		// TODO:cache class here

		try {
			INamespaceLoader loader = (INamespaceLoader) loaderClass
					.newInstance();
			return this.loadNamespace(loader);
		} catch (Exception e) {
			throw new BlueprintException(e);
		}
	}

	@Override
	public INamespace loadNamespace(String loaderClassPath) {
		try {
			Class<?> cls = Class.forName(loaderClassPath);
			return this.loadNamespace(cls);
		} catch (Exception e) {
			throw new BlueprintException(e);
		}
	}

	@Override
	public void setDefaultNamespace(INamespace ns) {
		this.mDefaultNS = ns;
	}

	@Override
	public INamespace getDefaultNamespace() {
		return this.mDefaultNS;
	}

	@Override
	public void registerNamespace(INamespace ns) {
		this.mTable.put(ns.getNamespaceURI(), ns);
	}

}
