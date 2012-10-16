package ananas.lib.blueprint2.swing;

import javax.swing.AbstractButton;

import ananas.lib.blueprint2.dom.IAttr;

public class AbstractButtonWrapper extends JComponentWrapper {

	private IAttr mText;

	@Override
	public boolean setAttribute(IAttr attr) {
		String lname = attr.getBlueprintClass().getLocalName();
		if (lname == null) {
			return false;
		} else if (lname.equals("label")) {
			this.mText = attr;
		} else if (lname.equals("text")) {
			this.mText = attr;
		} else {
			return super.setAttribute(attr);
		}
		return true;
	}

	@Override
	public void onTagBegin() {
		super.onTagBegin();
		AbstractButton btn = (AbstractButton) this.getTarget(true);
		if (this.mText != null) {
			String s = this.stringFromAttr(this.mText);
			btn.setText(s);
		}
	}
}
