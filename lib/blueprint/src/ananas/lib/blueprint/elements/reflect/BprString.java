package ananas.lib.blueprint.elements.reflect;

public class BprString extends BprBaseType {

	private StringBuilder mStrBldr = new StringBuilder();
	private String mValue;

	@Override
	protected void parseString(String s) {
		// this.mValue = s;
	}

	public String stringValue() {
		if (this.mValue == null) {
			this.mValue = this.mStrBldr.toString().trim();
		}
		return this.mValue;
	}

	public boolean bind(Object child) {
		if (child instanceof String) {
			this.mStrBldr.append(child.toString());
			return true;
		} else {
			return super.bind(child);
		}
	}

}
