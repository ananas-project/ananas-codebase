package ananas.lib.blueprint.elements.reflect;

public class BprBlueprint extends BprObject {

	// private ReflectElement mElement;

	public boolean bind(Object child) {
		if (child instanceof ReflectElement) {
			// this.mElement = (ReflectElement) child;
		} else {
			return false;
		}
		return true;
	}

	public void setBprHead(BprHead head) {
	}

	public void setBprBody(BprBody body) {
	}

}
