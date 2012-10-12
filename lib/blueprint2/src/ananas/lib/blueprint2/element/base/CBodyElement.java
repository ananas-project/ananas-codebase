package ananas.lib.blueprint2.element.base;

import ananas.lib.blueprint2.dom.IElement;
import ananas.lib.blueprint2.dom.INode;
import ananas.lib.blueprint2.dom.IText;

public class CBodyElement extends BaseElement {

	public CBodyElement() {
	}

	@Override
	public boolean appendChild(INode child) {

		if (child == null) {
			return false;

		} else if (child instanceof IText) {
			CBody body = (CBody) this.getTarget(true);
			IText ch = (IText) child;
			body.add(ch.getData());
			return true;

		} else if (child instanceof IElement) {
			CBody body = (CBody) this.getTarget(true);
			Object ch = ((IElement) child).getTarget(true);
			body.add(ch);
			return true;

		} else {
			return super.appendChild(child);
		}
	}
}
