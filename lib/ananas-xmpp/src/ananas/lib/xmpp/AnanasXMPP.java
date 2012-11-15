package ananas.lib.xmpp;

import ananas.lib.xmpp.api.AXClientFactory;

public class AnanasXMPP {

	public static AXClientFactory getFactory(String uri) {
		return AnanasXmppImplementation.loadFactory(uri);
	}

}
