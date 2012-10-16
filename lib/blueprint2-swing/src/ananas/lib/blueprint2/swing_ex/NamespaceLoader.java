package ananas.lib.blueprint2.swing_ex;

import ananas.lib.blueprint2.dom.helper.IImplementation;
import ananas.lib.blueprint2.dom.helper.INamespace;
import ananas.lib.blueprint2.dom.helper.INamespaceLoader;
import ananas.lib.blueprint2.element.base.BaseAttr;
import ananas.lib.blueprint2.swing.Swing_position;
import ananas.lib.blueprint2.swing.Swing_positionWrapper;

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
		String uri = "xmlns:ananas:blueprint:swing_ex";
		String defaultPrefix = "swingex";
		INamespace ns = impl.newNamespace(uri, defaultPrefix);
		MyHelper h = new MyHelper(ns);
		{
			// attribute

			h.reg("basepath", BaseAttr.class, String.class);
			// h.reg("id", BaseAttr.class, String.class);
			// h.reg("label", BaseAttr.class, String.class);
			// h.reg("title", BaseAttr.class, String.class);
			// h.reg("text", BaseAttr.class, String.class);
			// h.reg("x", BaseAttr.class, Integer.class);
			// h.reg("y", BaseAttr.class, Integer.class);
			// h.reg("width", BaseAttr.class, Integer.class);
			// h.reg("height", BaseAttr.class, Integer.class);
			// h.reg("value", BaseAttr.class, String.class);
		}
		{
			// element

			h.reg("position", Swing_positionWrapper.class, Swing_position.class);

			h.reg("JDirectoryTree", JDirectoryTreeWrapper.class,
					JDirectoryTree.class);
		}
		return ns;
	}

}
