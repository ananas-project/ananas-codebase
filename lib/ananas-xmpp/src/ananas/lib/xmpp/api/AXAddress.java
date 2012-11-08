package ananas.lib.xmpp.api;

public interface AXAddress {

	boolean isFullAddress();

	boolean isPureAddress();

	String getResource();

	String getUser();

	String getDomain();

	String getFullAddress();

	String getPureAddress();

}
