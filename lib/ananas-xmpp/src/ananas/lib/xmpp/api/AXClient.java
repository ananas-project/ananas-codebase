package ananas.lib.xmpp.api;

import ananas.lib.xmpp.api.command.AXCommand;

public interface AXClient {

	void addListener(AXClientListener listener);

	void removeListener(AXClientListener listener);

	void execute(AXCommand command);

}
