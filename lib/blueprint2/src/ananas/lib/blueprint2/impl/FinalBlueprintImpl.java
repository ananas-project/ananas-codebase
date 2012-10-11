package ananas.lib.blueprint2.impl;

import java.io.IOException;
import java.io.InputStream;

import ananas.lib.blueprint2.Blueprint2;
import ananas.lib.blueprint2.dom.IDocument;
import ananas.lib.blueprint2.dom.helper.IDocumentBuilder;
import ananas.lib.blueprint2.dom.helper.IDocumentBuilderFactory;
import ananas.lib.blueprint2.dom.helper.IDocumentSerializer;
import ananas.lib.blueprint2.dom.helper.IDocumentSerializerFactory;
import ananas.lib.blueprint2.dom.helper.IImplementation;
import ananas.lib.io.DefaultConnector;
import ananas.lib.io.IConnector;

public class FinalBlueprintImpl extends Blueprint2 {

	private IConnector mConnector;
	private IImplementation mImpl;
	private IDocumentBuilderFactory mBuilderFactory;
	private IDocumentSerializerFactory mSeriFactory;

	private FinalBlueprintImpl() {
	}

	@Override
	public IDocument loadDocument(String docURI) throws IOException {
		IDocumentBuilder builder = this._getBuilder();
		return builder.build(docURI);
	}

	@Override
	public IDocument loadDocument(InputStream is, String docURI) throws IOException {
		IDocumentBuilder builder = this._getBuilder();
		return builder.build(is, docURI);
	}

	private IDocumentBuilder _getBuilder() {
		return this.getDocumentBuilderFactory().createDocumentBuilder(this);
	}

	@Override
	public IConnector getConnector() {
		if (this.mConnector == null) {
			this.mConnector = DefaultConnector.getDefault();
		}
		return this.mConnector;
	}

	@Override
	public IImplementation getImplementation() {
		if (this.mImpl == null) {
			this.mImpl = new ImplImplementation();
		}
		return this.mImpl;
	}

	@Override
	public IDocumentBuilderFactory getDocumentBuilderFactory() {
		if (this.mBuilderFactory == null) {
			this.mBuilderFactory = new ImplBuilderFactory();
		}
		return this.mBuilderFactory;
	}

	@Override
	public IDocument saveAsDocument(String docURI, Object obj) {
		IDocumentSerializer seri = this._getSeri();
		return seri.serializeAsDocument(obj, docURI);
	}

	@Override
	public void saveDocument(IDocument doc) {
		IDocumentSerializer seri = this._getSeri();
		seri.serialize(doc, doc.getDocumentURI());
	}

	private IDocumentSerializer _getSeri() {
		return this.getDocumentSerializerFactory().createDocumentSerializer(
				this);
	}

	@Override
	public IDocumentSerializerFactory getDocumentSerializerFactory() {
		if (this.mSeriFactory == null) {
			this.mSeriFactory = new ImplSeriFactory();
		}
		return this.mSeriFactory;
	}

	private static Blueprint2 sInst;

	public static synchronized Blueprint2 getInst() {
		if (sInst == null) {
			sInst = new FinalBlueprintImpl();
		}
		return sInst;
	}

}
