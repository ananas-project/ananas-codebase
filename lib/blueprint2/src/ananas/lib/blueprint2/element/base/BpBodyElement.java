package ananas.lib.blueprint2.element.base;

import ananas.lib.blueprint2.dom.IElement;
import ananas.lib.blueprint2.dom.INode;
import ananas.lib.blueprint2.dom.IText;

public class BpBodyElement extends BaseElement {

	public BpBodyElement() {
	}

	@Override
	public boolean appendChild(INode child) {

		if (child == null) {
			return false;

		} else if (child instanceof IText) {
			BpBody body = (BpBody) this.getTarget(true);
			IText ch = (IText) child;
			body.add(ch.getData());
			return true;

		} else if (child instanceof IElement) {
			BpBody body = (BpBody) this.getTarget(true);
			Object ch = ((IElement) child).getTarget(true);
			body.add(ch);
			return true;

		} else {
			return super.appendChild(child);
		}
	}
}
