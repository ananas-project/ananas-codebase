package ananas.lib.xmpp.client.component;

import ananas.lib.blueprint2.AbstractElement;
import ananas.lib.blueprint2.dom.IElement;
import ananas.lib.blueprint2.dom.INode;
import ananas.lib.xmpp.component.AXClientComponent;

public class BaseXmppComponentElement extends AbstractElement {

	public BaseXmppComponent getXmppComponent() {
		return (BaseXmppComponent) this.getTarget(true);
	}

	@Override
	protected boolean onAppendChild(INode child) {
		if (child instanceof IElement) {
			Object tar = ((IElement) child).getTarget(true);
			if (tar instanceof AXClientComponent) {
				AXClientComponent ch = (AXClientComponent) tar;
				this.getXmppComponent().addChild(ch);
				return true;
			}
		}
		return super.onAppendChild(child);
	}
}
