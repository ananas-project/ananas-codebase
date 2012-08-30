package ananas.lib.blueprint.elements.reflect;

public class BprInteger extends BprBaseType {

	private int mValue;

	public int intValue() {
		return this.mValue;
	}

	@Override
	protected void parseString(String s) {
		this.mValue = Integer.parseInt(s);
	}

}
