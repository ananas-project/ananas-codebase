package ananas.lib.blueprint.elements.reflect;

public class BprBoolean extends BprBaseType {

	private boolean mValue;

	@Override
	protected void parseString(String s) {
		boolean val = true;
		if (s == null) {
			val = false;
		} else if (s.equalsIgnoreCase("true")) {
		} else if (s.equalsIgnoreCase("yes")) {
		} else if (s.equalsIgnoreCase("1")) {
		} else {
			val = false;
		}
		this.mValue = val;
	}

	public boolean booleanValue() {
		return this.mValue;
	}

}
