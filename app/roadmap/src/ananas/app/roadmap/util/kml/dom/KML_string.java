package ananas.app.roadmap.util.kml.dom;


public class KML_string extends KML_obj_base {

	public String mData;

	@Override
	public boolean appendText(String text) {
		this.mData = text;
		return true;
	}

}
