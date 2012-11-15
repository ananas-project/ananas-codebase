package ananas.lib.xmpp.api;

public interface AXClientFactoryLoader {

	AXClientFactory loadFactory();

	AXClientFactory loadFactory(String uri);

}
