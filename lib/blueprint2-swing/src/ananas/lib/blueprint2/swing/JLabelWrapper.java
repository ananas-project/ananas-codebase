package ananas.lib.blueprint2.swing;

import javax.swing.JLabel;

import ananas.lib.blueprint2.dom.IAttr;
import ananas.lib.blueprint2.dom.INode;
import ananas.lib.blueprint2.dom.IText;

public class JLabelWrapper extends JComponentWrapper {

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
	public void tagBegin() {
		super.tagBegin();
		JLabel label = this.getJLabel(true);
		if (this.mText != null) {
			String s = this.stringFromAttr(this.mText);
			label.setText(s);
		}
	}

	private JLabel getJLabel(boolean create) {
		return (JLabel) this.getTarget(create);
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

	public void tagEnd() {
		super.tagEnd();
		JLabel label = this.getJLabel(true);
		StringBuilder sb = this._getStringBuilder(false);
		if (sb != null) {
			String s = sb.toString();
			label.setText(s);
		}
	}

}
