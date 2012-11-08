package ananas.lib.xmpp;

import ananas.lib.xmpp.api.AXAccount;
import ananas.lib.xmpp.api.AXAddress;

public class DefaultAXAccount implements AXAccount {

	public AXAddress address;
	public String password;
	public String server;
	public int port = 5552;
	public int priority = 0;
	public boolean useSSL = false;
	public boolean ignoreSSLException = false;
	public boolean needForSecurity = true;

	@Override
	public String getServer() {
		String serv = this.server;
		if (serv == null) {
			AXAddress addr = this.address;
			serv = addr.getDomain();
		}
		return serv;
	}

	public void setXMPPAddress(String addr) {
		this.address = new DefaultAXAddress(addr);
	}

	@Override
	public int getPort() {
		return this.port;
	}

	@Override
	public AXAddress getXMPPAddress() {
		return this.address;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public int getPriority() {
		return this.priority;
	}

	@Override
	public boolean useSSL() {
		return this.useSSL;
	}

	@Override
	public boolean ignoreSSLException() {
		return this.ignoreSSLException;
	}

	@Override
	public boolean needForSecurity() {
		return this.needForSecurity;
	}

}
