package ananas.app.roadmap.util;

public class DistanceOnTheEarth {

	private static final double DEF_PI180 = 0.01745329252; // PI/180.0
	private static final double DEF_R = 6370693.5; // radius of earth

	public static double calcDistance(double lon1, double lat1, double lon2,
			double lat2) {

		double ew1, ns1, ew2, ns2;
		double distance;

		//
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;

		//
		distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1)
				* Math.cos(ns2) * Math.cos(ew1 - ew2);

		//
		if (distance > 1.0)
			distance = 1.0;
		else if (distance < -1.0)
			distance = -1.0;
		//
		distance = DEF_R * Math.acos(distance);

		return distance;
	}

	public static void main(String[] arg) {

		double lon1 = 110.29109339;
		double lat1 = 25.27796412;
		double lon2 = 114.05451355;
		double lat2 = 22.54565041;

		double dist = calcDistance(lon1, lat1, lon2, lat2);
		System.out.println(dist);
	}

}
