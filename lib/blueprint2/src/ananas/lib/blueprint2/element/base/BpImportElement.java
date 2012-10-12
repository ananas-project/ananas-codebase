package ananas.lib.blueprint2.element.base;

import ananas.lib.blueprint2.BlueprintException;
import ananas.lib.blueprint2.dom.IAttr;
import ananas.lib.blueprint2.dom.helper.IImplementation;
import ananas.lib.blueprint2.dom.helper.INamespace;
import ananas.lib.blueprint2.dom.helper.INamespaceLoader;

public class BpImportElement extends BaseElement {

	private String type;
	private String value;

	@Override
	public boolean setAttribute(IAttr attr) {
		String lname = attr.getBlueprintClass().getLocalName();
		if (lname == null) {
			return false;

		} else if (lname.equals("type")) {
			this.type = attr.getValue();

		} else if (lname.equals("value")) {
			this.value = attr.getValue();

		} else {
			return super.setAttribute(attr);
		}
		return true;
	}

	public static final String type_ns_loader = "class:NamespaceLoader";
	public static final String type_class_loader = "class:*";

	@Override
	public void tagEnd() {
		super.tagEnd();
		if (type_ns_loader.equals(type)) {
			this._loadNsLoader(this.value);
		}
	}

	private void _loadNsLoader(String className) {
		try {
			Class<?> cls = Class.forName(className);
			INamespaceLoader ldr = (INamespaceLoader) cls.newInstance();
			IImplementation impl = this.getOwnerDocument().getImplementation();
			INamespace ns = ldr.load(impl);
			impl.getNamespaceRegistrar().registerNamespace(ns);
		} catch (Exception e) {
			throw new BlueprintException(e);
		}
	}
}
