package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class BprDictionary extends BprObjectBase {

	private final HashMap<String, BprDictItem> mMap;

	public BprDictionary() {
		this.mMap = new HashMap<String, BprDictItem>();
	}

	@Override
	public void onSaveBegin(OutputStreamWriter osw) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveContent(OutputStreamWriter osw) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveEnd(OutputStreamWriter osw) throws IOException {
		// TODO Auto-generated method stub

	}

	public void setBprDictItem(BprDictItem item) {
		String key = item.getKey();
		this.mMap.put(key, item);
	}
}
