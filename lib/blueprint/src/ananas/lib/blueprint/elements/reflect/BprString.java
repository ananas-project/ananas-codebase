package ananas.lib.blueprint.elements.reflect;

public class BprString extends BprBaseType {

	private String mValue;

	@Override
	protected void parseString(String s) {
		this.mValue = s;
	}

	public String stringValue() {
		return this.mValue;
	}
}
