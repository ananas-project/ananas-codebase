package ananas.lib.xmpp.api.event;

import ananas.lib.xmpp.api.AXClientListener;

public interface AXEvent {

	void onReceiveBy(AXClientListener listener);

}
