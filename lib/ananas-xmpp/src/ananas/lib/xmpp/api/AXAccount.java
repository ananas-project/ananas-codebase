package ananas.lib.xmpp.api;

public interface AXAccount {

	String getServer();// ip4/ip6/domain-name

	int getPort();

	AXAddress getXMPPAddress();// 'user@domain/res'

	String getPassword();

	int getPriority();

	boolean useSSL();

	boolean ignoreSSLException();

	boolean needForSecurity();

}
