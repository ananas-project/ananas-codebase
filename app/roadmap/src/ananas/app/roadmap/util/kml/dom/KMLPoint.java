package ananas.app.roadmap.util.kml.dom;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class KMLPoint extends KMLGeometry {

	public KML_coordinates mCoordinates;

	@Override
	public boolean appendChild(IKMLObject child) {
		if (child instanceof KML_coordinates) {
			this.mCoordinates = (KML_coordinates) child;
		} else {
			return super.appendChild(child);
		}
		return true;
	}

	@Override
	public OverlayItem createOverlayItem(KMLPlacemark placemark) {
		String title = "";
		String content = "";
		if (placemark != null) {
			KML_description desc = placemark.mDescription;
			KML_name name = placemark.mName;
			title += (name == null) ? ("[no title]") : name.mData;
			content += (desc == null) ? ("") : desc.mData;
		}
		GeoPoint pt = this.mCoordinates.getCoordinate(0);
		return new OverlayItem(pt, title, content);
	}

}
