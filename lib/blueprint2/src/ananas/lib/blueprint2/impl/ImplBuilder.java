package ananas.lib.blueprint2.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import ananas.lib.blueprint2.BlueprintException;
import ananas.lib.blueprint2.dom.IDocument;
import ananas.lib.blueprint2.dom.IElement;
import ananas.lib.blueprint2.dom.IText;
import ananas.lib.blueprint2.dom.helper.IBlueprintContext;
import ananas.lib.blueprint2.dom.helper.IDocumentBuilder;
import ananas.lib.io.IInputConnection;

final class ImplBuilder implements IDocumentBuilder {

	private final IBlueprintContext mContext;

	public ImplBuilder(IBlueprintContext context) {
		this.mContext = context;
	}

	@Override
	public IDocument build(InputStream is, String docURI) {
		return this._build(is, docURI);
	}

	private IDocument _build(InputStream is, String docURI) {
		try {
			IDocument doc = this.getBlueprintContext().getImplementation()
					.newDocument(docURI);
			SAXParserFactory pf = SAXParserFactory.newInstance();
			pf.setNamespaceAware(true);
			SAXParser parser = pf.newSAXParser();
			DefaultHandler h = new MyBuilder(doc);
			parser.parse(is, h);
			return doc;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public IDocument build(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		String uri = "file://" + file.getAbsolutePath();
		return this._build(is, uri);
	}

	@Override
	public IDocument build(String docURI) throws IOException {
		String uri = docURI;
		IInputConnection conn = (IInputConnection) this.getBlueprintContext()
				.getConnector().open(uri);
		InputStream is = conn.getInputStream();
		return this._build(is, uri);
	}

	@Override
	public IBlueprintContext getBlueprintContext() {
		return this.mContext;
	}

	private static class MyBuilder extends DefaultHandler {

		private final IDocument mDoc;
		private final Stack<IElement> mStack;

		public MyBuilder(IDocument doc) {
			this.mDoc = doc;
			this.mStack = new Stack<IElement>();
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			String data = new String(ch, start, length);
			IText text = this.mDoc.createText(data);
			IElement element = this._curElement();
			boolean rlt = element.appendChild(text);
			if (!rlt) {
				String msg = "the element do not accept the text";
				System.err.println(msg);
				System.err.println("    " + "element = " + element);
				System.err.println("    " + "   text = " + data);
				System.err.println(msg);
				throw new BlueprintException(msg);
			}
		}

		private IElement _curElement() {
			return this.mStack.peek();
		}

		@Override
		public void endDocument() throws SAXException {
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {

			final IElement child = this.mStack.pop();
			final IElement parent;
			if (!this.mStack.isEmpty()) {
				parent = this.mStack.peek();
			} else {
				parent = null;
			}
			if (parent == null) {
				this.mDoc.setRootElement(child);
				return;
			}
			boolean rlt = parent.appendChild(child);
			if (!rlt) {
				String msg = "the parent do not accept the child";
				System.err.println(msg);
				System.err.println("    " + "parent = " + parent);
				System.err.println("    " + " child = " + child);
				System.err.println(msg);
				throw new BlueprintException(msg);
			}

		}

		@Override
		public void endPrefixMapping(String prefix) throws SAXException {
			// TODO Auto-generated method stub
			super.endPrefixMapping(prefix);
		}

		@Override
		public void error(SAXParseException e) throws SAXException {
			// TODO Auto-generated method stub
			super.error(e);
		}

		@Override
		public void fatalError(SAXParseException e) throws SAXException {
			// TODO Auto-generated method stub
			super.fatalError(e);
		}

		@Override
		public void ignorableWhitespace(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			super.ignorableWhitespace(ch, start, length);
		}

		@Override
		public void setDocumentLocator(Locator locator) {
			// TODO Auto-generated method stub
			super.setDocumentLocator(locator);
		}

		@Override
		public void startDocument() throws SAXException {

		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {

			IElement element = this.mDoc.createElement(uri, localName);
			if (element == null) {
				String msg = "cannot create element";
				System.err.println(msg);
				System.err.println("    " + "    nsURI = " + uri);
				System.err.println("    " + "localName = " + localName);
				System.err.println(msg);
				throw new BlueprintException(msg);
			}

			this.mStack.push(element);

		}

		@Override
		public void startPrefixMapping(String prefix, String uri)
				throws SAXException {
			// TODO Auto-generated method stub
			super.startPrefixMapping(prefix, uri);
		}

		@Override
		public void warning(SAXParseException e) throws SAXException {
			// TODO Auto-generated method stub
			super.warning(e);
		}

	}

}
