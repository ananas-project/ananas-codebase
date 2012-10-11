package ananas.lib.blueprint2.impl;

import ananas.lib.blueprint2.dom.IDocument;
import ananas.lib.blueprint2.dom.helper.IClassRegistrar;
import ananas.lib.blueprint2.dom.helper.IImplementation;
import ananas.lib.blueprint2.dom.helper.INamespace;
import ananas.lib.blueprint2.dom.helper.INamespaceRegistrar;

final class ImplImplementation implements IImplementation {

	private final INamespaceRegistrar mNsReg;
	private IClassRegistrar mClassReg;

	public ImplImplementation() {
		this.mNsReg = new ImplNamespaceRegistrar(this);
		String classpath = "ananas.lib.blueprint2.element.base.NamespaceLoader";
		this.mNsReg.loadNamespace(classpath);
	}

	@Override
	public INamespaceRegistrar getNamespaceRegistrar() {
		return this.mNsReg;
	}

	@Override
	public IDocument newDocument(String docURI) {
		IDocument doc = new ImplDocument(docURI, this);
		return doc;
	}

	@Override
	public INamespace newNamespace(String nsURI, String defaultPrefix) {
		return new ImplNamespace(nsURI, defaultPrefix, this);
	}

	@Override
	public IClassRegistrar getClassRegistrar() {
		return this.mClassReg;
	}

}
