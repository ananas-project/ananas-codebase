package ananas.lib.gislib;

public interface GpsRecord {

	String getSource();

	long getGpsTime();

	long getRtcTime();

	double getLongitude();

	double getLatitude();

	double getAltitude();

	double getAccuracy();

	double getSpeed();

	double getBearing();
}
