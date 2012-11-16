package ananas.lib.xmpp.client.component;

public class XMPPComCodecElement extends BaseXmppComponentElement {

	public XMPPComCodec getCodec() {
		return (XMPPComCodec) this.getTarget(true);
	}

}
