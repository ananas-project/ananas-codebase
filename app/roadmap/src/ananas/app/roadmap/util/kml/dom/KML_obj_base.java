package ananas.app.roadmap.util.kml.dom;


public class KML_obj_base implements IKMLObject {

	private IKMLObject mParent;

	public KML_obj_base() {
	}

	@Override
	public boolean setParent(IKMLObject parent) {
		this.mParent = parent;
		return true;
	}

	@Override
	public boolean appendChild(IKMLObject child) {
		return false;
	}

	@Override
	public boolean setAttribute(String name, String value) {
		return false;
	}

	@Override
	public IKMLObject getParent() {
		return this.mParent;
	}

	@Override
	public boolean appendText(String text) {
		return false;
	}

}
