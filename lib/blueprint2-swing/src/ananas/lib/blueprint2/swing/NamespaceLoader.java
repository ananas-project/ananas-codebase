package ananas.lib.blueprint2.swing;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;

import ananas.lib.blueprint2.dom.helper.IImplementation;
import ananas.lib.blueprint2.dom.helper.INamespace;
import ananas.lib.blueprint2.dom.helper.INamespaceLoader;
import ananas.lib.blueprint2.element.base.BaseAttr;

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
		String uri = "xmlns:ananas:blueprint:swing";
		String defaultPrefix = "swing";
		INamespace ns = impl.newNamespace(uri, defaultPrefix);
		MyHelper h = new MyHelper(ns);
		{
			// attribute

			h.reg("actionCommand", BaseAttr.class, String.class);
			h.reg("background", BaseAttr.class, Color.class);
			h.reg("command", BaseAttr.class, String.class);
			h.reg("dividerLocation", BaseAttr.class, Double.class);
			h.reg("dividerSize", BaseAttr.class, Integer.class);
			h.reg("editable", BaseAttr.class, Boolean.class);
			h.reg("id", BaseAttr.class, String.class);
			h.reg("label", BaseAttr.class, String.class);
			h.reg("orientation", BaseAttr.class, String.class);
			h.reg("resizeWeight", BaseAttr.class, Double.class);
			h.reg("title", BaseAttr.class, String.class);
			h.reg("text", BaseAttr.class, String.class);
			h.reg("x", BaseAttr.class, Integer.class);
			h.reg("y", BaseAttr.class, Integer.class);
			h.reg("width", BaseAttr.class, Integer.class);
			h.reg("height", BaseAttr.class, Integer.class);
			h.reg("value", BaseAttr.class, String.class);

		}
		{
			// element

			h.reg("position", Swing_positionWrapper.class, Swing_position.class);

			h.reg("JMenu", JMenuWrapper.class, JMenu.class);
			h.reg("JMenuBar", JMenuBarWrapper.class, JMenuBar.class);
			h.reg("JMenuItem", JMenuItemWrapper.class, JMenuItem.class);
			h.reg("JPopupMenu", JPopupMenuWrapper.class, JPopupMenu.class);
			h.reg("JSeparator", JSeparatorWrapper.class, JSeparator.class);

			h.reg("JButton", JButtonWrapper.class, JButton.class);
			h.reg("JFrame", JFrameWrapper.class, JFrame.class);
			h.reg("JLabel", JLabelWrapper.class, JLabel.class);
			h.reg("JList", JListWrapper.class, JList.class);
			h.reg("JPanel", JPanelWrapper.class, JPanel.class);
			h.reg("JPasswordField", JPasswordFieldWrapper.class,
					JPasswordField.class);
			h.reg("JScrollPane", JScrollPaneWrapper.class, JScrollPane.class);
			h.reg("JSplitPane", JSplitPaneWrapper.class, JSplitPane.class);

			h.reg("JTextArea", JTextAreaWrapper.class, JTextArea.class);
			h.reg("JTextField", JTextFieldWrapper.class, JTextField.class);

			h.reg("JTree", JTreeWrapper.class, JTree.class);
		}
		return ns;
	}

}
