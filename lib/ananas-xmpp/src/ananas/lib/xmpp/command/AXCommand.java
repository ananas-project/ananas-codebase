package ananas.lib.xmpp.command;

import ananas.lib.xmpp.api.AXClient;

public interface AXCommand {

	void onExecuteBy(AXClient client);

}
