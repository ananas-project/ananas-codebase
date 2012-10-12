package ananas.lib.blueprint2.awt;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

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
		String uri = "xmlns:ananas:blueprint:awt";
		String defaultPrefix = "awt";
		INamespace ns = impl.newNamespace(uri, defaultPrefix);
		MyHelper h = new MyHelper(ns);
		{
			// attribute

			h.reg("id", AwtBaseAttr.class, String.class);
			h.reg("label", AwtBaseAttr.class, String.class);
			h.reg("rows", AwtBaseAttr.class, Integer.class);
			h.reg("columns", AwtBaseAttr.class, Integer.class);
			h.reg("value", AwtBaseAttr.class, String.class);
		}
		{
			// element
			// h.reg("blueprint", CBlueprintElement.class, CBlueprint.class);

			h.reg("Frame", FrameWrapper.class, Frame.class);
			h.reg("Container", ContainerWrapper.class, Container.class);

			h.reg("position", Awt_positionWrapper.class, Awt_position.class);

			h.reg("FlowLayout", FlowLayoutWrapper.class, FlowLayout.class);
			h.reg("GridLayout", GridLayoutWrapper.class, GridLayout.class);
			h.reg("BorderLayout", BorderLayoutWrapper.class, BorderLayout.class);

			h.reg("MenuBar", MenuBarWrapper.class, MenuBar.class);
			h.reg("Menu", MenuWrapper.class, Menu.class);
			h.reg("MenuItem", MenuItemWrapper.class, MenuItem.class);
		}
		return ns;
	}

}
