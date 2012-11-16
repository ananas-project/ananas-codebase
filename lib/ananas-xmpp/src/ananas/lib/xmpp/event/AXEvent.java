package ananas.lib.xmpp.event;

import ananas.lib.xmpp.api.AXClientListener;

public interface AXEvent {

	void onReceiveBy(AXClientListener listener);

}
