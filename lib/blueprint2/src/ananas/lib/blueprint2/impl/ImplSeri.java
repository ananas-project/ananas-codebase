package ananas.lib.blueprint2.impl;

import java.io.File;
import java.io.OutputStream;

import ananas.lib.blueprint2.dom.IDocument;
import ananas.lib.blueprint2.dom.helper.IBlueprintContext;
import ananas.lib.blueprint2.dom.helper.IDocumentSerializer;

final class ImplSeri implements IDocumentSerializer {

	public ImplSeri(IBlueprintContext context) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IBlueprintContext getBlueprintContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serialize(IDocument doc, OutputStream os, String docURI) {
		// TODO Auto-generated method stub

	}

	@Override
	public void serialize(IDocument doc, File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void serialize(IDocument doc, String docURI) {
		// TODO Auto-generated method stub

	}

	@Override
	public IDocument serializeAsDocument(Object target, OutputStream os,
			String docURI) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDocument serializeAsDocument(Object target, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDocument serializeAsDocument(Object target, String docURI) {
		// TODO Auto-generated method stub
		return null;
	}

}
