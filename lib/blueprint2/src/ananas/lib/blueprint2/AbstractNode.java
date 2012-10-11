package ananas.lib.blueprint2;

import ananas.lib.blueprint2.dom.INode;
import ananas.lib.blueprint2.dom.helper.IClass;

public class AbstractNode implements INode {

	private IClass mClass;

	protected AbstractNode() {
	}

	@Override
	public final boolean bindBlueprintClass(IClass aClass) {
		if (this.mClass == null && aClass != null) {
			this.mClass = aClass;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public final IClass getBlueprintClass() {
		return this.mClass;
	}

}
