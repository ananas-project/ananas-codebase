package test.ananas.xmpp;

import java.io.IOException;

import ananas.lib.io.DefaultConnector;
import ananas.lib.io.IConnector;
import ananas.lib.xmpp.AnanasXMPP;
import ananas.lib.xmpp.IXmppConnection;

public class TestMain {

	public static void main(String[] args) {

		try {
			AnanasXMPP.init();
			IConnector connector = DefaultConnector.getDefault();
			IXmppConnection conn = (IXmppConnection) connector
					.open("xmpp://talk.google.com:5552/a_res_name?ssl=false");
			conn.connect("xukun@gmail.com", "12345678");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
