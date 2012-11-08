package ananas.lib.xmpp;

import ananas.lib.xmpp.api.AXClientFactory;

public class AnanasXMPP {

	public static AXClientFactory getDefaultFactory() {
		return DefaultAXClientFactoryLoader.load();
	}

}
