package ananas.lib.blueprint2.impl;

import java.util.HashMap;
import java.util.Map;

import ananas.lib.blueprint2.BlueprintException;
import ananas.lib.blueprint2.dom.IAttr;
import ananas.lib.blueprint2.dom.IDocument;
import ananas.lib.blueprint2.dom.IElement;
import ananas.lib.blueprint2.dom.IText;
import ananas.lib.blueprint2.dom.helper.IClass;
import ananas.lib.blueprint2.dom.helper.IClassRegistrar;
import ananas.lib.blueprint2.dom.helper.IImplementation;
import ananas.lib.blueprint2.dom.helper.INamespace;

final class ImplDocument implements IDocument {

	private final String mDocURI;
	private final IImplementation mImpl;
	private final Map<String, IElement> mElementTable;
	private IElement mRoot;

	public ImplDocument(String docURI, IImplementation impl) {
		this.mDocURI = docURI;
		this.mImpl = impl;
		this.mElementTable = new HashMap<String, IElement>();
	}

	@Override
	public boolean bindBlueprintClass(IClass aClass) {
		return false;
	}

	@Override
	public IClass getBlueprintClass() {
		return null;
	}

	@Override
	public IElement findElementById(String id) {
		return this.mElementTable.get(id);
	}

	@Override
	public Object findTargetById(String id) {
		IElement element = this.findElementById(id);
		if (element == null)
			return null;
		return element.getTarget();
	}

	@Override
	public void registerElement(IElement element) {
		String id = element.getId();
		if (id != null) {
			this.mElementTable.put(id, element);
		}
	}

	@Override
	public IElement getRootElement() {
		return this.mRoot;
	}

	@Override
	public void setRootElement(IElement root) {
		this.mRoot = root;
	}

	@Override
	public IImplementation getImplementation() {
		return this.mImpl;
	}

	@Override
	public String getDocumentURI() {
		return this.mDocURI;
	}

	@Override
	public IAttr createAttribute(String uri, String localName) {
		try {
			INamespace ns = this.mImpl.getNamespaceRegistrar()
					.getNamespace(uri);
			if (ns == null)
				throw new BlueprintException("no namespace");
			IClassRegistrar ar = ns;
			IClass cls = ar.findClass(null, localName);
			if (cls == null)
				throw new BlueprintException("no class");
			IAttr attr = (IAttr) this._safe_newInstance(cls.getWrapperClass());
			attr.bindBlueprintClass(cls);
					return attr;
		} catch (Exception e) {
			System.err.println("cannot create attribute:");
			System.err.println("    " + "      uri = " + uri);
			System.err.println("    " + "localName = " + localName);
			throw new BlueprintException(e);
		}
	}

	private Object _safe_newInstance(Class<?> aClass) {
		try {
			return aClass.newInstance();
		} catch (Exception e) {
			throw new BlueprintException(e);
		}
	}

	@Override
	public IElement createElement(String uri, String localName) {
		try {
			INamespace ns = this.mImpl.getNamespaceRegistrar()
					.getNamespace(uri);
			IClassRegistrar er = ns;
			if (ns == null)
				throw new BlueprintException("no namespace");
			IClass cls = er.findClass(null, localName);
			if (cls == null)
				throw new BlueprintException("no class");
			IElement element = (IElement) this._safe_newInstance(cls
					.getWrapperClass());
			element.bindOwnerDocument(this);
			element.bindBlueprintClass(cls);
			return element;
		} catch (Exception e) {
			System.err.println("cannot create element:");
			System.err.println("    " + "      uri = " + uri);
			System.err.println("    " + "localName = " + localName);
			throw new BlueprintException(e);
		}
	}

	@Override
	public IElement createElement(Object target) {
		try {
			IClass cls = this.mImpl.findClass(target);
			if (cls == null)
				throw new BlueprintException("no class");
			IElement element = (IElement) this._safe_newInstance(cls
					.getWrapperClass());
			element.bindOwnerDocument(this);
			element.bindBlueprintClass(cls);
			element.bindTarget(target);
			return element;
		} catch (Exception e) {
			System.err.println("cannot create element for target:");
			System.err.println("    " + "target = "
					+ target.getClass().getName());
			throw new BlueprintException(e);
		}
	}

	@Override
	public IText createText(String data) {
		return new ImplText(data);
	}

}
