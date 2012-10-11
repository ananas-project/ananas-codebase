package ananas.app.roadmap.util.kml.dom;

import java.util.Vector;

import com.google.android.maps.GeoPoint;

public class KML_coordinates extends KML_obj_base {

	private GeoPoint mFirstPoint;
	private Vector<GeoPoint> mPointList;

	@Override
	public boolean appendText(String text) {
		this._parseText(text);
		return true;
	}

	private void _parseText(String text) {
		GeoPoint the1stPoint = null;
		Vector<GeoPoint> ptList = null;
		String lat, lon;
		lat = lon = null;
		int iFrom, iTo, iCol;
		iCol = iFrom = iTo = 0;
		final char[] chs = (text + " ").toCharArray();
		final int chslen = chs.length;
		for (int i = 0; i < chslen; i++) {
			final char ch = chs[i];
			switch (ch) {
			case ' ':
			case 0x09:
			case 0x0a:
			case 0x0d:
			case ',': {
				iTo = i;
				final String str;
				if (iFrom < iTo) {
					str = new String(chs, iFrom, (iTo - iFrom));
				} else {
					str = null;
				}
				if (iCol == 0) {
					lon = str;
				} else if (iCol == 1) {
					lat = str;
				} else if (iCol == 2) {
					// alt = str;
				}
				if (ch == ',') {
					// next col
					iCol++;
				} else {
					// next line
					if (lon != null && lat != null) {
						double db_lon = Double.parseDouble(lon);
						double db_lat = Double.parseDouble(lat);
						GeoPoint pt = this._newPoint(db_lon, db_lat);
						if (ptList != null) {
							ptList.addElement(pt);
						} else {
							if (the1stPoint == null) {
								the1stPoint = pt;
							} else {
								ptList = new Vector<GeoPoint>();
								ptList.addElement(the1stPoint);
								ptList.addElement(pt);
							}
						}
					}
					lat = lon = null;
					iCol = 0;
				}
				iFrom = iTo + 1;
				break;
			}
			default:
			}
		}
		this.mFirstPoint = the1stPoint;
		this.mPointList = ptList;
	}

	private GeoPoint _newPoint(double lon, double lat) {
		return new GeoPoint((int) (lat * 1000000), (int) (lon * 1000000));
	}

	public GeoPoint getCoordinate(int index) {
		Vector<GeoPoint> ptList = this.mPointList;
		if (ptList == null) {
			return this.mFirstPoint;
		} else {
			if (0 <= index && index < ptList.size()) {
				return ptList.elementAt(index);
			} else {
				return null;
			}
		}
	}

}
