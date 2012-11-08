package ananas.lib.xmpp;

import java.net.URI;

import ananas.lib.xmpp.api.AXAddress;

public class DefaultAXAddress implements AXAddress {

	private final String mDomain;
	private final String mUser;
	private final String mRes;
	private String mFullAddrCache;
	private String mPureAddrCache;

	public DefaultAXAddress(String addr) {
		URI uri = URI.create("xmpp://" + addr);
		this.mDomain = uri.getHost();
		this.mUser = uri.getUserInfo();
		this.mRes = uri.getPath();
	}

	@Override
	public boolean isFullAddress() {
		return (this.mRes != null);
	}

	@Override
	public boolean isPureAddress() {
		return (this.mRes == null);
	}

	@Override
	public String getResource() {
		return this.mRes;
	}

	@Override
	public String getUser() {
		return this.mUser;
	}

	@Override
	public String getDomain() {
		return this.mDomain;
	}

	@Override
	public String getFullAddress() {
		String addr = this.mFullAddrCache;
		if (addr == null) {
			addr = this.mUser + "@" + this.mDomain + this.mRes;
			this.mFullAddrCache = addr;
		}
		return addr;
	}

	@Override
	public String getPureAddress() {
		String addr = this.mPureAddrCache;
		if (addr == null) {
			addr = this.mUser + "@" + this.mDomain;
			this.mPureAddrCache = addr;
		}
		return addr;
	}

}
