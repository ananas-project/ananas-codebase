package ananas.lib.xmpp.client.component;

public class XMPPComConnectionElement extends BaseXmppComponentElement {

	public XMPPComConnection getConnection() {
		return (XMPPComConnection) this.getTarget(true);
	}

}
