package ananas.lib.blueprint2.swing;

import javax.swing.text.JTextComponent;

import ananas.lib.blueprint2.dom.IAttr;
import ananas.lib.blueprint2.dom.INode;
import ananas.lib.blueprint2.dom.IText;

public class JTextComponentWrapper extends JComponentWrapper {

	private IAttr mText;
	private StringBuilder mStringBuilder;

	@Override
	public boolean setAttribute(IAttr attr) {
		String name = attr.getBlueprintClass().getLocalName();
		if (name == null) {
			return false;
		} else if (name.equals("text")) {
			this.mText = attr;
		} else {
			return super.setAttribute(attr);
		}
		return true;
	}

	@Override
	public boolean appendChild(INode child) {
		if (child instanceof IText) {
			this.appendText((IText) child);
			return true;
		} else {
			return super.appendChild(child);
		}
	}

	private void appendText(IText txt) {
		String data = txt.getData();
		StringBuilder sb = this._getStringBuilder(true);
		if (sb.length() > 0) {
			sb.append(' ');
		}
		sb.append(data);
	}

	private StringBuilder _getStringBuilder(boolean create) {
		StringBuilder sb = this.mStringBuilder;
		if (sb == null && create) {
			this.mStringBuilder = sb = new StringBuilder();
		}
		return sb;
	}

	public void onTagEnd() {
		super.onTagEnd();

		JTextComponent jtc = this.getJTextComponent(true);

		StringBuilder sb = this._getStringBuilder(false);
		if (sb != null) {
			String s = sb.toString();
			jtc.setText(s);
		}
	}

	@Override
	public void onTagBegin() {
		super.onTagBegin();
		JTextComponent jtc = this.getJTextComponent(true);
		if (this.mText != null) {
			String s = this.stringFromAttr(this.mText);
			jtc.setText(s);
		}
	}

	public JTextComponent getJTextComponent(boolean create) {
		return (JTextComponent) this.getTarget(create);
	}
}
