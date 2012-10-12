package ananas.lib.blueprint2.awt;

import ananas.lib.blueprint2.dom.IAttr;

public class Awt_positionWrapper extends ObjectWrapper {

	private IAttr mValue;

	@Override
	public boolean setAttribute(IAttr attr) {
		String lname = attr.getBlueprintClass().getLocalName();
		if (lname == null) {
		} else if (lname.equals("value")) {
			this.mValue = attr;
		} else {
			return super.setAttribute(attr);
		}
		return true;
	}

	public String getValue() {
		if (this.mValue == null)
			return null;
		return this.mValue.getValue();
	}

}
