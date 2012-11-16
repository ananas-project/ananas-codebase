package ananas.lib.xmpp.component;

import ananas.lib.xmpp.api.AXClient;

public interface AXClientComponent extends AXClient {

	void addChild(AXClientComponent child);

}
