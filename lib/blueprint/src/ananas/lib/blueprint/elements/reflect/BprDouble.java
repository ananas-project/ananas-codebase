package ananas.lib.blueprint.elements.reflect;

public class BprDouble extends BprBaseType {

	private double mValue;

	@Override
	protected void parseString(String s) {
		this.mValue = Double.parseDouble(s);
	}

	public double doubleValue() {
		return this.mValue;
	}

}
