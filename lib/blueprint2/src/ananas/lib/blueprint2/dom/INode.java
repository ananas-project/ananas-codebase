package ananas.lib.blueprint2.dom;

import ananas.lib.blueprint2.dom.helper.IClass;

public interface INode {

	boolean bindBlueprintClass(IClass aClass);

	IClass getBlueprintClass();

}
