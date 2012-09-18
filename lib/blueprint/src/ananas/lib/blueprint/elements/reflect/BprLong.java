package ananas.lib.blueprint.elements.reflect;

public class BprLong extends BprBaseType {

	private long mValue;

	@Override
	protected void parseString(String s) {
		this.mValue = Long.parseLong(s);
	}

	public long longValue() {
		return this.mValue;
	}
}
