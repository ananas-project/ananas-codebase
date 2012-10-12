package ananas.lib.blueprint2.element.base;

import ananas.lib.blueprint2.dom.INode;

public class CHeadElement extends BaseElement {

	@Override
	public boolean appendChild(INode child) {

		if (child == null) {
			return false;

		} else if (child instanceof CLinkElement) {
			CHead head = (CHead) this.getTarget(true);
			CLink link = (CLink) ((CLinkElement) child).getTarget(true);
			head.addLink(link);

		} else {
			return super.appendChild(child);

		}
		return true;
	}

}
