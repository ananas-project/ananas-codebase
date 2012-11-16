package ananas.lib.xmpp.api;

public interface AXClientControl {

	int CTRL_GET_ACCOUNT = 1;
	int CTRL_CONNECT = 2;
	int CTRL_DISCONNECT = 3;
	int CTRL_CLOSE = 4;
	int CTRL_BIND_ACCOUNT = 5;
	int CTRL_GET_INTERFACE = 6;
	int CTRL_GET_PHASE = 7;

	AXAccount getAccount();

	void bindAccount(AXAccount account);

	Object getInterface(Class<?> aInterface);

	void connect();

	void disconnect();

	void close();

	AXClientPhase getPhase();

}
