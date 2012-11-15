package test.ananas.xmpp;

import ananas.lib.xmpp.AnanasXMPP;
import ananas.lib.xmpp.DefaultAXAccount;
import ananas.lib.xmpp.DefaultAXAddress;
import ananas.lib.xmpp.api.AXClient;
import ananas.lib.xmpp.api.AXClientFactory;
import ananas.lib.xmpp.api.command.AXCmdSetClient;

public class TestMain {

	public static void main(String[] args) {

		try {

			AXClientFactory fact = AnanasXMPP.getFactory(null);
			DefaultAXAccount account = new DefaultAXAccount();
			account.server = "talk.google.com";
			account.password = "12345678";
			account.address = new DefaultAXAddress(
					"xk.1985.02.17@gmail.com/a/b.c");
			AXClient client = fact.createClient(account);
			client.execute(new AXCmdSetClient(AXCmdSetClient.CTRL_CONNECT));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
