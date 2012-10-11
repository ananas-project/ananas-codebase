package ananas.lib.blueprint2.dom.helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import ananas.lib.blueprint2.dom.IDocument;

public interface IDocumentBuilder {

	IDocument build(InputStream is, String docURI) throws IOException;

	IDocument build(File file) throws IOException;

	IDocument build(String docURI) throws IOException;

	IBlueprintContext getBlueprintContext();

}
