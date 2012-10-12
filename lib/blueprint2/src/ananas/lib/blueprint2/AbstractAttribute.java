package ananas.lib.blueprint2;

import ananas.lib.blueprint2.dom.IAttr;

public class AbstractAttribute extends AbstractNode implements IAttr {

	private String mValue;

	protected AbstractAttribute() {
	}

	@Override
	public String getValue() {
		return this.mValue;
	}

	@Override
	public void setValue(String value) {
		this.mValue = value;
	}

}
