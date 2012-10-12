package ananas.lib.blueprint2.element.base;

import ananas.lib.blueprint2.dom.helper.IImplementation;
import ananas.lib.blueprint2.dom.helper.INamespace;
import ananas.lib.blueprint2.dom.helper.INamespaceLoader;

public class NamespaceLoader implements INamespaceLoader {

	class MyHelper {

		private final INamespace mNS;
		private final String mURI;

		public MyHelper(INamespace ns) {
			this.mNS = ns;
			this.mURI = ns.getNamespaceURI();
		}

		public void reg(String localName, Class<?> classWrapper,
				Class<?> classTarget) {

			this.mNS.registerClass(this.mURI, localName, classWrapper,
					classTarget);
		}
	}

	@Override
	public INamespace load(IImplementation impl) {
		String uri = "xmlns:ananas:blueprint:base";
		String defaultPrefix = "bp";
		INamespace ns = impl.newNamespace(uri, defaultPrefix);
		MyHelper h = new MyHelper(ns);
		{
			// attribute
			h.reg("id", Object.class, Object.class);
			h.reg("xmlns", Object.class, Object.class);
		}
		{
			// element
			h.reg("blueprint", CBlueprintElement.class, CBlueprint.class);
			h.reg("import", CImportElement.class, CImport.class);
			h.reg("link", CLinkElement.class, CLink.class);
			h.reg("head", CHeadElement.class, CHead.class);
			h.reg("body", CBodyElement.class, CBody.class);
		}
		return ns;
	}

}
