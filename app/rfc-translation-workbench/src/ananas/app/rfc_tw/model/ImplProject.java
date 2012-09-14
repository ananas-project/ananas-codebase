package ananas.app.rfc_tw.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import ananas.app.rfc_tw.event.DefaultEvent;
import ananas.app.rfc_tw.event.DefaultEventDispatcher;
import ananas.app.rfc_tw.event.IEventDispatcher;
import ananas.app.rfc_tw.event.IEventListener;
import ananas.app.rfc_tw.model.bpr.BprRFCTW;
import ananas.app.rfc_tw.model.bpr.NamespaceLoader;
import ananas.lib.blueprint.Blueprint;
import ananas.lib.blueprint.IDocument;

class ImplProject implements IProject {

	private final IEventDispatcher mOriginalTextEventDisp;
	private BprRFCTW mRoot = new BprRFCTW();

	public ImplProject() {
		this.mOriginalTextEventDisp = new DefaultEventDispatcher();
	}

	@Override
	public String getOriginalText() {
		return this.mRoot.getOriginalText();
	}

	@Override
	public void setOriginalText(String text) {
		this.mRoot.setOriginalText(text);
		this.mOriginalTextEventDisp.dispacheEvent(new DefaultEvent());
	}

	@Override
	public void addOriginalTextListener(IEventListener listener) {
		this.mOriginalTextEventDisp.addListener(listener);
	}

	@Override
	public void removeOriginalTextListener(IEventListener listener) {
		this.mOriginalTextEventDisp.removeListener(listener);
	}

	@Override
	public IDictionary getDictionary() {
		return this.mRoot.getDict();
	}

	@Override
	public void load(InputStream is) throws IOException {
		this._load(is);
	}

	private void _load(InputStream is) throws IOException {
		Class<NamespaceLoader> ns = ananas.app.rfc_tw.model.bpr.NamespaceLoader.class;
		Blueprint.getInstance().getImplementation().getNamespaceRegistrar()
				.loadNamespace(ns);
		IDocument doc = Blueprint.getInstance().loadDocument(is, null);
		BprRFCTW root = (BprRFCTW) doc.getRootElement().getTarget();
		System.out.println("loaded:" + root.toString());
		this.mRoot = root;
	}

	@Override
	public void save(OutputStream os) throws IOException {
		this._save(os);
	}

	@Override
	public void load(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		this._load(is);
	}

	@Override
	public void save(File file) throws IOException {
		file.getParentFile().mkdirs();
		file.createNewFile();
		OutputStream os = new FileOutputStream(file);
		this._save(os);
		os.close();
	}

	private void _save(OutputStream os) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
		this.mRoot.save(osw);
		osw.flush();
	}

}
