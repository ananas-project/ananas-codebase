package ananas.lib.blueprint2.swing;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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

			h.reg("id", BaseAttr.class, String.class);
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

			h.reg("JButton", JButtonWrapper.class, JButton.class);
			h.reg("JFrame", JFrameWrapper.class, JFrame.class);
			h.reg("JLabel", JLabelWrapper.class, JLabel.class);
			h.reg("JPanel", JPanelWrapper.class, JPanel.class);
			h.reg("JPasswordField", JPasswordFieldWrapper.class,
					JPasswordField.class);
			h.reg("JScrollPane", JScrollPaneWrapper.class, JScrollPane.class);
			h.reg("JTextArea", JTextAreaWrapper.class, JTextArea.class);
			h.reg("JTextField", JTextFieldWrapper.class, JTextField.class);
		}
		return ns;
	}

}
