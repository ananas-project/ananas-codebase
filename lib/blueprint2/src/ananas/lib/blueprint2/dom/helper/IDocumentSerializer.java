package ananas.lib.blueprint2.dom.helper;

import java.io.File;
import java.io.OutputStream;

import ananas.lib.blueprint2.dom.IDocument;

public interface IDocumentSerializer {

	IBlueprintContext getBlueprintContext();

	void serialize(IDocument doc, OutputStream os, String docURI);

	void serialize(IDocument doc, File file);

	void serialize(IDocument doc, String docURI);

	IDocument serializeAsDocument(Object target, OutputStream os, String docURI);

	IDocument serializeAsDocument(Object target, File file);

	IDocument serializeAsDocument(Object target, String docURI);

}
