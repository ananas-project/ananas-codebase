package ananas.lib.blueprint.elements.reflect;

public class BprBaseType {

	public boolean bind(Object child) {
		if (child instanceof ReflectElement) {

		} else if (child instanceof String) {
			
		} else {
			return false;
		}
		return true;
	}

}
