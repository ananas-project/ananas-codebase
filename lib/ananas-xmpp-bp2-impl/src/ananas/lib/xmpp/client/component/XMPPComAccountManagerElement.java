package ananas.lib.xmpp.client.component;

public class XMPPComAccountManagerElement extends BaseXmppComponentElement {

	public XMPPComAccountManager getAccountManager() {
		return (XMPPComAccountManager) this.getTarget(true);
	}

}
