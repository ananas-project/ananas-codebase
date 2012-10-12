package ananas.lib.blueprint2.awt;

import java.awt.MenuItem;

import ananas.lib.blueprint2.dom.IAttr;

public class MenuItemWrapper extends MenuComponentWrapper {

	private IAttr mLabel;

	@Override
	public boolean setAttribute(IAttr attr) {
		String name = attr.getBlueprintClass().getLocalName();
		if (name == null) {
			return false;

		} else if (name.equals("label")) {
			this.mLabel = attr;

		} else {
			return super.setAttribute(attr);
		}
		return true;
	}

	@Override
	public void tagBegin() {

		final MenuItem item = (MenuItem) this.getTarget(true);

		if (this.mLabel != null) {
			String s = this.stringFromAttr(this.mLabel);
			item.setLabel(s);
		}
	}

}
