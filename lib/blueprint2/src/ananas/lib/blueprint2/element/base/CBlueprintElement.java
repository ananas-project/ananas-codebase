package ananas.lib.blueprint2.element.base;

import ananas.lib.blueprint2.dom.INode;
import ananas.lib.blueprint2.dom.IText;

public class CBlueprintElement extends ElementBase {

	@Override
	public boolean appendChild(INode child) {

		if (child == null) {
			return false;
		} else if (child instanceof IText) {
			// return true;

		} else if (child instanceof CBodyElement) {
			CBlueprint bp = (CBlueprint) this.getTarget(true);
			CBody body = (CBody) ((CBodyElement) child).getTarget(true);
			bp.setBody(body);

		} else if (child instanceof CHeadElement) {
			CBlueprint bp = (CBlueprint) this.getTarget(true);
			CHead head = (CHead) ((CHeadElement) child).getTarget(true);
			bp.setHead(head);

		} else {
			return false;
		}
		return true;
	}

}
