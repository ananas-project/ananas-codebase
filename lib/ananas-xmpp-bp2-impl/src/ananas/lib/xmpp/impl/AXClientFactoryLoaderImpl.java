package ananas.lib.xmpp.impl;

import ananas.lib.xmpp.api.AXClientFactory;
import ananas.lib.xmpp.api.AXClientFactoryLoader;

public final class AXClientFactoryLoaderImpl implements AXClientFactoryLoader {

	static final String default_conf_uri = ("file://res/ananas.xmpp.client.xml");

	public AXClientFactoryLoaderImpl() {
	}

	@Override
	public AXClientFactory loadFactory() {
		return this.loadFactory(null);
	}

	@Override
	public AXClientFactory loadFactory(String uri) {
		if (uri == null) {
			uri = default_conf_uri;
		}
		return new ImplAXClientFactory(uri);
	}

}
