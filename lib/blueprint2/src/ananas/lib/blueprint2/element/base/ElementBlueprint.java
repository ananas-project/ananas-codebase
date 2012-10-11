package ananas.lib.blueprint2.element.base;

import ananas.lib.blueprint2.dom.INode;
import ananas.lib.blueprint2.dom.IText;

public class ElementBlueprint extends ElementBase {

	private ElementBody mBody;
	private ElementHead mHead;

	@Override
	public boolean appendChild(INode child) {

		if (child == null) {
			return false;
		} else if (child instanceof IText) {
			// return true;
		} else if (child instanceof ElementBody) {
			this.mBody = (ElementBody) child;
		} else if (child instanceof ElementHead) {
			this.mHead = (ElementHead) child;
		} else {
			return false;
		}
		return true;
	}

}
