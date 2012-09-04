package ananas.lib.blueprint.elements.reflect;

public interface IBlueprintReflectable {

	/**
	 * bind the child to this object.
	 * 
	 * @param child
	 *            the object will be bind
	 * @return return true if success, else return false.
	 * */

	boolean bind(Object child);

}
