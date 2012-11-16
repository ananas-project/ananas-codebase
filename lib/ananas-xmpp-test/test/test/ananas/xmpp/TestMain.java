package test.ananas.xmpp;

import ananas.lib.xmpp.AnanasXMPP;
import ananas.lib.xmpp.DefaultAXAccount;
import ananas.lib.xmpp.DefaultAXAddress;
import ananas.lib.xmpp.api.AXClientEx;
import ananas.lib.xmpp.api.AXClientFactory;

public class TestMain {

	public static void main(String[] args) {

		try {

			AXClientFactory fact = AnanasXMPP.getFactory(null);
			DefaultAXAccount account = new DefaultAXAccount();
			account.server = "talk.google.com";
			account.password = "12345678";
			account.address = new DefaultAXAddress(
					"xk.1985.02.17@gmail.com/a/b.c");
			AXClientEx client = fact.createClient(account);
			client.connect();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
