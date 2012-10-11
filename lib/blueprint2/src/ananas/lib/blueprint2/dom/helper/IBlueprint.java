package ananas.lib.blueprint2.dom.helper;

import java.io.IOException;
import java.io.InputStream;

import ananas.lib.blueprint2.dom.IDocument;

public interface IBlueprint extends IBlueprintContext {

	IDocument loadDocument(String docURI) throws IOException;

	IDocument loadDocument(InputStream is, String docURI) throws IOException;

	void saveDocument(IDocument doc) throws IOException;

	IDocument saveAsDocument(String docURI, Object obj) throws IOException;

}
