package ananas.lib.blueprint.elements.reflect;

public abstract class BprBaseType extends BprObject {

	public boolean bind(Object child) {
		if (child instanceof ReflectElement) {

		} else if (child instanceof String) {
			this.parseString(child.toString());

		} else {
			return false;
		}
		return true;
	}

	protected abstract void parseString(String s);

}
