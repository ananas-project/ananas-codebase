package ananas.app.roadmap.util.kml.dom;

import java.util.Vector;

public class KMLContainer extends KMLFeature {

	private final Vector<KMLFeature> mChildList;

	public KMLContainer() {
		this.mChildList = new Vector<KMLFeature>();
	}

	@Override
	public boolean appendChild(IKMLObject child) {
		if (child instanceof KMLFeature) {
			this._appendFeature((KMLFeature) child);
		} else {
			return super.appendChild(child);
		}
		return true;
	}

	private void _appendFeature(KMLFeature child) {
		this.mChildList.addElement(child);
	}

	@Override
	public void listOverlayItems(IOverlayItemEnumerator enumerator) {
		for (KMLFeature fe : this.mChildList) {
			fe.listOverlayItems(enumerator);
		}
	}

}
