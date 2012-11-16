package ananas.lib.xmpp.client.component;

public class XMPPComRootElement extends BaseXmppComponentElement {

	public XMPPComRoot getRoot() {
		return (XMPPComRoot) this.getTarget(true);
	}
}
