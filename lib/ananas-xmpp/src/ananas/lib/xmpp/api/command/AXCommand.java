package ananas.lib.xmpp.api.command;

import ananas.lib.xmpp.api.AXClient;

public interface AXCommand {

	void onExecuteBy(AXClient client);

}
