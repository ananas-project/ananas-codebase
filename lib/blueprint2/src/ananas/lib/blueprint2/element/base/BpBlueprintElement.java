package ananas.lib.blueprint2.element.base;

import ananas.lib.blueprint2.dom.INode;
import ananas.lib.blueprint2.dom.IText;

public class BpBlueprintElement extends BaseElement {

	@Override
	public boolean appendChild(INode child) {

		if (child == null) {
			return false;
		} else if (child instanceof IText) {
			// return true;

		} else if (child instanceof BpBodyElement) {
			BpBlueprint bp = (BpBlueprint) this.getTarget(true);
			BpBody body = (BpBody) ((BpBodyElement) child).getTarget(true);
			bp.setBody(body);

		} else if (child instanceof BpHeadElement) {
			BpBlueprint bp = (BpBlueprint) this.getTarget(true);
			BpHead head = (BpHead) ((BpHeadElement) child).getTarget(true);
			bp.setHead(head);

		} else {
			return false;
		}
		return true;
	}

}
