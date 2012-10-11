package ananas.app.roadmap.util.kml.dom;

import com.google.android.maps.OverlayItem;

public class KMLPlacemark extends KMLFeature {

	public KMLGeometry mGeometry;
	private OverlayItem mOverlayItem;

	@Override
	public boolean appendChild(IKMLObject child) {

		if (child instanceof KMLGeometry) {
			this.mGeometry = (KMLGeometry) child;

		} else {
			return super.appendChild(child);
		}

		return true;
	}

	public OverlayItem getOverlayItem() {
		if (this.mOverlayItem == null) {
			final KMLGeometry geo = this.mGeometry;
			if (geo != null) {
				this.mOverlayItem = geo.createOverlayItem(this);
			}
		}
		return this.mOverlayItem;
	}

	@Override
	public void listOverlayItems(IOverlayItemEnumerator enumerator) {
		OverlayItem item = this.getOverlayItem();
		enumerator.append(item);
	}

}
