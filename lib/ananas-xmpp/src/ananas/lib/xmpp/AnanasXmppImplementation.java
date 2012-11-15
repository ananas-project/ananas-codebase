package ananas.lib.xmpp;

import ananas.lib.xmpp.api.AXClientFactory;
import ananas.lib.xmpp.api.AXClientFactoryLoader;

final class AnanasXmppImplementation {

	public static String loader_class_name = "ananas.lib.xmpp.impl.AXClientFactoryLoaderImpl";
	public static String conf_file_uri = "resource:///ananas.xmpp.client.xml";

	public static AXClientFactory loadFactory(String uri) {
		try {
			if (uri == null)
				uri = conf_file_uri;
			Class<?> cls = Class.forName(loader_class_name);
			AXClientFactoryLoader ldr = (AXClientFactoryLoader) cls
					.newInstance();
			return ldr.loadFactory(uri);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
