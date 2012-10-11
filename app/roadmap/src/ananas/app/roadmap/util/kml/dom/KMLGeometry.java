package ananas.app.roadmap.util.kml.dom;

import com.google.android.maps.OverlayItem;

public abstract class KMLGeometry extends KMLObject {

	public abstract OverlayItem createOverlayItem(KMLPlacemark placemark);

}
