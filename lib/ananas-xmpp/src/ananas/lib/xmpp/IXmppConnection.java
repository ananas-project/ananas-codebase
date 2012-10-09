package ananas.lib.xmpp;

import ananas.lib.io.IStreamConnection;

public interface IXmppConnection extends IStreamConnection {

	void connect(String user, String password);

}
